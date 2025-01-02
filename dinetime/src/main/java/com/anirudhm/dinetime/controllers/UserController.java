package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.UserDao;
import com.anirudhm.dinetime.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @PostMapping("/verify-username")
    public Boolean checkUsername(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        User user = userDao.getUserByUsername(username);
        return Objects.nonNull(user);
    }

}
