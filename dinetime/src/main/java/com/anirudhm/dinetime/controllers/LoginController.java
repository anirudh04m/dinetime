package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.dao.UserDao;
import com.anirudhm.dinetime.models.Restaurant;
import com.anirudhm.dinetime.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String loginUser(@ModelAttribute User user, Model model) {
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (Objects.nonNull(existingUser) && existingUser.getPassword().equals(user.getPassword())) {
            List<String> restaurantList = restaurantDao.getAllRestaurants().stream().map(Restaurant::getName).toList();
            model.addAttribute("restaurantList", restaurantList);
            return "restaurant-select";
        } else {
            return "index";
        }

    }


}
