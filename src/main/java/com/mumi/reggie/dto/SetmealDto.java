package com.mumi.reggie.dto;


import com.mumi.reggie.entity.Setmeal;
import com.mumi.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
