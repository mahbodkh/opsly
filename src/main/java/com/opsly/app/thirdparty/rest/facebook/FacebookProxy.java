package com.opsly.app.thirdparty.rest.facebook;

import com.opsly.app.thirdparty.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class FacebookProxy {

    private final RestProvider restProvider;
    @Value("${app.client.facebook.base-url}")
    private String facebookBaseUrl;

    public FacebookProxy(RestProvider restProvider) {
        this.restProvider = restProvider;
    }

    public CompletableFuture<String> loadStatus() {
        log.debug(" Third Party : Facebook REQUEST  -->  ");
        CompletableFuture<String> response = restProvider.doGet(facebookBaseUrl);
        log.debug(" Third Party : Facebook RESPONSE <--  ");
        return response;
    }

}
