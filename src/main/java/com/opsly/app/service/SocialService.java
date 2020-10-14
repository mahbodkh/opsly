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
@Log4j2
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

    public SocialResponse loadSocialAggregation() throws ExecutionException, InterruptedException {

        CompletableFuture<String> twitterCf = twitterProxy.loadTweet();
        CompletableFuture<String> facebookCf = facebookProxy.loadStatus();
        CompletableFuture<String> instagramCf = instagramProxy.loadPhotos();

        List<CompletableFuture<String>> allFutures = Arrays.asList(twitterCf, facebookCf, instagramCf);

        // wait until all request will be obtained
        CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.allOf(twitterCf, facebookCf, instagramCf)
                .thenApply(avoid -> allFutures  //start to collect them
                        .stream()
                        .map(CompletableFuture::join) //get List from feature. Here these cars has been obtained, therefore non blocking
                        .collect(Collectors.toList())
                );

        final List<String> join = listCompletableFuture.get();
        List<Twitter> twitters = new ArrayList<>();
        List<Facebook> facebooks = new ArrayList<>();
        List<Instagram> instagrams = new ArrayList<>();

        for (String result : join) {
            try {
                if (twitters.size() == 0) {
                    twitters =
                            Arrays.asList(mapper.readValue(result, Twitter[].class));

                } else if (facebooks.size() == 0) {
                    facebooks =
                            Arrays.asList(mapper.readValue(result, Facebook[].class));

                } else if (instagrams.size() == 0) {
                    instagrams =
                            Arrays.asList(mapper.readValue(result, Instagram[].class));

                }
            } catch (IOException e) {
                log.debug(" object binding result: {} --> exception: {} ", result, e.getMessage());
            }

        }
        return SocialResponse.builder()
                .twitterDtos(twitterDtoMapper.toDto(twitters))
                .facebookDtos(facebookDtoMapper.toDto(facebooks))
                .instagramDtos(instagramDtoMapper.toDto(instagrams))
                .build();
    }

}
