package com.desafio.sicred.exceptions.pauta;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoAtualizarPautaException extends HttpErrorException {

    private static final String MSG_ERROR_ATUALIZAR_PAUTA = "Error ao atualizar Pauta, tente novamente mais tarde. ";
    private static final long serialVersionUID = 7107087712292691737L;

    public FalhaAoAtualizarPautaException(Exception exception) {
        super(MSG_ERROR_ATUALIZAR_PAUTA, exception.getMessage(),  exception);
    }


}
