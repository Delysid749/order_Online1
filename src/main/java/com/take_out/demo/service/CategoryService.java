package com.take_out.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @author YAO
 * @create 2022-08-20 19:32
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
