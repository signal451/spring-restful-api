package com.example.restful.demo.controller;

import com.example.restful.demo.model.User;
import com.example.restful.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class requestController {


    private final UserService service;
    @Autowired
    public requestController(UserService userService) {
        this.service = userService;
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.ALL_VALUE)
    public List <User> getAllUser() {
        return service.getUsers();
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getSingleUser(@PathVariable String id) {
        return service.getSpecific(id);
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity userSignIn(@RequestBody User userSignIn) {
        return service.insert(userSignIn);
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity <String> removeUserFromDB(@PathVariable String id) { return  service.deleteUser(id); }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity <?> updateUser(@PathVariable String id, @RequestBody User user) { return service.updateSingleUser(id, user); }
}
