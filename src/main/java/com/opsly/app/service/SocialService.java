package com.opsly.app.service;


import com.opsly.app.domain.Facebook;
import com.opsly.app.domain.Instagram;
import com.opsly.app.domain.Twitter;
import com.opsly.app.thirdparty.rest.facebook.FacebookProxy;
import com.opsly.app.thirdparty.rest.instagram.InstagramProxy;
import com.opsly.app.thirdparty.rest.twitter.TwitterProxy;
import com.opsly.app.web.dto.SocialResponse;
import com.opsly.app.web.mapper.FacebookDtoMapper;
import com.opsly.app.web.mapper.InstagramDtoMapper;
import com.opsly.app.web.mapper.TwitterDtoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SocialService {

    private final ObjectMapper mapper;
    //
    private final InstagramProxy instagramProxy;
    private final TwitterProxy twitterProxy;
    private final FacebookProxy facebookProxy;
    //
    private final TwitterDtoMapper twitterDtoMapper;
    private final InstagramDtoMapper instagramDtoMapper;
    private final FacebookDtoMapper facebookDtoMapper;

   public SocialResponse loadSocialAggregation() {

        CompletableFuture<String> twitterCf = twitterProxy.loadTweet();
        CompletableFuture<String> facebookCf = facebookProxy.loadStatus();
        CompletableFuture<String> instagramCf = instagramProxy.loadPhotos();

        // wait until all request will be obtained
        CompletableFuture.allOf(twitterCf, facebookCf, instagramCf).join();
        List<Twitter> twitters = new ArrayList<>();
        List<Facebook> facebooks = new ArrayList<>();
        List<Instagram> instagrams = new ArrayList<>();

        try {
            String twitterResponse = twitterCf.get();
            log.debug(" twitter before mapping  --> {} ", twitterResponse);
            twitters = Arrays.asList(mapper.readValue(twitterResponse, Twitter[].class));
            log.debug(" twitter result mapping  <-- {} ", twitters);
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            log.error(" twitter exception: {} --> exception: {} ", twitters, e.getMessage());
        }

        try {
            String facebookResponse = facebookCf.get();
            log.debug(" facebook before mapping  --> {} ", facebookResponse);
            facebooks = Arrays.asList(mapper.readValue(facebookResponse, Facebook[].class));
            log.debug(" facebook result mapping  <-- {} ", facebooks);
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            log.error(" facebook exception: {} --> exception: {} ", facebooks, e.getMessage());
        }

        try {
            String instagramResponse = instagramCf.get();
            log.debug(" instagram before mapping  --> {} ", instagramResponse);
            instagrams = Arrays.asList(mapper.readValue(instagramResponse, Instagram[].class));
            log.debug(" instagram result mapping  <-- {} ", instagrams);
        } catch (InterruptedException | ExecutionException | JsonProcessingException e) {
            log.error(" instagram exception: {} --> exception: {} ", instagrams, e.getMessage());
        }

        return SocialResponse.builder()
                .twitterDtos(twitterDtoMapper.toDto(twitters))
                .facebookDtos(facebookDtoMapper.toDto(facebooks))
                .instagramDtos(instagramDtoMapper.toDto(instagrams))
                .build();
    }

}
