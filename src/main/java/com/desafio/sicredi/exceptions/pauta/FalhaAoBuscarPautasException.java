package com.desafio.sicredi.exceptions.pauta;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarPautasException extends HttpErrorException {

    private static final long serialVersionUID = 7495199778539166899L;

    private static final String MSG_ERROR_BUSCAR_PAUTAS = "Error ao buscar pautas, tente novamente mais tarde. ";

    public FalhaAoBuscarPautasException(Exception exception) {
        super(MSG_ERROR_BUSCAR_PAUTAS, exception.getMessage(), exception);
    }
}
