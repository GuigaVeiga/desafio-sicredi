package com.desafio.sicred.services;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafio.sicred.converters.AssociadoConverter;
import com.desafio.sicred.exceptions.associado.FalhaAoAtualizarAssociadoException;
import com.desafio.sicred.exceptions.associado.FalhaAoBuscarAssociadoException;
import com.desafio.sicred.exceptions.associado.FalhaAoBuscarAssociadoPorIdException;
import com.desafio.sicred.exceptions.associado.FalhaAoBuscarAssociadosException;
import com.desafio.sicred.exceptions.associado.FalhaAoCriarAssociadoException;
import com.desafio.sicred.exceptions.associado.FalhaAoDeletarAssociadoException;
import com.desafio.sicred.exceptions.associado.FalhaValidacaoAssociadoException;
import com.desafio.sicred.models.entities.Associado;
import com.desafio.sicred.models.request.AssociadoRequestModel;
import com.desafio.sicred.models.response.AssociadoResponseModel;
import com.desafio.sicred.repositories.AssociadoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssociadoService {

     private static final String MSG_ASSOCIADO_VAZIO = "Nome e CPF do associado vazio. Favor preencher o campo. ";
     private static final String MSG_ASSOCIADO_NAO_ENCONTRADO_PELO_ID = "Associado não encontrado na base de dados. Confira se o id esta correto. ID: %s";

     private final AssociadoRepository repository;
     private final AssociadoConverter converter;

     @Transactional
     public AssociadoResponseModel criar(AssociadoRequestModel associadoRequestModel) {

          try {
               Associado associado = converter.toAssociado(associadoRequestModel);

               validarPropriedadesAssociadoParaInsercao(associado);

               associado = repository.save(associado);

               return converter.toAssociadoResponseModel(associado);

          } catch (Exception exception) {
               throw new FalhaAoCriarAssociadoException(exception);
          }
     }

     @Transactional
     public AssociadoResponseModel atualizar(Long id, AssociadoRequestModel associadoRequestModel) {

          try {
               Associado associadoRequest = converter.toAssociado(associadoRequestModel);
               Associado associado = buscarAssociadoPorId(id);

               atualizarValoresAssociado(associadoRequest, associado);

               repository.save(associado);

               return converter.toAssociadoResponseModel(associado);

          } catch (Exception exception) {
               throw new FalhaAoAtualizarAssociadoException(exception);
          }
     }

     public List<AssociadoResponseModel> buscarTodos(Pageable page) {
          try {
               List<Associado> associados = repository.findAll(page).getContent();

               return isEmpty(associados) ? null : converter.toListAssociadoResponse(associados);
          } catch (Exception exception) {
               throw new FalhaAoBuscarAssociadosException(exception);
          }
     }

     public AssociadoResponseModel buscarPorId(Long id) {

          try {
               Associado associado = buscarAssociadoPorId(id);

               return converter.toAssociadoResponseModel(associado);

          } catch (Exception exception) {
               throw new FalhaAoBuscarAssociadoPorIdException(exception);
          }
     }

     public void deletar(Long id) {

          try {
               Associado associado = buscarAssociadoPorId(id);

               repository.delete(associado);

          } catch (Exception exception) {
               throw new FalhaAoDeletarAssociadoException(exception);
          }
     }

     public Associado buscarAssociadoPorId(Long id) throws FalhaAoBuscarAssociadoException {
          return repository.findById(id).orElseThrow(() -> new FalhaAoBuscarAssociadoException(format(MSG_ASSOCIADO_NAO_ENCONTRADO_PELO_ID, id)));
     }

     private void validarPropriedadesAssociadoParaInsercao(Associado associado) throws FalhaValidacaoAssociadoException {

          if (isEmpty(associado.getNome()) && isEmpty(associado.getCpf()))
               throw new FalhaValidacaoAssociadoException(MSG_ASSOCIADO_VAZIO);
     }

     private Associado atualizarValoresAssociado(Associado associadoRequest, Associado associadodatabase) {

          if (isPropriedadeFoiAlterada(associadodatabase.getNome(), associadoRequest.getNome()))
               associadodatabase.setNome(associadoRequest.getNome());

          if (isPropriedadeFoiAlterada(associadodatabase.getCpf(), associadoRequest.getCpf()))
               associadodatabase.setNome(associadoRequest.getNome());

          return associadodatabase;
     }

     private boolean isPropriedadeFoiAlterada(Object objectRequest, Object objectDatabase) {
          return !isEmpty(objectRequest) &&
                    !objectRequest.equals(objectDatabase);
     }
}
