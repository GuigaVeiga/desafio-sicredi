package com.desafio.sicredi.utils;

import com.desafio.sicredi.application.handler.HttpErrorException;

public class ExceptionUtil {

    public static String getMensagemExcecao(Exception exception) {
        String mensagemException = exception.getMessage();

        if (exception instanceof HttpErrorException) {
            mensagemException = ((HttpErrorException) exception).getMessageDetail();
        }
        return mensagemException;
    }

}
