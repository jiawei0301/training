package com.example.springpractice.service;

import com.example.springpractice.entity.User;
import com.example.springpractice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    public UserServiceImpl(UserRepository repo) { this.repo = repo; }

    @Transactional
    @Override public List<User> findAll() { return repo.findAll(); }
    @Transactional
    @Override public Optional<User> findById(Long id) { return repo.findById(id); }
    @Transactional
    @Override public User save(User user) { return repo.save(user); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
}
// TODO:
// 0. fix restful endpoints
// 1. review springboot framework we discussed
// 2. enable springboot actuator
// 3. list out annotations we used, difference + when to use which (@Bean, @Configuration, @primary,
//                                                  @Value, @profile, @Primary, @RestController,
//                                        @Controller, @SpringbootApplication, @Service, @Repository, @RequestBody, @Component
//                                        @RequestParam, @PathVariable, @Aspet, pointcut, Join point)
// 4. unit test for your current backend -- ensure coverage is more than 90%
// 5. cache implementaions for springboot ----> annotation
// 6. @transcational
