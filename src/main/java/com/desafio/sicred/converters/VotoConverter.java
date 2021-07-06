package com.desafio.sicred.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.desafio.sicred.models.entities.Associado;
import com.desafio.sicred.models.entities.Pauta;
import com.desafio.sicred.models.entities.Sessao;
import com.desafio.sicred.models.entities.Voto;
import com.desafio.sicred.models.request.VotoRequestModel;
import com.desafio.sicred.models.response.TotalVotosResponseModel;
import com.desafio.sicred.models.response.VotoResponseModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class VotoConverter {

     public Voto toVoto(VotoRequestModel votoRequestModel, Sessao sessao, Associado associado, Pauta pauta) {
          return Voto
                    .builder()
                    .tipoVoto(votoRequestModel.getTipoVoto())
                    .sessao(sessao)
                    .associado(associado)
                    .pauta(pauta)
                    .build();
     }

     public List<VotoResponseModel> toListVotoResponse(List<Voto> votos) {

          return votos
                    .stream()
                    .map(this::toVotoResponseModel).collect(Collectors.toList());
     }

     public VotoResponseModel toVotoResponseModel(Voto voto) {

          return VotoResponseModel
                    .builder()
                    .id(voto.getId())
                    .tipoVoto(voto.getTipoVoto())
                    .idSessao(voto.getSessao().getId())
                    .idAssociado(voto.getAssociado().getId())
                    .idPauta(voto.getPauta().getId())
                    .build();
     }

     public TotalVotosResponseModel toContabilzaVotosResponseModel(Pauta pauta, Integer votosSim, Integer votosNao, Integer total) {

          return TotalVotosResponseModel
                    .builder()
                    .idPauta(pauta.getId())
                    .titulo(pauta.getTitulo())
                    .statusPauta(pauta.getStatusPauta())
                    .votosSim(votosSim)
                    .votosNao(votosNao)
                    .totalVotos(total)
                    .build();
     }
}
