package com.example.user.repository;

import com.example.user.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jakarta.annotation.Resource;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "INSERT INTO \"users\" (name, email) VALUES (?, ?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail());
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE \"users\" SET name=?, email=? WHERE id=?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM \"users\" WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM \"users\" WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public List<User> list() {
        String sql = "SELECT * FROM \"users\" ORDER BY id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}