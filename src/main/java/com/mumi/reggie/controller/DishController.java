package com.mumi.reggie.controller;

import com.mumi.reggie.service.DishFlavorService;
import com.mumi.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController{
    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private DishService dishService;
}
