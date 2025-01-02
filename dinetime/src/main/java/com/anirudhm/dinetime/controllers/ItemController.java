package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.ItemDao;
import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.models.Item;
import com.anirudhm.dinetime.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemDao itemDao;

    @Autowired
    RestaurantDao restaurantDao;

    @PostMapping
    public String getItemAddPage(Model model, @ModelAttribute("restaurantId") Integer restaurantId) {
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);
        model.addAttribute("item", new Item());
        model.addAttribute("restaurant", restaurant);
        return "item-add-admin";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item") Item item, @ModelAttribute("restaurantId") Integer restaurantId, Model model) {
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);
        item.setRestaurant(restaurant);
        itemDao.saveItem(item);
        List<Item> items = itemDao.getItemsByRestaurantId(restaurantId);
        model.addAttribute("restaurant", item.getRestaurant());
        model.addAttribute("itemList", items);
        return "item-manage-admin";
    }
}
