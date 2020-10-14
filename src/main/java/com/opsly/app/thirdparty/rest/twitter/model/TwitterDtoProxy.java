package com.opsly.app.thirdparty.rest.twitter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class TwitterDtoProxy implements Serializable {
    private static final long serialVersionUID = 712411472995531514L;

    private String username;
    private String tweet;
}
