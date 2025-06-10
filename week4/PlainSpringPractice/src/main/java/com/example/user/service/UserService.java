package com.example.user.service;

import com.example.user.entity.User;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(int id);
    User get(int id);
    List<User> list();
}