package com.example.user.repository;

import com.example.user.entity.User;
import java.util.List;

public interface UserRepository {
    void save(User user);
    void update(User user);
    void delete(int id);
    User get(int id);
    List<User> list();
}