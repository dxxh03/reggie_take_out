package com.lzx.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzx.reggie_take_out.dto.DishDto;
import com.lzx.reggie_take_out.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {

    //新增菜品，同时保存对应的口味数据
    public void saveWithFlavor(DishDto dishDto);

    //根据菜品id查询菜品，且查出对用的口味
    public DishDto getByIdWithFlavors(Long id);

    //更新菜品，同时更新对应的菜品信息
    public void updateWithFlavor(DishDto dishDto);

    //删除菜品并删除对应的口味
    boolean deleteDishWithFlavorByDishId(List<Long> ids);
}
