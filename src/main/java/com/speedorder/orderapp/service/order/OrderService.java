package com.speedorder.orderapp.service.order;

import com.speedorder.orderapp.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
}
