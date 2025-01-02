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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    ItemDao itemDao;

    @PostMapping
    public String getMenuSelectPage(@ModelAttribute(name = "selectedRestaurantId") Integer selectedRestaurantId, Model model) {
        Restaurant restaurant = restaurantDao.getRestaurantById(selectedRestaurantId);
        List<Item> items = itemDao.getItemsByRestaurantId(selectedRestaurantId);
        model.addAttribute("menuItems", items);
        model.addAttribute("restaurant", restaurant);
        return "menu-select";
    }

    @PostMapping("/add")
    public String addToCart(
            @ModelAttribute("restaurantId") int restaurantId,
            @RequestParam("selectedItems") List<Integer> selectedItemIds,
            Model model) {

        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);

        List<Item> selectedItems = itemDao.getItemsByItemsIds(selectedItemIds);

        double totalPrice = selectedItems.stream()
                .mapToDouble(Item::getPrice)
                .sum();
        double roundedOffTotal = Math.round(totalPrice * 100.0) / 100.0;
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("cartItems", selectedItems);
        model.addAttribute("totalPrice", roundedOffTotal);

        return "cart-summary";
    }
}
