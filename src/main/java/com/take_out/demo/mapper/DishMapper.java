package com.take_out.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YAO
 * @create 2022-08-20 21:50
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
