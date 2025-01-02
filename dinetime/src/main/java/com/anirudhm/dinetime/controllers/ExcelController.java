package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.OrderDao;
import com.anirudhm.dinetime.models.Order;
import com.anirudhm.dinetime.views.OrdersExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    OrderDao orderDao;

    @PostMapping("/order/download")
    public ModelAndView getOrderExcelFile(@ModelAttribute("restaurantId") Integer restaurantId) {
        List<Order> orders = orderDao.getOrdersByRestaurantId(restaurantId);

        String restaurantName = orders.isEmpty() ? "unknown" : orders.getFirst().getRestaurant().getName();

        return new ModelAndView(new OrdersExcelView(orders, restaurantName));
    }


}
