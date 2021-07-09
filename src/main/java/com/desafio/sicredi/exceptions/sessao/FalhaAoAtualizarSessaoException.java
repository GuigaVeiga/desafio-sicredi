package com.desafio.sicredi.exceptions.sessao;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class FalhaAoAtualizarSessaoException extends HttpErrorException {

    private static final String MSG_ERROR_ATUALIZAR_SESSAO = "Error ao atualizar Sessão, tente novamente mais tarde. ";
    private static final long serialVersionUID = 3537842496753639641L;

    public FalhaAoAtualizarSessaoException(Exception exception) {
        super(MSG_ERROR_ATUALIZAR_SESSAO, exception.getMessage(),  exception);
    }


}
