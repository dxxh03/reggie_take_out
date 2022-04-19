package com.lzx.reggie_take_out.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzx.reggie_take_out.common.R;
import com.lzx.reggie_take_out.dto.DishDto;
import com.lzx.reggie_take_out.entity.Category;
import com.lzx.reggie_take_out.entity.Dish;
import com.lzx.reggie_take_out.entity.DishFlavor;
import com.lzx.reggie_take_out.service.CategoryService;
import com.lzx.reggie_take_out.service.DishFlavorService;
import com.lzx.reggie_take_out.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜品管理
 *
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(@RequestParam("page") int page, @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "name", required = false) String name) {
        //构建分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();


        //构建条件构造器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);

        //添加排序条件
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行查询
        dishService.page(pageInfo, dishLambdaQueryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        //遍历循环赋值，目的是让dishDtoPage这个对象中records有值，且传入categoryName（菜品分类）
        List<DishDto> list = records.stream().map(item -> {

            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());

        //设置dishDtoPage这个对象的records属性
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("dishDto = {}", dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 根据菜品id查询菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable(name = "id", required = true) Long id) {
        DishDto dishDto = dishService.getByIdWithFlavors(id);
        if (dishDto != null) {
            return R.success(dishDto);
        }
        return R.error("该菜品信息异常");
    }

    /**
     * 修改菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }

    /**
     * 删除菜品，并删除对应的口味，判断是否有套餐包含了该菜品
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        log.info("ids = {}", ids);
        boolean b = dishService.deleteDishWithFlavorByDishId(ids);
        if (b) {
            return R.success("删除成功");
        }
        return R.error("删除失败");
    }

    /**
     * 修改菜品状态（启售/停售），包含批量修改
     *
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable("status") int status, @RequestParam("ids") List<Long> ids) {
        log.info("status = {}, ids = {}", status, ids);
        List<Dish> dishes = dishService.listByIds(ids);
        dishes = dishes.stream().map(item -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        //dishService.saveBatch(dishes);
        dishService.updateBatchById(dishes);
        return R.success("修改成功");
    }

    /**
     * 根据条件查询菜品信息
     *
     * @param dish
     * @return
     */
    @RequestMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        lambdaQueryWrapper.eq(Dish::getStatus, 1);

        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(lambdaQueryWrapper);
        List<DishDto> dishDtoList = list.stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String name = category.getName();
                dishDto.setCategoryName(name);
            }

            //当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

    ///**
    // * 根据条件查询菜品信息
    // *
    // * @param dish
    // * @return
    // */
    //@RequestMapping("/list")
    //public R<List<Dish>> list(Dish dish) {
    //    LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    //
    //    lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
    //    lambdaQueryWrapper.eq(Dish::getStatus, 1);
    //
    //    lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //
    //    List<Dish> list = dishService.list(lambdaQueryWrapper);
    //
    //    return R.success(list);
    //}
}
