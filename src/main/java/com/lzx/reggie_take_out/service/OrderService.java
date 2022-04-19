package com.lzx.reggie_take_out.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzx.reggie_take_out.entity.Orders;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     *
     * @param orders
     */
    public void submit(Orders orders);
}
