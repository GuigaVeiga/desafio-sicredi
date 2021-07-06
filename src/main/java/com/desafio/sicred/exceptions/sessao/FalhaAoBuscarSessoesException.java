package com.desafio.sicred.exceptions.sessao;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoBuscarSessoesException extends HttpErrorException {

    private static final long serialVersionUID = -1194951991401319451L;

    private static final String MSG_ERROR_BUSCAR_SESSOES = "Error ao buscar sessões, tente novamente mais tarde. ";

    public FalhaAoBuscarSessoesException(Exception exception) {
        super(MSG_ERROR_BUSCAR_SESSOES, exception.getMessage(), exception);
    }
}
