package com.opsly.app.thirdparty.rest.instagram;

import com.opsly.app.thirdparty.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class InstagramProxy {

    @Value("${app.client.instagram.base-url}")
    private String instagramBaseUrl;


    private @Autowired
    RestProvider restProvider;

    public CompletableFuture<String> loadPhotos() {
        log.debug(" Third Party : Instagram REQUEST  -->  ");
        CompletableFuture<String> response = restProvider.doGet(instagramBaseUrl);
        log.debug(" Third Party : Instagram RESPONSE <--  {}", response);
        return response;
    }
}
