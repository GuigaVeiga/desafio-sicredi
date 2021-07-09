package com.desafio.sicredi.exceptions.associado;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarAssociadosException extends HttpErrorException {

    private static final long serialVersionUID = -3119068061892615121L;

    private static final String MSG_ERROR_BUSCAR_ASSOCIADOS = "Error ao buscar associados, tente novamente mais tarde. ";

    public FalhaAoBuscarAssociadosException(Exception exception) {
        super(MSG_ERROR_BUSCAR_ASSOCIADOS, exception.getMessage(), exception);
    }
}
