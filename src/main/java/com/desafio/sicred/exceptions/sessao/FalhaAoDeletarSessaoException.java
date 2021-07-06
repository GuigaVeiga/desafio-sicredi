package com.desafio.sicred.exceptions.sessao;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoDeletarSessaoException extends HttpErrorException {

    private static final String MSG_ERROR_DELETAR_SESSAO = "Error ao deletar sessão, tente novamente mais tarde. ";
    private static final long serialVersionUID = 8078824133850066956L;

    public FalhaAoDeletarSessaoException(Exception exception) {
        super(MSG_ERROR_DELETAR_SESSAO, exception.getMessage(), exception);
    }
}
