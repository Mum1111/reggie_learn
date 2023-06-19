package com.mumi.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mumi.reggie.dto.SetmealDto;
import com.mumi.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
}
