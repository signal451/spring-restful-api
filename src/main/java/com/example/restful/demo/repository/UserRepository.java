package com.example.restful.demo.repository;

import com.example.restful.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // For testing purpose

    @Query("{ 'username' : ?0,'password' : ?1}")
    User findItemQuery(String username, String password);
}
//value = "{id: '?0'}"