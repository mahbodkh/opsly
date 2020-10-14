package com.opsly.app.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Twitter implements Serializable {
    private static final long serialVersionUID = 98244472995566444L;

    private String username;
    private String tweet;

    public Twitter() {
    }
}
