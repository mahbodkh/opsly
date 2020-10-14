package com.opsly.app.thirdparty.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Slf4j
public class RestProvider {

    public CompletableFuture<String> doGet(String url) {
        // Create an HttpClient with default configurations
        HttpClient client = HttpClient.newHttpClient();


        // Create a Get HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();


        // Send the request to the server synchronously handling the response body as a String
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(httpResponse -> {
                    int status = httpResponse.statusCode();

                    // exception handling with 400 status code
                    if (HttpStatus.valueOf(status).is4xxClientError()) {
                        log.debug(" request --> [thenApply block] problem: -->  [status code: {}] [body: {}] ", status, httpResponse.body());
                        return httpResponse.body();
                    } else if (HttpStatus.valueOf(status).is5xxServerError()) { // exception handling with 500 status code
                        log.debug(" request --> [thenApply block] problem: -->  [status code: {}] [body: {}] ", status, httpResponse.body());
                        return httpResponse.body();
                    } else
                        return httpResponse.body();
                })
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.debug(" request --> [whenComplete block] failed: --> {} ", ex.getMessage());
                    }
                }).exceptionally(throwable -> {
                    if (throwable instanceof CompletionException)
                        throwable = throwable.getCause();
                    log.debug(" request --> [exceptionally block] failed: --> {} ", throwable.getMessage());
                    throw new CompletionException(throwable);
                });


    }
}
