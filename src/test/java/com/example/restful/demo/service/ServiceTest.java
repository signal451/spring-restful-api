package com.example.restful.demo.service;


import com.example.restful.demo.model.StatusMessage;
import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;

@SpringBootTest
public class ServiceTest {

    @Autowired
    UserService service;

    @Autowired
    UserRepository repository;

    static HashMap<String, String> RandomUser = new HashMap<>();

    @BeforeAll
    public static void preRun() {
        RandomUserGenerate();
    }


    private static void RandomUserGenerate() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            list.add(generateUserDetails(getRandomNumber(6, 10)));
        }
        RandomUser.put("username", list.get(0));
        RandomUser.put("email", list.get(0) + "@gmail.com");
        RandomUser.put("password", list.get(1));

        System.out.println(RandomUser);

    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private static String generateUserDetails(int StringLength) {
        String [] user = {};
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = StringLength;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }



    @Test
    @DisplayName("Testing service.insert")
    public void insertTestUserToDB() {
        User user = new User();
        user.setUsername(RandomUser.get("username"));
        user.setPassword(RandomUser.get("password"));
        user.setEmail(RandomUser.get("email"));
        ResponseEntity<List<User>> data = (ResponseEntity<List<User>>) service.insert(user);
        StatusMessage message = (StatusMessage) data.getBody();
        HttpStatus status = data.getStatusCode();
        String[] arr = getResponseStatusCode(status);
        if(arr[0].equals("200")) {
            assertEquals("OK", arr[1]);
            assertEquals("Хэрэглэгч амжилттай бүртгэгдсэн", message.getMessage());
        }
        else {
            assertEquals("NOT_FOUND", arr[1]);
        }
    }


    @Test
    @DisplayName("Verify Early inserted user")
    public void checkUserIsExist() {
        User user = repository.findItemQuery(RandomUser.get("username"), RandomUser.get("password"));
        assertEquals(RandomUser.get("username"), user.getUsername());
        assertEquals(RandomUser.get("password"), user.getPassword());
    }

    @DisplayName("Get all users data")
    @Test
    void getUsers() {
       HttpStatus status = service.getUsers().getStatusCode();
       String[] arr = getResponseStatusCode(status);
       if(arr[0].equals("200")) {
           assertEquals("OK", arr[1]);
       }
       else {
           assertEquals("NOT_FOUND", arr[1]);
       }
    }

    // DELETE TEST
    @Test
    @DisplayName("Test service.delete")
    public void deleteUser() {

    }


    // UPDATE TEST


    public String[] getResponseStatusCode(HttpStatus status) {
        String strStatus =  status.toString();
        String[] arr = strStatus.split(" ", 2);
        return arr;
    }
}