package com.desafio.sicred.models.request;

import java.io.Serializable;

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
public class PautaRequestModel implements Serializable {

     private static final long serialVersionUID = 6422974960548370138L;

     @NotNull(message = "Título da pauta não pode ser nulo. ")
     @JsonProperty("titulo")
     private String titulo;

     @NotNull(message = "Descrição da pauta não pode ser nulo. ")
     @JsonProperty("descricao")
     private String descricao;

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
