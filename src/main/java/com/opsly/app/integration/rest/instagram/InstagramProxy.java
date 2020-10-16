package com.opsly.app.integration.rest.instagram;

import com.opsly.app.integration.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class InstagramProxy {

    private final RestProvider restProvider;
    @Value("${app.client.instagram.base-url}")
    private String instagramBaseUrl;

    public InstagramProxy(RestProvider restProvider) {
        this.restProvider = restProvider;
    }

    public CompletableFuture<String> loadPhotos() {
        log.debug(" Third Party : Instagram REQUEST  -->  ");
        CompletableFuture<String> response = restProvider.doGet(instagramBaseUrl);
        log.debug(" Third Party : Instagram RESPONSE <--  ");
        return response;
    }
}
