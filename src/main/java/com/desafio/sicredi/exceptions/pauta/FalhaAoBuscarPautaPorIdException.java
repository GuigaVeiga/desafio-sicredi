package com.desafio.sicredi.exceptions.pauta;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarPautaPorIdException extends HttpErrorException {

    private static final String MSG_ERROR_BUSCAR_PAUTA_POR_ID = "Error ao buscar pauta por id, tente novamente mais tarde. ";
    private static final long serialVersionUID = -1588860362234022474L;

    public FalhaAoBuscarPautaPorIdException(Exception exception) {
        super(MSG_ERROR_BUSCAR_PAUTA_POR_ID, exception.getMessage(), exception);

    }
}
