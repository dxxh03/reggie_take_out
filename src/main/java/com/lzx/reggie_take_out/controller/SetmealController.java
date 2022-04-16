package com.lzx.reggie_take_out.controller;

import com.lzx.reggie_take_out.common.R;
import com.lzx.reggie_take_out.dto.SetmealDto;
import com.lzx.reggie_take_out.entity.SetmealDish;
import com.lzx.reggie_take_out.service.SetmealDishService;
import com.lzx.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 套餐管理
 *
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {

        setmealService.saveWithDish(setmealDto);

        return R.success("新增菜品成功");
    }
}
