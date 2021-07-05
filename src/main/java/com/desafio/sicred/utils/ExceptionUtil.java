package com.desafio.sicred.utils;

import com.desafio.sicred.application.handler.HttpErrorException;

public class ExceptionUtil {

    public static String getMensagemExcecao(Exception exception) {
        String mensagemException = exception.getMessage();

        if (exception instanceof HttpErrorException) {
            mensagemException = ((HttpErrorException) exception).getMessageDetail();
        }
        return mensagemException;
    }

}
