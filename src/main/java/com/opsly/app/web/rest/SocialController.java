package com.opsly.app.web.rest;

import com.opsly.app.service.SocialService;
import com.opsly.app.web.dto.SocialResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @GetMapping(value = "/")
    public ResponseEntity<SocialResponse> getTweetsNonBlocking() throws ExecutionException, InterruptedException {
        // todo:: it should determine with webflux
        log.info("!");
        SocialResponse socialResponse = socialService.loadSocialAggregation();
        log.info("?");
        return Optional.ofNullable(socialResponse)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
}
