package com.desafio.sicredi.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.desafio.sicredi.models.entities.Pauta;
import com.desafio.sicredi.models.entities.Sessao;
import com.desafio.sicredi.models.request.SessaoRequestModel;
import com.desafio.sicredi.models.response.SessaoResponseModel;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SessaoConverter {

     public Sessao toSessao(SessaoRequestModel sessaoRequestModel, Pauta pauta) {
          return Sessao
                    .builder()
                    .duracao(sessaoRequestModel.getDuracao())
                    .ativo(sessaoRequestModel.getAtivo())
                    .pauta(pauta)
                    .build();
     }

     public List<SessaoResponseModel> toListSessaoResponse(List<Sessao> sessoes) {

          return sessoes
                    .stream()
                    .map(this::toSessaoResponseModel).collect(Collectors.toList());
     }

     public SessaoResponseModel toSessaoResponseModel(Sessao sessao) {

          return SessaoResponseModel
                    .builder()
                    .id(sessao.getId())
                    .duracao(sessao.getDuracao())
                    .abertura(sessao.getAbertura())
                    .fechamento(sessao.getFechamento())
                    .ativo(sessao.isAtivo())
                    .idPauta(sessao.getPauta().getId())
                    .build();
     }
}
