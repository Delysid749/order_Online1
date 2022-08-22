package com.take_out.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author YAO
 * @create 2022-08-20 19:33
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
//      若分类关联菜品或套餐则抛业务异常

        LambdaQueryWrapper<Dish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Dish::getCategoryId, id);
        if (dishService.count(lambdaQueryWrapper1) > 0) {
            throw new CustomException("该分类下存在菜品，无法删除");
        }
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(Setmeal::getCategoryId, id);
        if (setmealService.count(lambdaQueryWrapper2) > 0) {
            throw new CustomException("该分类下存在套餐，无法删除");
        }
        super.removeById(id);
    }
}
