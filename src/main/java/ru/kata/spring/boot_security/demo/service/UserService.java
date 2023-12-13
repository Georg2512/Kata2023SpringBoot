package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long id);

    List<User> findAllUsers();

    void deleteUserById(Long id);

    UserDetails loadUserByUsername(String username);

    void saveUser(User user);

    void updateUser(User user);

    Long getUsernameByName(String username);

    void getUserAndRoles(User user, String[] roles);

    void getNotNullRole(User user);

    void addTestUsers();
}
