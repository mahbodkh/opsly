package com.opsly.app.integration.rest.twitter;

import com.opsly.app.integration.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class TwitterProxy {

    private final RestProvider restProvider;
    @Value("${app.client.twitter.base-url}")
    private String twitterBaseUrl;

    public TwitterProxy(RestProvider restProvider) {
        this.restProvider = restProvider;
    }


    public CompletableFuture<String> loadTweet() {
        log.debug(" Third Party : Twitter REQUEST  -->  ");
        CompletableFuture<String> response = restProvider.doGet(twitterBaseUrl);
        log.debug(" Third Party : Twitter RESPONSE <--  ");
        return response;
    }

}
