package com.lzx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzx.reggie_take_out.dto.DishDto;
import com.lzx.reggie_take_out.entity.Dish;
import com.lzx.reggie_take_out.entity.DishFlavor;
import com.lzx.reggie_take_out.mapper.DishMapper;
import com.lzx.reggie_take_out.service.DishFlavorService;
import com.lzx.reggie_take_out.service.DishService;
import org.springframework.beans.BeanUtils;
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
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDto
     */
    @Override
    @Transactional //开启事务
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表中
        this.save(dishDto);

        //获得菜品id
        Long id = dishDto.getId();

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表中
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据菜品id查询菜品，且查出对用的口味
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavors(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        //构建条件构造器
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(DishFlavor::getDishId, id);

        List<DishFlavor> list = dishFlavorService.list(lambdaQueryWrapper);

        dishDto.setFlavors(list);

        return dishDto;
    }

    /**
     * 更新菜品，同时更新对应的菜品信息
     *
     * @param dishDto
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        Long id = dishDto.getId();

        //清理当前菜品对应口味数据，dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, id);

        dishFlavorService.remove(lambdaQueryWrapper);


        //添加当前提交过来的口味数据，dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public boolean deleteDishWithFlavorByDishId(List<Long> ids) {
        for (Long id : ids) {
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);
            boolean b1 = dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
            if (!b1) {
                return false;
            }
        }
        boolean b2 = this.removeByIds(ids);
        if (!b2) {
            return false;
        }
        return true;
    }
}
