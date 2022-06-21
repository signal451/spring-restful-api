package com.example.restful.demo.model;


import com.example.restful.demo.markers.Insert;
import com.fasterxml.jackson.annotation.JsonFilter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;


@Document("user")

public class User {
    @Id
    private String id;
    @NotBlank(message = "Хэрэглэгчийн нэр хоосон байна")
    @Size(min = 4, max = 20, message = "Хэрэглэгчийн нэр 4 эсвэл 20-ийн хооронд байх")
    private String username;
    @NotBlank(message = "И-мейл талбар хоосон байна")
    @Email(message = "И-мейл буруу форматтай байна", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotBlank(message = "Нууц үг хоосон байна")
    @Size(min = 6 , message = "Нууц үг 6 болон түүнээс дээш тэмдэгтээс бүрдсэн байх")
    private String password;



    public User(String id, String username, String email, String password) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(){}


    public String getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
