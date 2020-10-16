package com.opsly.app.integration.rest.facebook.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FacebookDtoProxy implements Serializable {
    private static final long serialVersionUID = -1887269589256626043L;

    private String status;
}
