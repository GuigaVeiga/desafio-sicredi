package com.desafio.sicred.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.desafio.sicred.models.entities.Associado;
import com.desafio.sicred.models.request.AssociadoRequestModel;
import com.desafio.sicred.models.response.AssociadoResponseModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AssociadoConverter {

     public Associado toAssociado(AssociadoRequestModel associadoRequestModel) {
          return Associado
                    .builder()
                    .nome(associadoRequestModel.getNome())
                    .cpf(associadoRequestModel.getCpf())
                    .build();
     }

     public List<AssociadoResponseModel> toListAssociadoResponse(List<Associado> associados) {

          return associados
                    .stream()
                    .map(this::toAssociadoResponseModel).collect(Collectors.toList());
     }

     public AssociadoResponseModel toAssociadoResponseModel(Associado associado) {

          return AssociadoResponseModel
                    .builder()
                    .id(associado.getId())
                    .nome(associado.getNome())
                    .cpf(associado.getCpf())
                    .build();
     }
}
