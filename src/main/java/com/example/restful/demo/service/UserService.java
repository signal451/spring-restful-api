package com.example.restful.demo.service;

import com.example.restful.demo.model.StatusMessage;
import com.example.restful.demo.model.Messages;
import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Validated
@EnableMongoRepositories
public class UserService {

    @Autowired
    userRepository repository;
    Messages USER_MESSAGE;

    public List<User> getUsers() {
        List <User> list = repository.findAll();
        return list;
    }

    public ResponseEntity<?> getSpecific(String id) {
        User singleUser =  repository.findByID(id);
        if(Objects.isNull(singleUser)) {
            StatusMessage message = new StatusMessage(Messages.USER_NOT_FOUND);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        //1) Make enum to store common exception messages
        return new ResponseEntity<>( singleUser, HttpStatus.OK);
    }



    public ResponseEntity <?> insert(@Valid @RequestBody User request) {

        User userValidate = new User();
        userValidate.setUsername(request.getUsername());
        userValidate.setEmail(request.getEmail());
        userValidate.setPassword(request.getPassword());
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
          User userDetail = repository.findByID(id);
          if(Objects.isNull(userDetail)) {
              StatusMessage message = new StatusMessage(Messages.USER_NOT_FOUND);
              return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
          }
          repository.deleteById(id);
          String format = String.format("%s id-тай хэрэглэгчийг амжилттай устгасан", id);
          StatusMessage message = new StatusMessage(format);
          return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<?> updateSingleUser( @Valid User user) {
        Optional<User> userData = repository.findById(user.getId());
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
