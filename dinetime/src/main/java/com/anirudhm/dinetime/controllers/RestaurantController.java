package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.ItemDao;
import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.enums.Role;
import com.anirudhm.dinetime.models.Item;
import com.anirudhm.dinetime.models.Restaurant;
import com.anirudhm.dinetime.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    ItemDao itemDao;

    @PostMapping("/admin")
    public String displayAdminManagement(@ModelAttribute("selectedRestaurant") Integer selectedRestaurant, Model model) {
        Restaurant restaurant = restaurantDao.getRestaurantById(selectedRestaurant);
        model.addAttribute("restaurant", restaurant);
        return "restaurant-manage-admin";
    }

    @PostMapping("/admin/select")
    public String displayAdminOptions(@ModelAttribute("selectedAction") String selectedAction,
                                      @ModelAttribute("restaurant") Integer restaurantId,
                                      Model model) {
        if (selectedAction.equals("item")) {
            Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);
            model.addAttribute("restaurant", restaurant);
            List<Item> items = itemDao.getItemsByRestaurantId(restaurantId);
            model.addAttribute("itemList", items);
            return "item-manage-admin";
        } else {
            return "redirect:/order/admin?restaurantId=" + restaurantId + "&page=0";
        }
    }

    @GetMapping("/select")
    public String displayUserRestaurantsPage(Model model) {
        List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
        model.addAttribute("restaurantList", restaurantList);

        return "restaurant-select";
    }

    @GetMapping("/admin/back")
    public String goBackToRestaurantSelectAdmin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals(Role.ADMIN)) {
            List<Restaurant> restaurantList = restaurantDao.getAllRestaurants();
            model.addAttribute("restaurantList", restaurantList);
            return "restaurant-select-admin";
        }
        return "index";
    }
}
