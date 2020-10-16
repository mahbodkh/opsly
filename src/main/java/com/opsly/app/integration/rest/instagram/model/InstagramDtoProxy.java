package com.opsly.app.integration.rest.instagram.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class InstagramDtoProxy implements Serializable {
    private static final long serialVersionUID = 170441172995566444L;
    private String photo;
}
