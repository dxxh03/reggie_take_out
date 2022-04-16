package com.lzx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzx.reggie_take_out.dto.SetmealDto;
import com.lzx.reggie_take_out.entity.Setmeal;
import com.lzx.reggie_take_out.entity.SetmealDish;
import com.lzx.reggie_take_out.mapper.SetmealMapper;
import com.lzx.reggie_take_out.service.SetmealDishService;
import com.lzx.reggie_take_out.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@Service
public class SetmealSericeImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal表
        this.save(setmealDto);

        //保存套餐和菜品的关联关系，操作setmeal_dish表
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

    }
}
