package com.lzx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzx.reggie_take_out.entity.Employee;
import com.lzx.reggie_take_out.mapper.EmployeeMapper;
import com.lzx.reggie_take_out.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
