package com.desafio.sicredi.exceptions.pauta;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoCriarPautaException extends HttpErrorException {

     private static final String MSG_ERROR_CRIAR_PAUTA = "Error ao criar pauta, tente novamente mais tarde. ";
     private static final long serialVersionUID = -1228186271307414241L;

     public FalhaAoCriarPautaException(Exception exception) {super(MSG_ERROR_CRIAR_PAUTA, exception.getMessage(),  exception); }
}
