package com.mumi.reggie.controller;

import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.Orders;
import com.mumi.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> submit(Orders orders){
        log.info("提交订单");
        ordersService.submit(orders);
        return R.success("提交订单成功");
    }

}
