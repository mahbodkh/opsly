package com.opsly.app.thirdparty.rest.twitter;

import com.opsly.app.thirdparty.util.RestProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class TwitterProxy {

    @Value("${app.client.twitter.base-url}")
    private String twitterBaseUrl;

    private @Autowired
    RestProvider restProvider;


    public CompletableFuture<String> loadTweet() {
        log.debug("");
        CompletableFuture<String> response = restProvider.doGet(twitterBaseUrl);
        log.debug("");
        return response;
    }

}
