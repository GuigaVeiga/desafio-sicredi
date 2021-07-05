package com.desafio.sicred.models.request;

import java.io.Serializable;

import javax.persistence.Column;
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
public class AssociadoRequestModel implements Serializable {

     private static final long serialVersionUID = -3544029289735101184L;

     @NotNull(message = "Nome do associado não pode ser nulo. ")
     @JsonProperty("nome")
     private String nome;

     @NotNull(message = "CPF do associado não pode ser nulo. ")
     @JsonProperty("cpf")
     private String cpf;

     /**
      * Nome do associado.
      *
      * @return nome
      */
     @ApiModelProperty(example = "Guilherme", required = true, value = "Nome do Associado. ")
     public String getNome() {
          return nome;
     }

     /**
      * Cpf do associado.
      *
      * @return cpf
      */
     @ApiModelProperty(example = "35462225040", required = true, value = "Cpf do Associado. ")
     public String getCpf() {
          return cpf;
     }
}
