package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;


    @GetMapping("/admin")
    public String AllUsers(Model model, Principal principal) {
        List<User> user = userService.findAllUsers();
        Long userId = userService.getUsernameByName(principal.getName());
        User userRole = userService.findUserById(userId);
        model.addAttribute("allUser", user);
        model.addAttribute("username", principal.getName());
        model.addAttribute("role",userRole.getAllRolesString());
       // model.addAttribute("user", new User());
        //model.addAttribute("userData", userService.getUserByEmail(principal.getName()));
        return "admin";
    }

    @GetMapping("/admin/userSave")
    public String saveUserAndRole(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", new User());
        return "adminRedactUser";
    }

    @PostMapping("/admin/userSave")
    public String saveUser(User user) {
        userService.getNotNullRole(user);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/userDelete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin/userUpdate/{id}")
    public String updateUserAndRole(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.findUserById(id));
        return "adminUpdateUser";
    }

    @PostMapping("/admin/userUpdate")
    public String updateUsers(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
        userService.getUserAndRoles(user, roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
