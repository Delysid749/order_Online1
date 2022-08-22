package com.take_out.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author YAO
 * @create 2022-08-20 21:52
 */
public interface DishService extends IService<Dish> {
//    新增菜品，同时插入口味
    public void saveWithFlavor(DishDto dishDto);


    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
