package com.desafio.sicredi.exceptions.sessao;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoBuscarSessaoPorIdException extends HttpErrorException {

    private static final String MSG_ERROR_BUSCAR_SESSAO_POR_ID = "Error ao buscar sessao por id, tente novamente mais tarde. ";
    private static final long serialVersionUID = -4912232385489256675L;

    public FalhaAoBuscarSessaoPorIdException(Exception exception) {
        super(MSG_ERROR_BUSCAR_SESSAO_POR_ID, exception.getMessage(), exception);

    }
}
