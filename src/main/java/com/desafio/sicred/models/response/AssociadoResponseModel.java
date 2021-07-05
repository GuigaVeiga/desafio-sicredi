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
public class AssociadoResponseModel {

     @JsonProperty("id")
     private Long id;

     @JsonProperty("nome")
     private String nome;

     @JsonProperty("cpf")
     private String cpf;

     /**
      * Identificador do associado.
      *
      * @return id
      **/
     @ApiModelProperty(example = "1", required = true, value = "Identificador do associado. ")
     public Long getId() {
          return id;
     }

     /**
      * Nome do associado.
      *
      * @return nome
      */
     @ApiModelProperty(example = "Guilherme", required = true, value = "Nome do associado. ")
     public String getNome() {
          return nome;
     }

     /**
      * CPF do associado.
      *
      * @return cpf
      */
     @ApiModelProperty(example = "35462225040", required = true, value = "CPF do associado. ")
     public String getCpf() {
          return cpf;
     }
}
