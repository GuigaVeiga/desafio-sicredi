package com.desafio.sicredi.exceptions.pauta;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoDeletarPautaException extends HttpErrorException {

    private static final String MSG_ERROR_DELETAR_PAUTA = "Error ao deletar pauta, tente novamente mais tarde. ";
    private static final long serialVersionUID = 1874661597748883949L;

    public FalhaAoDeletarPautaException(Exception exception) {
        super(MSG_ERROR_DELETAR_PAUTA, exception.getMessage(), exception);
    }
}
