package com.desafio.sicredi.exceptions.sessao;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarSessoesException extends HttpErrorException {

    private static final long serialVersionUID = -1194951991401319451L;

    private static final String MSG_ERROR_BUSCAR_SESSOES = "Error ao buscar sessões, tente novamente mais tarde. ";

    public FalhaAoBuscarSessoesException(Exception exception) {
        super(MSG_ERROR_BUSCAR_SESSOES, exception.getMessage(), exception);
    }
}
