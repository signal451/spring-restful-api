package com.example.restful.demo.service;


import com.example.restful.demo.model.StatusMessage;
import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


    private User genericUser() {
        User generic = new User();
        generic.setUsername(RandomUser.get("username"));
        generic.setPassword(RandomUser.get("password"));
        generic.setEmail(RandomUser.get("email"));
        return generic;
    }


    @Test
    @DisplayName("Testing service.insert")
    public void insertTestUserToDB() {
        User user = genericUser();
        ResponseEntity<StatusMessage> data = (ResponseEntity<StatusMessage>) service.insert(user);
        String[] arr = getResponseStatusCode(data.getStatusCode());
        if(arr[0].equals("200")) {
            assertEquals("OK", arr[1]);
            assertEquals("Хэрэглэгч амжилттай бүртгэгдсэн", data.getBody().getMessage());
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



    @Test
    @DisplayName("Test service.update")
    void updateUser() {
        User user = repository.findItemQuery(RandomUser.get("username"), RandomUser.get("password"));
        RandomUser.put("id", user.getId());
        User GenericUser = genericUser();
        ResponseEntity<User> updated = (ResponseEntity<User>) service.updateSingleUser(user.getId(), GenericUser);
        String[] arr = getResponseStatusCode(updated.getStatusCode());
        if(arr[0].equals("200")) {
            GenericUser.setId(user.getId());
            assertEquals("OK", arr[1]);
            assertEquals(GenericUser.getId(), updated.getBody().getId());
        }
        else {
            assertEquals("NOT_FOUND", arr[1]);
        }
    }

    @Test
    @DisplayName("Test service.delete")
    void deleteUser() {
        ResponseEntity<StatusMessage> message = (ResponseEntity<StatusMessage>) service.deleteUser(RandomUser.get("id"));
        String[] arr = getResponseStatusCode(message.getStatusCode());
        if(arr[0].equals("200")) {
            assertEquals("OK", arr[1]);
            assertEquals(RandomUser.get("id") + " id-тай хэрэглэгчийг амжилттай устгасан", message.getBody().getMessage());
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