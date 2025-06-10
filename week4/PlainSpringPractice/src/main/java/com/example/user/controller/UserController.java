package com.example.user.controller;

import com.example.user.entity.User;
import com.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Resource
    private UserService userService;

    // Get all users: GET /api/users
    @GetMapping
    public List<User> listUsers() {
        return userService.list();
    }

    // Get a single user by ID: GET /api/users/{id}
    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return userService.get(id);
    }

    // Create a new user: POST /api/users
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.save(user);
    }

    // Update an existing user: PUT /api/users/{id}
    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        userService.update(user);
    }

    // Delete a user: DELETE /api/users/{id}
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.delete(id);
    }
}