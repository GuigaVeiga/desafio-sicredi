package com.desafio.sicred.exceptions.associado;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoDeletarAssociadoException extends HttpErrorException {

    private static final String MSG_ERROR_DELETAR_ASSOCIADO = "Error ao deletar associado, tente novamente mais tarde. ";
    private static final long serialVersionUID = 6202388687311293277L;

    public FalhaAoDeletarAssociadoException(Exception exception) {
        super(MSG_ERROR_DELETAR_ASSOCIADO, exception.getMessage(), exception);
    }
}
