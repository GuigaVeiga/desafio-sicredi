package com.desafio.sicred.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseModel {

    @JsonProperty("mensagem")
    private String mensagem;

    @JsonProperty("mensagemDetalhe")
    private String mensagemDetalhe;


    @ApiModelProperty(example = "Erro ao inserir nova pauta na base de dados. ", value = "Descrição do erro. ")
    public String getMensagem() {
        return mensagem;
    }

    @ApiModelProperty(example = "Erro ao converter dados  - Null Pointer Exception line 68", value = "Stack do erro/ mais info sobre o erro  ")
    public String getMensagemDetalhe() {
        return mensagemDetalhe;
    }

}

