package com.example.restful.demo.service;

import com.example.restful.demo.model.StatusMessage;
import com.example.restful.demo.model.Messages;
import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Validated
@EnableMongoRepositories
public class UserService {


    @Autowired
    UserRepository repository;

    Messages USER_MESSAGE;

    public ResponseEntity<?> getUsers() {
        List <User> list = repository.findAll();
        if(list.isEmpty()) {
            StatusMessage message = new StatusMessage(Messages.LIST_NOT_FOUND);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<?> getSpecific(String id) {
        Optional<User> userDetails = repository.findById(id);
        if(userDetails.isEmpty()) {
            StatusMessage message = new StatusMessage(Messages.USER_NOT_FOUND);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    public ResponseEntity <?> insert(@Valid @RequestBody User request) {
        // Encrypt user password I guess ;-;
        repository.insert(request);
        StatusMessage message = new StatusMessage(Messages.USER_INSERTED);
        return new ResponseEntity<>(message, HttpStatus.OK);
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
        return Messages.VALID_USER;
    }

    public ResponseEntity<?> deleteUser(String id) {
           Optional<User> userDetails = repository.findById(id);
          if(userDetails.isEmpty()) {
              StatusMessage message = new StatusMessage(Messages.USER_NOT_FOUND);
              return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
          }
          repository.deleteById(id);
          String format = String.format("%s id-тай хэрэглэгчийг амжилттай устгасан", id);
          StatusMessage message = new StatusMessage(format);
          return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<?> updateSingleUser(String UserId, @Valid User user) {
        Optional<User> userData = repository.findById(UserId);
        if(userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());

            return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
        }
        StatusMessage message = new StatusMessage(Messages.USER_NOT_FOUND);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


}
