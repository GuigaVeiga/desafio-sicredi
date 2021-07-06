package com.desafio.sicred.models.response;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.desafio.sicred.models.entities.Pauta;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Validated
@Builder
@Setter
@Getter
public class SessaoResponseModel {

     @JsonProperty("id")
     private Long id;

     @JsonProperty("duracao")
     private Long duracao;

     @JsonProperty("abertura")
     private LocalDateTime abertura;

     @JsonProperty("fechamento")
     private LocalDateTime fechamento;

     @JsonProperty("ativo")
     private boolean ativo;

     @JsonProperty("idPauta")
     private Long idPauta;

     /**
      * Identificador da sessão.
      *
      * @return id
      **/
     @ApiModelProperty(example = "1", required = true, value = "Identificador da Sessão. ")
     public Long getId() {
          return id;
     }

     /**
      * Duração da sessão.
      *
      * @return duracao
      */
     @ApiModelProperty(example = "1", required = true, value = "Tempo de duração da sessão. ")
     public Long getDuracao() {
          return duracao;
     }

     /**
      * Fechamento da sessão.
      *
      * @return fechamento
      */
     @ApiModelProperty(example = "Data e Hora de fechamento da sessão", required = true, value = "fechamento da sessão. ")
     public LocalDateTime getFechamento() {
          return fechamento;
     }

     /**
      * Abertura da sessão.
      *
      * @return abertura
      */
     @ApiModelProperty(example = "Data e Hora de abertura da sessão", required = true, value = "abertura da sessão. ")
     public LocalDateTime getAbertura() {
          return abertura;
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
