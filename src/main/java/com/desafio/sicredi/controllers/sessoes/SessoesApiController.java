package com.desafio.sicredi.controllers.sessoes;

import static com.desafio.sicredi.utils.HttpResponsesUtil.getURI;
import static java.util.Objects.nonNull;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.sicredi.exceptions.pauta.FalhaAoBuscarPautaException;
import com.desafio.sicredi.models.request.SessaoRequestModel;
import com.desafio.sicredi.models.request.VotoRequestModel;
import com.desafio.sicredi.models.response.SessaoResponseModel;
import com.desafio.sicredi.models.response.TotalVotosResponseModel;
import com.desafio.sicredi.models.response.VotoResponseModel;
import com.desafio.sicredi.services.SessaoService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class SessoesApiController implements SessoesApi {

     public static final String DOMINIO_PAUTAS = "sessoes";
     private final SessaoService service;

     @Override
     public ResponseEntity<SessaoResponseModel> iniciarSessao(SessaoRequestModel sessaoRequestModel) {

          SessaoResponseModel sessaoResponseModel = service.iniciarSessao(sessaoRequestModel);

          return ResponseEntity.created(getURI(DOMINIO_PAUTAS, sessaoResponseModel.getId())).body(sessaoResponseModel);
     }

     @Override
     public ResponseEntity<SessaoResponseModel> atualizar(Long id, SessaoRequestModel sessaoRequestModel) {

          SessaoResponseModel sessaoResponseModel = service.atualizar(id, sessaoRequestModel);

          return ResponseEntity.ok(sessaoResponseModel);
     }

     @Override
     public ResponseEntity<SessaoResponseModel> buscarPorId(Long id) {

          SessaoResponseModel sessaoResponseModel = service.buscarPorId(id);

          if (nonNull(sessaoResponseModel)) return ResponseEntity.ok(sessaoResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<List<SessaoResponseModel>> buscarTodos(Pageable page) {

          List<SessaoResponseModel> sessoesResponseModel = service.buscarTodos(page);

          if (nonNull(sessoesResponseModel)) return ResponseEntity.ok(sessoesResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<Void> deletar(Long id) {

          service.deletar(id);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<VotoResponseModel> votar(VotoRequestModel votoRequestModel) {

          VotoResponseModel votoResponseModel = service.votar(votoRequestModel);

          if (nonNull(votoResponseModel)) return ResponseEntity.ok(votoResponseModel);

          return ResponseEntity.noContent().build();
     }

     @Override
     public ResponseEntity<TotalVotosResponseModel> contabilizarTotalVotosFechandoSessao(Long id, Long idSessao) throws FalhaAoBuscarPautaException {

          TotalVotosResponseModel totalVotosResponseModel = service.contabilizarTotalVotosFechandoSessao(id, idSessao);

          if (nonNull(totalVotosResponseModel)) return ResponseEntity.ok(totalVotosResponseModel);

          return ResponseEntity.noContent().build();
     }
}
