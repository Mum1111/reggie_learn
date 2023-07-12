package com.mumi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mumi.reggie.entity.Orders;
import com.mumi.reggie.mapper.OrdersMapper;
import com.mumi.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
