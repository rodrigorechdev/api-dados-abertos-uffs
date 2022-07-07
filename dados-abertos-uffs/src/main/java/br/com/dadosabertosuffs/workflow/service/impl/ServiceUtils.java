package br.com.dadosabertosuffs.workflow.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

abstract class ServiceUtils {
    
    private final HttpClient httpClient = HttpClient.newBuilder().build();

    protected HttpRequest criarRequest(URI uri) {
        return HttpRequest.newBuilder()
        .uri(uri)
        .GET()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
    }

    protected String obterResponseBody(HttpRequest httpRequest) throws IOException, InterruptedException {
        return httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();
    }

}
