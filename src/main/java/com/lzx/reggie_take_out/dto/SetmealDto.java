package com.lzx.reggie_take_out.dto;

import com.lzx.reggie_take_out.entity.Setmeal;
import com.lzx.reggie_take_out.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
