package com.example.javaee8.controller;

import com.example.javaee8.model.User;
import com.example.javaee8.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/sign-in")
    public String postSignIn(@ModelAttribute User user) {
        try {
            userService.login(user.getUsername(), user.getPassword());
            return "redirect:/";
        }
        catch (Exception e){
            return "login";
        }
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/sign-up")
    public String postSignUp(@ModelAttribute User user) {
        userService.signup(user);
        try {
            return "redirect:/sign-in";
        } catch (Exception e) {
            return "signup";
        }
    }

}
