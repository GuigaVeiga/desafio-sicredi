package com.desafio.sicred.exceptions.voto;

import com.desafio.sicred.application.handler.HttpErrorException;

public class FalhaAoRealizarVotacaoException extends HttpErrorException {

     private static final String MSG_ERROR_CRIAR_SESSAO = "Error ao realizar votação, tente novamente mais tarde. ";
     private static final long serialVersionUID = -7647182030728847276L;

     public FalhaAoRealizarVotacaoException(Exception exception) {super(MSG_ERROR_CRIAR_SESSAO, exception.getMessage(),  exception); }
}
