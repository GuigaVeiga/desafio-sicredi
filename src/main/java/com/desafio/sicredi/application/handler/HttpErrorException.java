package com.desafio.sicredi.application.handler;

public class HttpErrorException extends RuntimeException {

    private  static final long serialVersionUID = 1L;

    private final String messageDetail;


    public HttpErrorException(String message, String messageDetail, Throwable cause) {
        super(message, cause);
        this.messageDetail = messageDetail;

    }

    public HttpErrorException(String message, String messageDetail) {
        super(message);
        this.messageDetail = messageDetail;

    }


    public String getMessageDetail() {
        return messageDetail;
    }
}
