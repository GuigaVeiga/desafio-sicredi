package com.desafio.sicred.controllers.pautas;

import static com.desafio.sicred.utils.HttpResponsesUtil.getURI;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.sicred.models.request.PautaRequestModel;
import com.desafio.sicred.models.response.PautaResponseModel;
import com.desafio.sicred.services.PautaService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PautasApiController implements PautasApi {

     public static final String DOMINIO_PAUTAS = "pautas";
     private final PautaService service;

     @Override
     public ResponseEntity<PautaResponseModel> criar(PautaRequestModel pautaRequestModel) {

          PautaResponseModel pautaResponseModel = service.criar(pautaRequestModel);

          return ResponseEntity.created(getURI(DOMINIO_PAUTAS, pautaResponseModel.getId())).body(pautaResponseModel);
     }

     @Override
     public ResponseEntity<PautaResponseModel> atualizar(Long id, PautaRequestModel pautaRequestModel) {

          PautaResponseModel pautaResponseModel = service.atualizar(id, pautaRequestModel);

          return ResponseEntity.ok(pautaResponseModel);
     }

     @Override
     public ResponseEntity<PautaResponseModel> buscarPorId(Long id) {

          PautaResponseModel pautaResponseModel = service.buscarPorId(id);

          if (nonNull(pautaResponseModel)) return ResponseEntity.ok(pautaResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<List<PautaResponseModel>> buscarTodos(Pageable page) {

          List<PautaResponseModel> pautasResponseModel = service.buscarTodos(page);

          if (nonNull(pautasResponseModel)) return ResponseEntity.ok(pautasResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<Void> deletar(Long id) {

          service.deletar(id);

          return ResponseEntity.noContent().build();
     }
}
