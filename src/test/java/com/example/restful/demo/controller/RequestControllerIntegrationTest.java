package com.example.restful.demo.controller;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class RequestControllerIntegrationTest {

    @Test
    public void getAllUserFromDB(@Autowired MongoTemplate mongoTemplate) {

        DBObject objectSave = BasicDBObjectBuilder.start()
                .add("key", "value")
                .get();
        mongoTemplate.save(objectSave, "TestCollection");

        assertThat(mongoTemplate.findAll(DBObject.class, "TestCollection")).extracting("key")
                .containsOnly("value");

    }
}