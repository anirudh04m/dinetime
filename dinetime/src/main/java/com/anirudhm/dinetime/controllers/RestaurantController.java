package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantDao restaurantDao;

    @PostMapping
    public void saveRestaurant(@RequestBody Map<String, String> restaurant) {
        Restaurant res = new Restaurant();
        res.setName(restaurant.get("name"));
        res.setPhone(restaurant.get("phone"));
        res.setAddress(restaurant.get("address"));
        restaurantDao.saveRestaurant(res);
    }
}
