package com.example.restful.demo.repository;

import com.example.restful.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface userRepository extends MongoRepository<User, String> {
    @Query("{username: ?0}")
    User findItemByUsername(String username);

}
