package com.mumi.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mumi.reggie.entity.Orders;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
