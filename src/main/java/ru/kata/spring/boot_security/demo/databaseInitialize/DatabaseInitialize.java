package ru.kata.spring.boot_security.demo.databaseInitialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Service
public class DatabaseInitialize {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DatabaseInitialize(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
///Тестовые пользователи
    @PostConstruct
    public void addTestUsers() {
        if(roleService.getAllRoles().isEmpty()) {
            Role roleAdmin = new Role("ROLE_ADMIN");
            Role roleUser = new Role("ROLE_USER");
            roleService.save(roleAdmin);
            roleService.save(roleUser);
            if(userService.getAllUsers().isEmpty()) {
                User admin = new User("admin", "admin", "firstname", "lastname", "admin@mail.ru");
                admin.getRoles().add(roleAdmin);
                userService.save(admin);
                User user = new User("user", "user", "user", "user", "user@mail.ru");
                user.getRoles().add(roleUser);
                userService.save(user);
            }
        }
    }
}
