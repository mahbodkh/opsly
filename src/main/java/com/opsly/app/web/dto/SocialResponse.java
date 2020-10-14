package com.opsly.app.web.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialResponse {

    @JsonProperty("twitter")
    List<TwitterDto> twitterDtos = new ArrayList<>();
    @JsonProperty("facebook")
    List<FacebookDto> facebookDtos = new ArrayList<>();
    @JsonProperty("instagram")
    List<InstagramDto> instagramDtos = new ArrayList<>();
}
