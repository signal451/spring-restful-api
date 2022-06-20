package com.example.restful.demo.service;

import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.*;

@Service
@EnableMongoRepositories
public class UserService {

    @Autowired
    userRepository repository;
    final String VALID = "VALID";

    public List<User> getUsers() {
        List <User> list = repository.findAll();
        return list;
    }

    public User getSpecific(String id) {
        User singleUser =  repository.findByID(id);
        return singleUser;
    }


    public ResponseEntity insert(@Valid @RequestBody User request) {

        User userValidate = new User();
        userValidate.setUsername(request.getUsername());
        userValidate.setEmail(request.getEmail());
        userValidate.setPassword(request.getPassword());

        String validationResult = inputValidation(request);

        if(VALID.equals(validationResult)) {
            repository.insert(request);
            return new ResponseEntity <String>("Хэрэглэгчийг амжилттай бүртгэсэн", HttpStatus.OK);
        }

        return new ResponseEntity <String>(validationResult, HttpStatus.BAD_REQUEST);


    }

    private String inputValidation(User validate) {
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(validate);
        if(violations.size() > 0) {
            for(ConstraintViolation<User> violation: violations) {
                return violation.getMessage();
            }
        }
        return VALID;
    }

    // delete ..
    public ResponseEntity<String> deleteUser(String id) {
          User userDetail = repository.findByID(id);
          if(Objects.isNull(userDetail)) {
              return new ResponseEntity<>("Сервер дээр хэрэглэгч олдсонгүй", HttpStatus.NOT_FOUND);
          }
          repository.deleteById(id);
          String format = String.format("%s id-тай хэрэглэгчийг амжилттай устгасан", id);
          return new ResponseEntity<>(format, HttpStatus.OK);
    }

    // update ..
    public ResponseEntity<User> updateSingleUser(User user) {

        User userDetail = repository.findByID(user.getId());

        // loop through object and set that data ....
        if(!Objects.isNull(userDetail)) {
            //garbage code ....
            //regex bicheed keyword ni taarch baival tuhain keyword deerh object deer utga olgono
            String username = user.getUsername() == null || user.getUsername().length() == 0 ?  userDetail.getUsername() : user.getUsername();
            String email    = user.getEmail() == null || user.getEmail().length() == 0 ? userDetail.getEmail() : user.getEmail();
            String password = user.getPassword() == null || user.getPassword().length() == 0 ? userDetail.getPassword() : user.getPassword();


            userDetail.setUsername(username);
            userDetail.setEmail(email);
            userDetail.setPassword(password);

            repository.save(userDetail);

            User send = new User();
            send.setUsername(username);
            send.setEmail(email);
            send.setPassword(password);

            return new ResponseEntity<>(send, HttpStatus.OK);
        }

        return new ResponseEntity<>(userDetail, HttpStatus.NOT_FOUND);
    }
}
