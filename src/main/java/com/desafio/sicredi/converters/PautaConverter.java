package com.desafio.sicredi.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.desafio.sicredi.models.entities.Pauta;
import com.desafio.sicredi.models.request.PautaRequestModel;
import com.desafio.sicredi.models.response.PautaResponseModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PautaConverter {

     public Pauta toPauta(PautaRequestModel pautaRequestModel) {
          return Pauta
                    .builder()
                    .titulo(pautaRequestModel.getTitulo())
                    .descricao(pautaRequestModel.getDescricao())
                    .build();
     }

     public List<PautaResponseModel> toListPautaResponse(List<Pauta> pautas) {

          return pautas
                    .stream()
                    .map(this::toPautaResponseModel).collect(Collectors.toList());
     }

     public PautaResponseModel toPautaResponseModel(Pauta pauta) {

          return PautaResponseModel
                    .builder()
                    .id(pauta.getId())
                    .titulo(pauta.getTitulo())
                    .descricao(pauta.getDescricao())
                    .build();
     }
}
