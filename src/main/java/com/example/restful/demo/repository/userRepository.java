package com.example.restful.demo.repository;

import com.example.restful.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface userRepository extends MongoRepository<User, String> {

}
