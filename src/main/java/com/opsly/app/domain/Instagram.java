package com.opsly.app.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Instagram implements Serializable {
    private static final long serialVersionUID = 9192400472995566444L;

    private String photo;

    public Instagram() {
    }
}
