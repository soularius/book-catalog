package com.example.CHALENGER_3.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private int count; // Número total de resultados
    private List<Book> results; // Lista de libros

    // Getters y setters
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Book> getResults() {
        return results;
    }

    public void setResults(List<Book> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "count=" + count +
                ", results=" + results +
                '}';
    }
}