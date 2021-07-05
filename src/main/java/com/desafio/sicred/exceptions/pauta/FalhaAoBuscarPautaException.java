package com.desafio.sicred.exceptions.pauta;

public class FalhaAoBuscarPautaException extends Exception {

    private static final long serialVersionUID = -2745576620625303609L;

    public FalhaAoBuscarPautaException(String mensagem) {
        super(mensagem);
    }
}
