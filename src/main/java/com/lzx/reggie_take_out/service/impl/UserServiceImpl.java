package com.lzx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzx.reggie_take_out.entity.User;
import com.lzx.reggie_take_out.mapper.UserMapper;
import com.lzx.reggie_take_out.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
