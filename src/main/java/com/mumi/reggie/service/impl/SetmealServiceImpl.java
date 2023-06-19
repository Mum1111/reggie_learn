package com.mumi.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mumi.reggie.dto.SetmealDto;
import com.mumi.reggie.entity.Setmeal;
import com.mumi.reggie.entity.SetmealDish;
import com.mumi.reggie.mapper.SetmealMapper;
import com.mumi.reggie.service.SetmealDishService;
import com.mumi.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        // 将数据存入setmeal表
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        // 将数据存入setmealDish表
        setmealDishService.saveBatch(setmealDishes);

    }
}
