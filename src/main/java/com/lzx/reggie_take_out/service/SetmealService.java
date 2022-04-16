package com.lzx.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzx.reggie_take_out.dto.SetmealDto;
import com.lzx.reggie_take_out.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，和套餐和菜品的关联关系
     *
     * @param ids
     */
    public void removeWithDish(List<Long> ids);
}
