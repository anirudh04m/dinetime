package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.ItemDao;
import com.anirudhm.dinetime.dao.OrderDao;
import com.anirudhm.dinetime.dao.RestaurantDao;
import com.anirudhm.dinetime.models.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderDao orderDao;

    @PostMapping("/place")
    public String placeOrder(@ModelAttribute("restaurantId") int restaurantId,
                             @RequestParam("selectedItems") List<Integer> selectedItemIds,
                             Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Restaurant restaurant = restaurantDao.getRestaurantById(restaurantId);

        List<Item> selectedItems = itemDao.getItemsByItemsIds(selectedItemIds);

        Order order = new Order();

        order.setStatus("Pending");
        order.setUser(user);
        order.setRestaurant(restaurant);
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;

        for (int itemId : selectedItemIds) {
            Item item = selectedItems.stream().filter(it -> it.getItemId() == itemId).findFirst().orElse(null);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);

            assert item != null;
            totalPrice += item.getPrice();
            orderItems.add(orderItem);
        }

        double roundedOffTotal = Math.round(totalPrice * 100.0) / 100.0;
        order.setOrderItems(orderItems);
        order.setTotalPrice(roundedOffTotal);

        orderDao.saveOrder(order);

        model.addAttribute("order", order);
        model.addAttribute("restaurant", restaurant);

        return "order-confirm";
    }

    @GetMapping("/user")
    public String getOrdersByUserId(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        List<Order> orders = orderDao.getOrdersByUserId(user.getUserId());

        model.addAttribute("user", user);
        model.addAttribute("orders", orders);

        return "user-orders";
    }

    @GetMapping("/admin")
    public String displayOrders(@RequestParam(name = "restaurantId") Integer restaurantId,
                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                Model model) {
        int pageSize = 5;
        List<Order> orders = orderDao.findPageOrdersByRestaurantId(restaurantId, page, pageSize);
        long totalOrders = orderDao.countOrdersByRestaurantId(restaurantId);
        int totalPages = (int) Math.ceil((double) totalOrders / pageSize);
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("restaurantId", restaurantId);
        return "order-management-admin";
    }
}
