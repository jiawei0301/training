package com.example.user.service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public void save(User user) { userRepository.save(user); }

    @Override
    public void update(User user) { userRepository.update(user); }

    @Override
    public void delete(int id) { userRepository.delete(id); }

    @Override
    public User get(int id) { return userRepository.get(id); }

    @Override
    public List<User> list() { return userRepository.list(); }
}