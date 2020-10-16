package com.opsly.app.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.opsly.app.domain.Facebook;
import com.opsly.app.domain.Instagram;
import com.opsly.app.domain.Twitter;
import com.opsly.app.integration.rest.facebook.FacebookProxy;
import com.opsly.app.integration.rest.instagram.InstagramProxy;
import com.opsly.app.integration.rest.twitter.TwitterProxy;
import com.opsly.app.integration.util.RestProvider;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ThirdPartyProxyTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    @InjectMocks
    private SocialService socialService;
    @InjectMocks
    private FacebookProxy facebookProxy;
    @Spy
    private RestProvider restProvider;
    @InjectMocks
    private TwitterProxy twitterProxy;
    @InjectMocks
    private InstagramProxy instagramProxy;
    @Spy
    private ObjectMapper mapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(facebookProxy, "facebookBaseUrl", "https://takehome.io/facebook");
        ReflectionTestUtils.setField(instagramProxy, "instagramBaseUrl", "https://takehome.io/instagram");
        ReflectionTestUtils.setField(twitterProxy, "twitterBaseUrl", "https://takehome.io/twitter");
    }




    @Test(expected = MismatchedInputException.class)
    public void get_instagram_service_TEST() throws Exception {
        //************************
        //          GIVEN
        //************************


        //************************
        //          WHEN
        //************************
        CompletableFuture<String> instagramCF = instagramProxy.loadPhotos();
        final String instagramResponse = instagramCF.get();
        List<Instagram> instagrams = Arrays.asList(mapper.readValue(instagramResponse, Instagram[].class));


        //************************
        //          THEN
        //************************
        assertThat(instagramCF.get()).isNotEmpty();
        String expectedMessage = "404 page not found";

        exceptionRule.expect(MismatchedInputException.class);
        exceptionRule.expectMessage(expectedMessage);
    }

    @Test
    public void get_twitter_service_TEST() throws Exception {
        //************************
        //          GIVEN
        //************************

        //************************
        //          WHEN
        //************************
        CompletableFuture<String> twitterCF = twitterProxy.loadTweet();
        final String twitterResponse = twitterCF.get();
        List<Twitter> twitters = Arrays.asList(mapper.readValue(twitterResponse, Twitter[].class));

        //************************
        //          THEN
        //************************
        assertThat(twitterResponse).isNotEmpty();
        assertThat(twitters.size()).isNotZero();
        assertThat(twitters.size()).isEqualTo(2);
        assertThat(twitters.get(0).getTweet()).asString().isNotEmpty();
        assertThat(twitters.get(0).getUsername()).asString().isNotEmpty();
        assertThat(twitters.get(1).getTweet()).asString().isNotEmpty();
        assertThat(twitters.get(1).getUsername()).asString().isNotEmpty();
    }

    @Test
    public void get_facebook_service_TEST() throws Exception {
        //************************
        //          GIVEN
        //************************

        //************************
        //          WHEN
        //************************
        CompletableFuture<String> facebookCF = facebookProxy.loadStatus();
        final String facebookResponse = facebookCF.get();
        List<Facebook> facebooks = null;

        //************************
        //          THEN
        //************************
        assertThat(facebookResponse).isNotEmpty();

        try {
            facebooks = Arrays.asList(mapper.readValue(facebookResponse, Facebook[].class));

            assertThat(facebooks.size()).isEqualTo(2);
            assertThat(facebooks.get(0).getName()).asString().isNotEmpty();
            assertThat(facebooks.get(0).getStatus()).asString().isNotEmpty();
            assertThat(facebooks.get(1).getName()).asString().isNotEmpty();
            assertThat(facebooks.get(1).getStatus()).asString().isNotEmpty();
        } catch (Exception exception) {
            String expectedMessage = "I am trapped in a social media factory send help";
            assertThat(exception.getMessage()).contains(expectedMessage);
        }
    }

}
