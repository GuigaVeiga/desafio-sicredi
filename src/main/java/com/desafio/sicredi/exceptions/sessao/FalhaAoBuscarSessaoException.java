package com.desafio.sicredi.exceptions.sessao;

public class FalhaAoBuscarSessaoException extends Exception {

    private static final long serialVersionUID = 8856085860912022503L;

    public FalhaAoBuscarSessaoException(String mensagem) {
        super(mensagem);
    }
}
