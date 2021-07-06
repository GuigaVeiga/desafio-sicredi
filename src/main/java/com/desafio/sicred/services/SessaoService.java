package com.desafio.sicred.services;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.sicred.converters.SessaoConverter;
import com.desafio.sicred.converters.VotoConverter;
import com.desafio.sicred.exceptions.pauta.FalhaAoBuscarPautaException;
import com.desafio.sicred.exceptions.sessao.FalhaAoAtualizarSessaoException;
import com.desafio.sicred.exceptions.sessao.FalhaAoBuscarSessaoException;
import com.desafio.sicred.exceptions.sessao.FalhaAoBuscarSessaoPorIdException;
import com.desafio.sicred.exceptions.sessao.FalhaAoBuscarSessoesException;
import com.desafio.sicred.exceptions.sessao.FalhaAoIniciarSessaoException;
import com.desafio.sicred.exceptions.sessao.FalhaAoDeletarSessaoException;
import com.desafio.sicred.exceptions.sessao.FalhaDataFechamentoSessaoException;
import com.desafio.sicred.exceptions.sessao.FalhaValidacaoSessaoException;
import com.desafio.sicred.exceptions.voto.FalhaAoRealizarVotacaoException;
import com.desafio.sicred.models.entities.Associado;
import com.desafio.sicred.models.entities.Pauta;
import com.desafio.sicred.models.entities.Sessao;
import com.desafio.sicred.models.entities.Voto;
import com.desafio.sicred.models.enums.StatusPauta;
import com.desafio.sicred.models.enums.TipoVoto;
import com.desafio.sicred.models.request.PautaRequestModel;
import com.desafio.sicred.models.request.SessaoRequestModel;
import com.desafio.sicred.models.request.VotoRequestModel;
import com.desafio.sicred.models.response.SessaoResponseModel;
import com.desafio.sicred.models.response.TotalVotosResponseModel;
import com.desafio.sicred.models.response.VotoResponseModel;
import com.desafio.sicred.repositories.PautaRepository;
import com.desafio.sicred.repositories.SessaoRepository;
import com.desafio.sicred.repositories.VotoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessaoService {

     private static final String MSG_SESSAO_VAZIO = "Pauta da sessão vazio. Favor preencher o campo. ";
     private static final String MSG_SESSAO_NAO_ENCONTRADO_PELO_ID = "Sessão não encontrada na base de dados. Confira se o id esta correto. ID: %s";
     private static final String MSG_ERROR_FECHAMENTO_SESSAO = "Data e Hora do fechamento da Sessão é menor que a Atual. Data Fechamento: %s";
     private static final String MSG_ERROR_SESSAO_VOTACAO = "Erro ao realizar votação. Confira se a sessão foi fechada ou se o associado já realizou o voto";

     private final SessaoRepository repository;
     private final VotoRepository votoRepository;
     private final PautaRepository pautaRepository;

     private final SessaoConverter converter;
     private final VotoConverter votoConverter;

     private final PautaService pautaService;
     private final AssociadoService associadoService;



     @Transactional
     public SessaoResponseModel iniciarSessao(SessaoRequestModel sessaoRequestModel) {

          try {
               Pauta pauta = pautaService.buscarPautaPorId(sessaoRequestModel.getIdPauta());

               Sessao sessao = converter.toSessao(sessaoRequestModel, pauta);

               validarPropriedadesSessaoParaInsercao(sessao);

               validaTempoSessao(sessao);

               sessao = repository.save(sessao);

               return converter.toSessaoResponseModel(sessao);

          } catch (Exception exception) {
               throw new FalhaAoIniciarSessaoException(exception);
          }
     }

     @Transactional
     public VotoResponseModel votar(VotoRequestModel votoRequestModel) {

          try {
               Associado associado = associadoService.buscarAssociadoPorId(votoRequestModel.getIdAssociado());
               Pauta pauta = pautaService.buscarPautaPorId(votoRequestModel.getIdPauta());
               Sessao sessao = buscarSessaoPorId(votoRequestModel.getIdSessao());

               if (validarSessao(sessao) || validarVotacaoAssociado(sessao, associado))
                    throw new FalhaValidacaoSessaoException(MSG_ERROR_SESSAO_VOTACAO);

               Voto voto = votoConverter.toVoto(votoRequestModel, sessao, associado, pauta);
               voto = votoRepository.save(voto);

               return votoConverter.toVotoResponseModel(voto);

          } catch (Exception exception) {
               throw new FalhaAoRealizarVotacaoException(exception);
          }

     }

     public TotalVotosResponseModel buscarTotalVotosPorIdPauta(Long idPauta) throws FalhaAoBuscarPautaException {

          try {
               Pauta pauta = pautaService.buscarPautaPorId(idPauta);

               List<Voto> votos = votoRepository.findByPauta(pauta);

               Integer totalVotosSim = calcularVotos(votos, TipoVoto.SIM, false);

               Integer totalVotosNao = calcularVotos(votos, TipoVoto.NAO, false);

               Integer totalVotos = calcularVotos(votos, null, true);


               pauta.setStatusPauta(resultadoPauta(totalVotosSim, totalVotosNao));
               pautaRepository.save(pauta);

               return votoConverter.toContabilzaVotosResponseModel(pauta, totalVotosSim, totalVotosNao, totalVotos);

          } catch (Exception exception) {
               throw new FalhaAoRealizarVotacaoException(exception);
          }

     }

     @Transactional
     public SessaoResponseModel atualizar(Long id, SessaoRequestModel sessaoRequestModel) {

          try {
               Pauta pauta = pautaService.buscarPautaPorId(sessaoRequestModel.getIdPauta());

               Sessao sessaoRequest = converter.toSessao(sessaoRequestModel, pauta);
               Sessao sessao = buscarSessaoPorId(id);

               atualizarValoresSessao(sessaoRequest, sessao);

               repository.save(sessao);

               return converter.toSessaoResponseModel(sessao);

          } catch (Exception exception) {
               throw new FalhaAoAtualizarSessaoException(exception);
          }
     }

     public List<SessaoResponseModel> buscarTodos(Pageable page) {
          try {
               List<Sessao> sessoes = repository.findAll(page).getContent();

               return isEmpty(sessoes) ? null : converter.toListSessaoResponse(sessoes);

          } catch (Exception exception) {
               throw new FalhaAoBuscarSessoesException(exception);
          }
     }

     public SessaoResponseModel buscarPorId(Long id) {

          try {
               Sessao sessao = buscarSessaoPorId(id);

               return converter.toSessaoResponseModel(sessao);

          } catch (Exception exception) {
               throw new FalhaAoBuscarSessaoPorIdException(exception);
          }
     }

     public void deletar(Long id) {

          try {
               Sessao sessao = buscarSessaoPorId(id);

               repository.delete(sessao);

          } catch (Exception exception) {
               throw new FalhaAoDeletarSessaoException(exception);
          }
     }

     public Sessao buscarSessaoPorId(Long id) throws FalhaAoBuscarSessaoException {
          return repository.findById(id).orElseThrow(() -> new FalhaAoBuscarSessaoException(format(MSG_SESSAO_NAO_ENCONTRADO_PELO_ID, id)));
     }

     private void validarPropriedadesSessaoParaInsercao(Sessao sessao) throws FalhaValidacaoSessaoException {

          if (isEmpty(sessao.getPauta()))
               throw new FalhaValidacaoSessaoException(MSG_SESSAO_VAZIO);
     }

     private Sessao atualizarValoresSessao(Sessao sessaoRequest, Sessao sessaoDatabase) {

          if (isPropriedadeFoiAlterada(sessaoRequest.getDuracao(), sessaoDatabase.getDuracao()))
               sessaoDatabase.setDuracao(sessaoRequest.getDuracao());

          if (isPropriedadeFoiAlterada(sessaoRequest.getPauta(), sessaoDatabase.getPauta()))
               sessaoDatabase.setPauta(sessaoRequest.getPauta());

          if (isPropriedadeFoiAlterada(sessaoRequest.getFechamento(), sessaoDatabase.getFechamento()))
               sessaoDatabase.setFechamento(sessaoRequest.getFechamento());

          if (isPropriedadeFoiAlterada(sessaoRequest.isAtivo(), sessaoDatabase.isAtivo()))
               sessaoDatabase.setAtivo(sessaoRequest.isAtivo());

          return sessaoDatabase;
     }

     private boolean isPropriedadeFoiAlterada(Object objectRequest, Object objectDatabase) {
          return !isEmpty(objectRequest) &&
                    !objectRequest.equals(objectDatabase);
     }

     public Sessao validaTempoSessao(Sessao sessao) throws FalhaDataFechamentoSessaoException {
          LocalDateTime tempoFechamento;

          if (isEmpty(sessao.getDuracao())) {
               tempoFechamento = LocalDateTime.now().plusMinutes(1);
          } else {
               tempoFechamento = LocalDateTime.now().plusMinutes(sessao.getDuracao());
          }

          if (tempoFechamento.isBefore(LocalDateTime.now()) || tempoFechamento.isEqual(LocalDateTime.now())) {
               new FalhaDataFechamentoSessaoException(format(MSG_ERROR_FECHAMENTO_SESSAO, tempoFechamento));
          }

          sessao.setFechamento(tempoFechamento);

          return sessao;
     }

     private boolean validarSessao(Sessao sessao) {
          return isEmpty(sessao) || sessao.getFechamento().isBefore(LocalDateTime.now()) ||
                    !sessao.isAtivo();
     }

     private boolean validarVotacaoAssociado(Sessao sessao, Associado associado) {
          Optional<Associado> result = sessao.getVotos()
                    .stream()
                    .filter(voto -> voto.getAssociado().getId().equals(associado.getId()))
                    .map(Voto::getAssociado)
                    .findFirst();

          return  result.isPresent();
     }

     public Integer calcularVotos(List<Voto> votos, TipoVoto tipoVoto , boolean isTotal) {

          return isTotal
                    ? votos.stream()
                    .filter(voto -> !isEmpty(voto.getTipoVoto()))
                    .collect(Collectors.toList())
                    .size()
                    : votos.stream()
                    .filter(voto -> voto.getTipoVoto() == tipoVoto)
                    .collect(Collectors.toList())
                    .size();
     }

     public StatusPauta resultadoPauta(Integer votosSim, Integer votosNao) {
          return votosSim > votosNao ? StatusPauta.APROVADA : StatusPauta.RECUSADA;
     }
}
