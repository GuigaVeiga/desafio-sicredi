package com.desafio.sicred.exceptions.associado;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoCriarAssociadoException extends HttpErrorException {

     private static final String MSG_ERROR_CRIAR_ASSOCIADO = "Error ao criar associado, tente novamente mais tarde. ";
     private static final long serialVersionUID = 1161266001457146393L;

     public FalhaAoCriarAssociadoException(Exception exception) {super(MSG_ERROR_CRIAR_ASSOCIADO, exception.getMessage(),  exception); }
}
