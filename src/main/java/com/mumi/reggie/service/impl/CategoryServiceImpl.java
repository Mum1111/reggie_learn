package com.mumi.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mumi.reggie.common.CustomException;
import com.mumi.reggie.entity.Category;
import com.mumi.reggie.entity.Dish;
import com.mumi.reggie.entity.Setmeal;
import com.mumi.reggie.mapper.CategoryMapper;
import com.mumi.reggie.service.CategoryService;
import com.mumi.reggie.service.DishService;
import com.mumi.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, ids);
        int count1 = dishService.count(dishLambdaQueryWrapper);

        if(count1 > 0) {
            // 抛出一个错误
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, ids);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if (count2 > 0) {
            // 抛出一个业务错误
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        super.removeById(ids);
    }
}
