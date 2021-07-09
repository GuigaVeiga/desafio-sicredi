package com.desafio.sicredi.exceptions.sessao;

public class FalhaValidacaoSessaoException extends Exception {

     private static final long serialVersionUID = 7564950744176825616L;

     public FalhaValidacaoSessaoException(String mensagem) {
          super(mensagem);
     }
}
