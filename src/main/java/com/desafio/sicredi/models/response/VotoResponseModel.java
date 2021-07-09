package com.desafio.sicredi.models.response;

import org.springframework.validation.annotation.Validated;

import com.desafio.sicredi.models.enums.TipoVoto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Validated
@Builder
@Setter
@Getter
public class VotoResponseModel {

     @JsonProperty("id")
     private Long id;

     @JsonProperty("tipoVoto")
     private TipoVoto tipoVoto;

     @JsonProperty("idSessao")
     private Long idSessao;

     @JsonProperty("idPauta")
     private Long idPauta;

     @JsonProperty("idAssociado")
     private Long idAssociado;


     /**
      * Voto da sessão.
      *
      * @return tipoVoto
      */
     @ApiModelProperty(example = "Voto da sessão", required = true, value = "Voto da sessão. ")
     public TipoVoto getTipoVoto() {
          return tipoVoto;
     }


     /**
      * Identificador da Sessão.
      *
      * @return idSessao
      */
     @ApiModelProperty(example = "1", required = true, value = "ID da Sessão. ")
     public Long getIdSessao() {
          return idSessao;
     }

     /**
      * Identificador da Pauta na sessão.
      *
      * @return idPauta
      */
     @ApiModelProperty(example = "1", required = true, value = "ID da Pauta da sessão. ")
     public Long getIdPauta() { return idPauta; }

     /**
      * Identificador do Associado da sessão.
      *
      * @return idAssociado
      */
     @ApiModelProperty(example = "1", required = true, value = "Identificador do Associado da sessão. ")
     public Long getIdAssociado() {
          return idAssociado;
     }

}
