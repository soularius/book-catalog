package com.example.CHALENGER_3.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.CHALENGER_3.model.ApiResponse;

import java.net.http.HttpResponse;

public class ApiResponseHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ApiResponse handleResponse(HttpResponse<String> response) throws Exception {
        if (response.statusCode() == 200) {
            // Deserializar todo el objeto JSON en ApiResponse
            return objectMapper.readValue(response.body(), ApiResponse.class);
        } else {
            throw new RuntimeException("Error en la solicitud: " + response.statusCode());
        }
    }
}
