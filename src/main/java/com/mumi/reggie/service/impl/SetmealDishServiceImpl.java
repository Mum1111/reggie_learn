package com.mumi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mumi.reggie.entity.SetmealDish;
import com.mumi.reggie.mapper.SetmealDishMapper;
import com.mumi.reggie.mapper.SetmealMapper;
import com.mumi.reggie.service.SetmealDishService;
import com.mumi.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
