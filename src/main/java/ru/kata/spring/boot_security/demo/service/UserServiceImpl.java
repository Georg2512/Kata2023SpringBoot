package ru.kata.spring.boot_security.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {


    private final UserRepository userRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;


    public User findUsersById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(username + "Пользователь не найден");
        }
        return userDetails;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    @Override
    @Transactional
    public void saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return;
        }
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            return;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        if(user.getPassword().isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getUsername()));
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public Long getUsernameByName(String username) {
        User user = userRepository.findByUsername(username);
        return user.getId();
    }

    public void saveUserTest(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return;
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void getUserForUpdateUsers(User user, String username) {
        User userDb = findUserById(getUsernameByName(username));
        Set<Role> roles = userDb.getRoles();
        user.setRoles(roleService.getRoleByName(roles.stream().map(Role::getName).toArray(String[]::new)));
    }

    public void getUserAndRoles(User user, String[] roles) {
        user.setRoles(roleService.getRoleByName(Objects.requireNonNullElseGet(roles, () -> new String[]{"ROLE_USER"})));
    }

    @Override
    public void getNotNullRole(User user) {
        if (user.getRoles() == null) {
            user.setRoles(Collections.singleton(new Role(2L)));
        }
    }

    ///для тестов
    @PostConstruct
    public void addTestUsers() {
        roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        roleRepository.save(new Role(2L, "ROLE_USER"));
        User newAdmin = new User("admin", "admin", roleService.getRoleByName(new String[]{"ROLE_ADMIN"}), 0, "admin");
        saveUserTest(newAdmin);
        User newUser = new User("user", "user", roleService.getRoleByName(new String[]{"ROLE_USER"}), 0, "user");
        saveUserTest(newUser);
    }
}
