package com.example.restful.demo.model;

import org.springframework.data.annotation.Id;

public class BaseUserDocument {
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

