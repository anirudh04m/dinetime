package com.anirudhm.dinetime.controllers;

import com.anirudhm.dinetime.dao.OrderDao;
import com.anirudhm.dinetime.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Autowired
    private OrderDao orderDao;

    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestBody Map<String, Object> requestData) {
        try {
            int orderId = (int) requestData.get("orderId");
            String status = (String) requestData.get("status");

            Order order = orderDao.getOrderById(orderId);
            if (order != null) {
                order.setStatus(status);
                orderDao.updateOrder(order);
                return "Status updated successfully";
            } else {
                return "Order not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating status";
        }
    }

}
