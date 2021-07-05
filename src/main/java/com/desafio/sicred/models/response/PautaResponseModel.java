package com.desafio.sicred.models.response;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Validated
@Builder
@Setter
@Getter
public class PautaResponseModel {

     @JsonProperty("id")
     private Long id;

     @JsonProperty("titulo")
     private String titulo;

     @JsonProperty("descricao")
     private String descricao;

     /**
      * Identificador do associado.
      *
      * @return id
      **/
     @ApiModelProperty(example = "1", required = true, value = "Identificador da Pauta. ")
     public Long getId() {
          return id;
     }

     /**
      * Titulo da pauta.
      *
      * @return titulo
      */
     @ApiModelProperty(example = "Prestação de Contas 2020", required = true, value = "Título da pauta. ")
     public String getTitulo() {
          return titulo;
     }

     /**
      * Descricao da pauta.
      *
      * @return descricao
      */
     @ApiModelProperty(example = "Demonstração do que foi feito com os recursos", required = true, value = "Descrição da pauta. ")
     public String getDescricao() {
          return descricao;
     }
}
