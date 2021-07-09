package com.desafio.sicredi.utils;


import java.net.URI;

public class HttpResponsesUtil {

    public static URI getURI(String dominio, Long id) {
        return URI.create("/" + dominio + "/" + id);
    }

    public static URI getURI(String dominio) {
        return URI.create("/" + dominio);

    }
}
