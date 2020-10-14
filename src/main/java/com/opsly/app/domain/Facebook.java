package com.opsly.app.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class Facebook implements Serializable {
    private static final long serialVersionUID = 889100472995566444L;


    private String name;
    private String status;

    public Facebook() {
    }

}
