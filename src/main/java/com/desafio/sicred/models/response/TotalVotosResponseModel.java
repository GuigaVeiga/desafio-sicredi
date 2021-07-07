package com.desafio.sicred.models.response;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.desafio.sicred.models.enums.StatusPauta;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Validated
@Builder
@Setter
@Getter
public class TotalVotosResponseModel implements Serializable {

     @JsonProperty("idPauta")
     private Long idPauta;

     @JsonProperty("titulo")
     private String titulo;

     @JsonProperty("statusPauta")
     private StatusPauta statusPauta;

     @JsonProperty("votosSim")
     private Integer votosSim;

     @JsonProperty("votosNao")
     private Integer votosNao;

     @JsonProperty("totalVotos")
     private Integer totalVotos;


     /**
      * Identificador da Pauta na votação.
      *
      * @return idPauta
      */
     @ApiModelProperty(example = "1", value = "Identificador da Pauta na votação. ")
     public Long getIdPauta() {
          return idPauta;
     }

     /**
      * Título da Pauta na votação.
      *
      * @return titulo
      */
     @ApiModelProperty(example = "Prestação de contas 2020", value = "Título da Pauta na votação. ")
     public String getTitulo() {
          return titulo;
     }

     /**
      * Status da Pauta na votação.
      *
      * @return statusPauta
      */
     @ApiModelProperty(example = "APROVADA", value = "Status da Pauta na votação. ")
     public StatusPauta getStatusPauta() {
          return statusPauta;
     }

     /**
      * Total de Votos Sim da Pauta.
      *
      * @return votosSim
      */
     @ApiModelProperty(example = "10", value = "Total de Votos Sim da Pauta. ")
     public Integer getVotosSim() {
          return votosSim;
     }

     /**
      * Total de Votos Não da Pauta.
      *
      * @return votosNao
      */
     @ApiModelProperty(example = "10", value = "Total de Votos Não da Pauta. ")
     public Integer getVotosNao() {
          return votosNao;
     }

     /**
      * Total de Votos da Pauta.
      *
      * @return totalVotos
      */
     @ApiModelProperty(example = "10", value = "Total de Votos da Pauta. ")
     public Integer getTotalVotos() {
          return totalVotos;
     }

}
