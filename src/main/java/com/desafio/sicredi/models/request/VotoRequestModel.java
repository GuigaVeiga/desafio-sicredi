package com.desafio.sicredi.models.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.desafio.sicredi.models.enums.TipoVoto;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotoRequestModel implements Serializable {

     private static final long serialVersionUID = -5559435875025966144L;

     @NotNull(message = "Voto da sessão não pode ser nulo. ")
     @JsonProperty("tipoVoto")
     private TipoVoto tipoVoto;

     @NotNull(message = "ID da Sessão não pode ser nulo. ")
     @JsonProperty("idSessao")
     private Long idSessao;

     @NotNull(message = "ID do Associado não pode ser nulo. ")
     @JsonProperty("idAssociado")
     private Long idAssociado;

     @NotNull(message = "ID da Pauta não pode ser nulo. ")
     @JsonProperty("idPauta")
     private Long idPauta;

     /**
      * Voto da sessão.
      *
      * @return tipoVoto
      */
     @ApiModelProperty(example = "SIM", required = true, value = "Voto da sessão. ")
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
      * Identificador do Associado na sessão.
      *
      * @return idAssociado
      */
     @ApiModelProperty(example = "1", required = true, value = "ID do Associado da sessão. ")
     public Long getIdAssociado() {
          return idAssociado;
     }

     /**
      * Identificador da Pauta na sessão.
      *
      * @return idPauta
      */
     @ApiModelProperty(example = "1", required = true, value = "ID da Pauta da sessão. ")
     public Long getIdPauta() { return idPauta; }
}
