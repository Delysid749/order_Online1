package com.take_out.demo.common;

/**
 * @author YAO
 * @create 2022-08-20 18:03
 * 基于ThreadLocal的封装工具类，用于保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
