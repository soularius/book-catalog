package com.example.CHALENGER_3.httpclient;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public class ApiRequestBuilder {
    public static HttpRequest buildGetRequest(String endpoint, String query) {
        try {
            // Codificar la palabra clave para que sea válida en una URL
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String fullUrl = endpoint + "?search=" + encodedQuery;

            return HttpRequest.newBuilder()
                    .uri(URI.create(fullUrl))
                    .GET()
                    .header("Accept", "application/json")
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al construir la solicitud: " + e.getMessage(), e);
        }
    }
}
