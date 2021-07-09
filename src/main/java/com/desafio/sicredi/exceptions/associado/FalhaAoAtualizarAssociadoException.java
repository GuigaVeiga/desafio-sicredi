package com.desafio.sicredi.exceptions.associado;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoAtualizarAssociadoException extends HttpErrorException {

    private static final String MSG_ERROR_ATUALIZAR_ASSOCIADO = "Error ao atualizar associado, tente novamente mais tarde. ";
    private static final long serialVersionUID = -4723018915169355476L;

    public FalhaAoAtualizarAssociadoException(Exception exception) {
        super(MSG_ERROR_ATUALIZAR_ASSOCIADO, exception.getMessage(),  exception);
    }


}
