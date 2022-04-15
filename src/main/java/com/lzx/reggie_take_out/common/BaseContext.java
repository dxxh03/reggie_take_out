package com.lzx.reggie_take_out.common;

/**
 * 基于ThreadLocal封装的工具类，用户保存和获取当前登录用户的id
 * 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
 * @Description
 * @Author dxxh
 * @Data 2022
 */
public class BaseContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
