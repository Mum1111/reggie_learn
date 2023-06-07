package com.mumi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.Category;
import com.mumi.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public R<String> add(@RequestBody Category category) {
        log.info("添加信息 {}", category.toString());

        categoryService.save(category);

        return R.success("添加成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改信息 {}", category.toString());

        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除的id {}", ids);

        categoryService.removeById(ids);
        return R.success("删除成功");
    }
}
