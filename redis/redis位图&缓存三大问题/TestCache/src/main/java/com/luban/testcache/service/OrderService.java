package com.luban.testcache.service;

import com.luban.testcache.entity.Order;
import com.luban.testcache.entity.R;

import java.util.List;

public interface OrderService {
    Integer insertOrder(Order order);

    R selectOrderById(Integer id);

    List<Order> selectOrderyAll();

    R synchronizedSelectOrderById(Integer id);
}
