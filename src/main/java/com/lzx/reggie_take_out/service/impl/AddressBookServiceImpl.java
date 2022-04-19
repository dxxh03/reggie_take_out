package com.lzx.reggie_take_out.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzx.reggie_take_out.entity.AddressBook;
import com.lzx.reggie_take_out.mapper.AddressBookMapper;
import com.lzx.reggie_take_out.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
