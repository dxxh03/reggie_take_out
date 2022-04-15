package com.lzx.reggie_take_out.common;

/**
 * 自定义异常
 * @Description
 * @Author dxxh
 * @Data 2022
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
