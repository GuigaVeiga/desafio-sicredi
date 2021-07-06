package com.desafio.sicred.models.request;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

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
public class SessaoRequestModel implements Serializable {

     private static final long serialVersionUID = 6422974960548370138L;

     @NotNull(message = "Duração da sessão não pode ser nulo. ")
     @JsonProperty("duracao")
     private Long duracao;

     @NotNull(message = "Status da sessão não pode ser nulo. ")
     @JsonProperty("ativo")
     private boolean ativo;

     @NotNull(message = "ID da Pauta não pode ser nulo. ")
     @JsonProperty("idPauta")
     private Long idPauta;

     /**
      * Tempo de duração da sessão.
      *
      * @return duracao
      */
     @ApiModelProperty(example = "1", required = true, value = "Tempo de duração da sessão. ")
     public Long getDuracao() {
          return duracao;
     }

     /**
      * Status da sessão.
      *
      * @return ativo
      */
     @ApiModelProperty(example = "True", required = true, value = "Status da sessão. ")
     public boolean getAtivo() {
          return ativo;
     }

     /**
      * ID Pauta da sessão.
      *
      * @return idPauta
      */
     @ApiModelProperty(example = "1", required = true, value = "ID Pauta da sessão. ")
     public Long getIdPauta() {
          return idPauta;
     }
}
