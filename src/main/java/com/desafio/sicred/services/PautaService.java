package com.desafio.sicred.services;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.sicred.converters.PautaConverter;
import com.desafio.sicred.exceptions.pauta.FalhaAoAtualizarPautaException;
import com.desafio.sicred.exceptions.pauta.FalhaAoBuscarPautaException;
import com.desafio.sicred.exceptions.pauta.FalhaAoBuscarPautaPorIdException;
import com.desafio.sicred.exceptions.pauta.FalhaAoBuscarPautasException;
import com.desafio.sicred.exceptions.pauta.FalhaAoCriarPautaException;
import com.desafio.sicred.exceptions.pauta.FalhaAoDeletarPautaException;
import com.desafio.sicred.exceptions.pauta.FalhaValidacaoPautaException;
import com.desafio.sicred.models.entities.Pauta;
import com.desafio.sicred.models.request.PautaRequestModel;
import com.desafio.sicred.models.response.PautaResponseModel;
import com.desafio.sicred.repositories.PautaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PautaService {

     private static final String MSG_PAUTA_VAZIO = "Título e Descrição da pauta vazio. Favor preencher o campo. ";
     private static final String MSG_PAUTA_NAO_ENCONTRADO_PELO_ID = "Pauta não encontrada na base de dados. Confira se o id esta correto. ID: %s";

     private final PautaRepository repository;
     private final PautaConverter converter;

     @Transactional
     public PautaResponseModel criar(PautaRequestModel pautaRequestModel) {

          try {
               Pauta pauta = converter.toPauta(pautaRequestModel);

               validarPropriedadesPautaParaInsercao(pauta);

               pauta = repository.save(pauta);

               return converter.toPautaResponseModel(pauta);

          } catch (Exception exception) {
               throw new FalhaAoCriarPautaException(exception);
          }
     }

     @Transactional
     public PautaResponseModel atualizar(Long id, PautaRequestModel pautaRequestModel) {

          try {
               Pauta pautaRequest = converter.toPauta(pautaRequestModel);
               Pauta pauta = buscarPautaPorId(id);

               atualizarValoresPauta(pautaRequest, pauta);

               repository.save(pauta);

               return converter.toPautaResponseModel(pauta);

          } catch (Exception exception) {
               throw new FalhaAoAtualizarPautaException(exception);
          }
     }

     public List<PautaResponseModel> buscarTodos(Pageable page) {
          try {
               List<Pauta> pautas = repository.findAll(page).getContent();

               return isEmpty(pautas) ? null : converter.toListPautaResponse(pautas);
          } catch (Exception exception) {
               throw new FalhaAoBuscarPautasException(exception);
          }
     }

     public PautaResponseModel buscarPorId(Long id) {

          try {
               Pauta pauta = buscarPautaPorId(id);

               return converter.toPautaResponseModel(pauta);

          } catch (Exception exception) {
               throw new FalhaAoBuscarPautaPorIdException(exception);
          }
     }

     public void deletar(Long id) {

          try {
               Pauta pauta = buscarPautaPorId(id);

               repository.delete(pauta);

          } catch (Exception exception) {
               throw new FalhaAoDeletarPautaException(exception);
          }
     }

     public Pauta buscarPautaPorId(Long id) throws FalhaAoBuscarPautaException {
          return repository.findById(id).orElseThrow(() -> new FalhaAoBuscarPautaException(format(MSG_PAUTA_NAO_ENCONTRADO_PELO_ID, id)));
     }

     private void validarPropriedadesPautaParaInsercao(Pauta pauta) throws FalhaValidacaoPautaException {

          if (isEmpty(pauta.getTitulo()) && isEmpty(pauta.getDescricao()))
               throw new FalhaValidacaoPautaException(MSG_PAUTA_VAZIO);
     }

     private Pauta atualizarValoresPauta(Pauta pautaRequest, Pauta pautaDatabase) {

          if (isPropriedadeFoiAlterada(pautaDatabase.getTitulo(), pautaRequest.getTitulo()))
               pautaDatabase.setTitulo(pautaRequest.getTitulo());

          if (isPropriedadeFoiAlterada(pautaDatabase.getDescricao(), pautaRequest.getDescricao()))
               pautaDatabase.setDescricao(pautaRequest.getDescricao());

          return pautaDatabase;
     }

     private boolean isPropriedadeFoiAlterada(Object objectRequest, Object objectDatabase) {
          return !isEmpty(objectRequest) &&
                    !objectRequest.equals(objectDatabase);
     }
}
