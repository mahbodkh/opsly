package com.opsly.app.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.opsly.app.web.dto.SocialResponse;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class SocialControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
    WireMockConfiguration wireMockConfiguration = WireMockConfiguration
            .options()
            .port(8089)
            .usingFilesUnderClasspath("wiremock");
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfiguration);
    @Spy
    private ObjectMapper mapper;
    @Value("${app.client.twitter.base-url}")
    private String twitterBaseUrl;
    @Value("${app.client.facebook.base-url}")
    private String facebookBaseUrl;
    @Value("${app.client.instagram.base-url}")
    private String instagramBaseUrl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        wireMockRule.resetMappings();
        wireMockRule.resetScenarios();
        wireMockRule.resetRequests();
    }

    @Test
    public void getSocialController_excepted_mock_thirdParty_TEST() throws Exception {
        //************************
        //        Given
        //************************
        wireMockRule.stubFor(
                WireMock.get(twitterBaseUrl)
                        .withHeader(HttpHeaders.CONTENT_TYPE, new EqualToPattern("application/json"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBody("[{\"username\":\"@GuyEndoreKaiser\",\"tweet\":\"If you live to be 100, you should make up some fake reason why, just to mess with people... like claim you ate a pinecone every single day.\"},{\"username\":\"@mikeleffingwell\",\"tweet\":\"STOP TELLING ME YOUR NEWBORN'S WEIGHT AND LENGTH I DON'T KNOW WHAT TO DO WITH THAT INFORMATION.\"}]")));

        wireMockRule.stubFor(
                WireMock.get(facebookBaseUrl)
                        .withHeader(HttpHeaders.CONTENT_TYPE, new EqualToPattern("application/json"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                                .withBody("[{\"name\":\"Some Friend\",\"status\":\"Here's some photos of my holiday. Look how much more fun I'm having than you are!\"},{\"name\":\"Drama Pig\",\"status\":\"I am in a hospital. I will not tell you anything about why I am here.\"}]")));


        wireMockRule.stubFor(
                WireMock.get(instagramBaseUrl)
                        .withHeader(HttpHeaders.CONTENT_TYPE, new EqualToPattern(MediaType.APPLICATION_JSON_VALUE))
                        .willReturn(aResponse()
                                .withStatus(404)
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBody("404 page not found")));


        //************************
        //          WHEN
        //************************
        // second client request
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String response = getContentAsStringResponse(mvcResult);
        SocialResponse socialResponse = mapper.readValue(response, SocialResponse.class);
        //************************
        //          THEN
        //************************

        Assertions.assertThat(socialResponse).isNotNull();
    }


    private String getContentAsStringResponse(MvcResult mvcResult) throws UnsupportedEncodingException {
        return mvcResult.getResponse().getContentAsString();
    }


}
