package com.lzx.reggie_take_out.controller;

import com.lzx.reggie_take_out.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetmealService setmealService;
}
