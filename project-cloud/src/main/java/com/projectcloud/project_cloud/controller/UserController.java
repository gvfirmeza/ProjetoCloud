package com.projectcloud.project_cloud.controller;

import com.projectcloud.project_cloud.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static List<User> Users = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<User>> getUser() {
        return new ResponseEntity(Users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        Users.add(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
