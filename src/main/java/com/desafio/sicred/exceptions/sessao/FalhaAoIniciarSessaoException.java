package com.desafio.sicred.exceptions.sessao;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoIniciarSessaoException extends HttpErrorException {

     private static final String MSG_ERROR_INICIAR_SESSAO = "Error ao iniciar sessão, tente novamente mais tarde. ";
     private static final long serialVersionUID = 5839564489592125551L;

     public FalhaAoIniciarSessaoException(Exception exception) {super(MSG_ERROR_INICIAR_SESSAO, exception.getMessage(),  exception); }
}
