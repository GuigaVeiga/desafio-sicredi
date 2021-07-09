package com.desafio.sicredi.services;

import static com.desafio.sicredi.ConstantsApplication.FILA_RESULTADO_VOTACAO;
import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.desafio.sicredi.application.queue.RabbitMQService;
import com.desafio.sicredi.converters.SessaoConverter;
import com.desafio.sicredi.converters.VotoConverter;
import com.desafio.sicredi.exceptions.pauta.FalhaAoBuscarPautaException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoAtualizarSessaoException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoBuscarSessaoException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoBuscarSessaoPorIdException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoBuscarSessoesException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoIniciarSessaoException;
import com.desafio.sicredi.exceptions.sessao.FalhaAoDeletarSessaoException;
import com.desafio.sicredi.exceptions.sessao.FalhaDataFechamentoSessaoException;
import com.desafio.sicredi.exceptions.sessao.FalhaValidacaoSessaoException;
import com.desafio.sicredi.exceptions.voto.FalhaAoRealizarVotacaoException;
import com.desafio.sicredi.models.entities.Associado;
import com.desafio.sicredi.models.entities.Pauta;
import com.desafio.sicredi.models.entities.Sessao;
import com.desafio.sicredi.models.entities.Voto;
import com.desafio.sicredi.models.enums.StatusPauta;
import com.desafio.sicredi.models.enums.StatusVotacao;
import com.desafio.sicredi.models.enums.TipoVoto;
import com.desafio.sicredi.models.request.SessaoRequestModel;
import com.desafio.sicredi.models.request.VotoRequestModel;
import com.desafio.sicredi.models.response.SessaoResponseModel;
import com.desafio.sicredi.models.response.TotalVotosResponseModel;
import com.desafio.sicredi.models.response.VotoResponseModel;
import com.desafio.sicredi.repositories.PautaRepository;
import com.desafio.sicredi.repositories.SessaoRepository;
import com.desafio.sicredi.repositories.VotoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessaoService {

     private static final String MSG_SESSAO_VAZIO = "Pauta da sessão vazio. Favor preencher o campo. ";
     private static final String MSG_SESSAO_NAO_ENCONTRADO_PELO_ID = "Sessão não encontrada na base de dados. Confira se o id esta correto. ID: %s";
     private static final String MSG_ERROR_FECHAMENTO_SESSAO = "Data e Hora do fechamento da Sessão é menor que a Atual. Data Fechamento: %s";
     private static final String MSG_ERROR_SESSAO_VOTACAO = "Erro ao realizar votação. Confira se a sessão foi fechada ou se o associado já realizou o voto";
     private static final String URI = "https://user-info.herokuapp.com/users/%s";

     private final SessaoRepository repository;
     private final VotoRepository votoRepository;
     private final PautaRepository pautaRepository;

     private final SessaoConverter converter;
     private final VotoConverter votoConverter;

     private final PautaService pautaService;
     private final AssociadoService associadoService;
     private final RabbitMQService rabbitMQService;



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

     @Transactional
     public TotalVotosResponseModel contabilizarTotalVotosFechandoSessao(Long idPauta, Long idSessao) throws FalhaAoBuscarPautaException {

          try {
               Pauta pauta = pautaService.buscarPautaPorId(idPauta);

               List<Voto> votos = votoRepository.findByPauta(pauta);

               Integer totalVotosSim = calcularVotos(votos, TipoVoto.SIM, false);

               Integer totalVotosNao = calcularVotos(votos, TipoVoto.NAO, false);

               Integer totalVotos = calcularVotos(votos, null, true);


               pauta.setStatusPauta(resultadoPauta(totalVotosSim, totalVotosNao));
               pautaRepository.save(pauta);

               TotalVotosResponseModel totalVotosResponseModel = votoConverter.toContabilzaVotosResponseModel(pauta, totalVotosSim, totalVotosNao, totalVotos);

               this.fecharSessao(idSessao, totalVotosResponseModel);

               return totalVotosResponseModel;

          } catch (Exception exception) {
               throw new FalhaAoRealizarVotacaoException(exception);
          }

     }

     @Transactional
     public void fecharSessao(Long idSessao, TotalVotosResponseModel totalVotosResponseModel) throws FalhaAoBuscarSessaoException {

          Sessao sessao = buscarSessaoPorId(idSessao);

          if (!validarSessao(sessao)) {
               sessao.setFechamento(LocalDateTime.now());
               sessao.setAtivo(false);
               repository.save(sessao);
          }

          rabbitMQService.send(FILA_RESULTADO_VOTACAO, totalVotosResponseModel);
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

          if (result.isPresent()) return true;

          return getStatusVotarAssociadoPeloCpf(associado.getCpf()).equals(StatusVotacao.UNABLE_TO_VOTE.name());
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

     private String getStatusVotarAssociadoPeloCpf(String cpf)
     {
          try {
               RestTemplate restTemplate = new RestTemplate();

               ResponseEntity<String> response = restTemplate.getForEntity(format(URI, cpf), String.class);

               ObjectMapper mapper = new ObjectMapper();

               JsonNode root = mapper.readTree(response.getBody());
               JsonNode status = root.path("status");

               return status.asText();

          } catch (JsonMappingException e) {
               e.printStackTrace();
               throw new FalhaAoRealizarVotacaoException(e);

          } catch (JsonProcessingException e) {
               e.printStackTrace();
               throw new FalhaAoRealizarVotacaoException(e);
          }
     }
}
