package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.services.UserService;


@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String main(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "user";
    }

    @ModelAttribute("newUser")
    public User getUser(){
        return new User();
    }
    @RequestMapping("/user")
	public String index(Model model){
    	model.addAttribute("user", userService.getAllUsers());
    	return "user";
	}

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public String saveUser(@ModelAttribute("newUser") User user,
                        BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("user", userService.getAllUsers());
            return "user";
        }
        userService.saveUser(user);
    	return "redirect:/user";
    }

    @RequestMapping(value = "/user/{id}")
    public String deletePerson(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/user";
    }
    @RequestMapping(value = "/user/{id}/edit")
    public String editUser(@ModelAttribute("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    /*@RequestMapping(method = RequestMethod.PATCH,value = "/user/{id}/update")
    public String updateUser(@ModelAttribute("id") Long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        userService.saveUser(user);
        return "user";
    }*/
}