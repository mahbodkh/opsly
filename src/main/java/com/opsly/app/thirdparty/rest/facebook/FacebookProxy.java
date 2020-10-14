package com.opsly.app.thirdparty.rest.facebook;

import com.opsly.app.thirdparty.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class FacebookProxy {

    @Value("${app.client.facebook.base-url}")
    private String facebookBaseUrl;

    private @Autowired
    RestProvider restProvider;

    public CompletableFuture<String> loadStatus() {
        log.debug("");
        CompletableFuture<String> response
                = restProvider.doGet(facebookBaseUrl);
        log.debug("");
        return response;
    }

}
