package com.mumi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.Category;
import com.mumi.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Category> categoryWrapper = new LambdaQueryWrapper<>();
        //添加排序
        categoryWrapper.orderByDesc(Category::getUpdateTime);

        // 执行查询
        categoryService.page(pageInfo,categoryWrapper);

        return R.success(pageInfo);
    }
}
