package com.example.restful.demo.controller;

import com.example.restful.demo.model.User;
import com.example.restful.demo.service.UserSequenceGenerate;
import com.example.restful.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RequestController {

    private final UserService service;


    @Autowired
    public RequestController(UserService userService) {
        this.service = userService;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.ALL_VALUE)
    public  ResponseEntity<?> getAllUser() {
        return service.getUsers();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getSingleUser(@PathVariable String id) {
        return service.getSpecific(id);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> userSignIn(@RequestBody User userData) {
        //TODO: --> when incoming request fails we don't have to increase sequence number
        return service.insert(userData);
    }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity <?> removeUserFromDB(@PathVariable String id) { return  service.deleteUser(id); }
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity <?> updateUser(@PathVariable String id, @RequestBody User user) { return service.updateSingleUser(id, user); }
}
