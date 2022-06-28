package com.example.restful.demo.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {

    @Autowired
    UserService service;


    @DisplayName("Get all users data")
    @Test
    void getUsers() {
       HttpStatus status = service.getUsers().getStatusCode();
       String[] arr = getResponseStatusCode(status);
       if(arr[0].equals("200")) {
           assertEquals("OK", arr[0]);
       }
       else {
           assertEquals("NOT_FOUND", arr[1]);
       }
    }


    public String[] getResponseStatusCode(HttpStatus status) {
        String strStatus =  status.toString();
        String[] arr = strStatus.split(" ", 2);
        return arr;
    }
}