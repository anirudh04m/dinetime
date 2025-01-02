package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.UserDao;
import com.anirudhm.dinetime.enums.Role;
import com.anirudhm.dinetime.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    UserDao userDao;

    @GetMapping
    public String getSignUpPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping
    public String handleForm(@ModelAttribute("user") User user,
                             @ModelAttribute("confirmPassword") String confirmPassword, Model model) {
        user.setRole(Role.CUSTOMER);
        if (Objects.isNull(userDao.getUserByUsername(user.getUsername()))) {
            userDao.saveUser(user);
            return "index";
        } else {
            model.addAttribute("message", "Username already exists! Try Again.");
            return "signup";
        }
    }
}
