package com.lzx.reggie_take_out.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回对象
 *
 * @Description
 * @Author dxxh
 * @Data 2022
 */
@Data
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, String value) {
        this.map.put(key, value);
        return this;
    }
}
