package com.desafio.sicredi.controllers.associados;

import static com.desafio.sicredi.utils.HttpResponsesUtil.getURI;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.sicredi.models.request.AssociadoRequestModel;
import com.desafio.sicredi.models.response.AssociadoResponseModel;
import com.desafio.sicredi.services.AssociadoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AssociadosApiController implements AssociadosApi {

     public static final String DOMINIO_ASSOCIADOS = "associados";
     private final AssociadoService service;

     @Override
     public ResponseEntity<AssociadoResponseModel> criar(AssociadoRequestModel associadoRequestModel) {

          AssociadoResponseModel associadoResponseModel = service.criar(associadoRequestModel);

          return ResponseEntity.created(getURI(DOMINIO_ASSOCIADOS, associadoResponseModel.getId())).body(associadoResponseModel);
     }

     @Override
     public ResponseEntity<AssociadoResponseModel> atualizar(Long id, AssociadoRequestModel associadoRequestModel) {

          AssociadoResponseModel associadoResponseModel = service.atualizar(id, associadoRequestModel);

          return ResponseEntity.ok(associadoResponseModel);
     }

     @Override
     public ResponseEntity<AssociadoResponseModel> buscarPorId(Long id) {

          AssociadoResponseModel associadoResponseModel = service.buscarPorId(id);

          if (nonNull(associadoResponseModel)) return ResponseEntity.ok(associadoResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<List<AssociadoResponseModel>> buscarTodos(Pageable page) {

          List<AssociadoResponseModel> associadosResponseModel = service.buscarTodos(page);

          if (nonNull(associadosResponseModel)) return ResponseEntity.ok(associadosResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<Void> deletar(Long id) {

          service.deletar(id);

          return ResponseEntity.noContent().build();
     }
}
