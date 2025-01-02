package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.dao.UserDao;
import com.anirudhm.dinetime.enums.Role;
import com.anirudhm.dinetime.models.Restaurant;
import com.anirudhm.dinetime.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    UserDao userDao;

    @Autowired
    RestaurantDao restaurantDao;

    @GetMapping("/")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (Objects.nonNull(existingUser) && existingUser.getPassword().equals(user.getPassword())) {
            List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
            model.addAttribute("restaurantList", restaurantList);
            session.setAttribute("user", existingUser);
            if (existingUser.getRole().equals(Role.ADMIN)) {
                return "restaurant-select-admin";
            } else {
                return "initial-option-select";
            }
        } else {
            model.addAttribute("user", new User());
            return "index";
        }
    }


    @GetMapping("/initial")
    public String displayInitialPage() {
        return "initial-option-select";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("user", new User());
        return "index";
    }

}
