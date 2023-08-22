package com.mumi.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mumi.reggie.common.BaseContext;
import com.mumi.reggie.common.CustomException;
import com.mumi.reggie.entity.AddressBook;
import com.mumi.reggie.entity.Orders;
import com.mumi.reggie.entity.ShoppingCart;
import com.mumi.reggie.entity.User;
import com.mumi.reggie.mapper.OrdersMapper;
import com.mumi.reggie.service.AddressBookService;
import com.mumi.reggie.service.OrdersService;
import com.mumi.reggie.service.ShoppingCartService;
import com.mumi.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Override
    @Transactional
    public void submit(Orders orders) {
        // 获取当前用户id信息
        Long userId = BaseContext.getCurrentId();

        // 查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);

        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartLambdaQueryWrapper);

        if (shoppingCartList == null || shoppingCartList.isEmpty()) {
            throw new CustomException("购物车为空，不能下单");
        }

        //获取用户信息
        User user = userService.getById(userId);

        // 查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new CustomException("地址信息为空，不能下单");
        }


        // 向订单表中插入一条数据

        // 向订单明细表中插入多条数据

        // 清空购物车数据
    }
}
