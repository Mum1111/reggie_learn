package com.mumi.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mumi.reggie.dto.DishDto;
import com.mumi.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getDishWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
