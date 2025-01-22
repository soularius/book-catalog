package com.example.CHALENGER_3.httpclient;

import java.net.http.HttpClient;
import java.time.Duration;

public class ApiClient {
    private static final HttpClient CLIENT = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL) // Habilitar redirecciones automáticas
            .connectTimeout(Duration.ofSeconds(50))     // Configurar tiempo de espera
            .version(HttpClient.Version.HTTP_2)         // Usar HTTP/2
            .build();

    public static HttpClient getClient() {
        return CLIENT;
    }
}
