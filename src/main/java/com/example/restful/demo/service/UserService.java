package com.example.restful.demo.service;

import com.example.restful.demo.model.ErrorMessage;
import com.example.restful.demo.model.User;
import com.example.restful.demo.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

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

    public ResponseEntity<?> getSpecific(String id) {
        User singleUser =  repository.findByID(id);
        if(Objects.isNull(singleUser)) {
            ErrorMessage message = new ErrorMessage();
            return new ResponseEntity<>("Сервер дээр хэрэглэгч олдсонгүй", HttpStatus.NOT_FOUND);
        }
        //1) Make enum to store common exception messages
        return new ResponseEntity<>( singleUser, HttpStatus.OK);
    }



    public ResponseEntity <String> insert(@Valid @RequestBody User request) {

        User userValidate = new User();
        userValidate.setUsername(request.getUsername());
        userValidate.setEmail(request.getEmail());
        userValidate.setPassword(request.getPassword());

        String validationResult = inputValidation(request);

        if(VALID.equals(validationResult)) {
            repository.insert(request);
            return new ResponseEntity<>("Хэрэглэгчийг амжилттай бүртгэсэн", HttpStatus.OK);
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
    public ResponseEntity<?> updateSingleUser( @PathVariable("id") String id,  @Valid User user) {
        System.out.println("user id" + id);
        Optional<User> userData = repository.findById(id);
        if(userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setEmail(user.getEmail());
            _user.setPassword(user.getPassword());

            return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
        }
        return new ResponseEntity<>("Сервер дээр хүсэлтийн хэрэглэгч олдсонгүй", HttpStatus.NOT_FOUND);
    }


}
