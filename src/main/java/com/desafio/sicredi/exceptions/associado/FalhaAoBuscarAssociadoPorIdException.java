package com.desafio.sicredi.exceptions.associado;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarAssociadoPorIdException extends HttpErrorException {

    private static final String MSG_ERROR_BUSCAR_ASSOCIADO_POR_ID = "Error ao buscar associado por id, tente novamente mais tarde. ";
    private static final long serialVersionUID = -4197803608701520278L;

    public FalhaAoBuscarAssociadoPorIdException(Exception exception) {
        super(MSG_ERROR_BUSCAR_ASSOCIADO_POR_ID, exception.getMessage(), exception);

    }
}
