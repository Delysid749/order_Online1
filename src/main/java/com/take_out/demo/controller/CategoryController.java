package com.take_out.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author YAO
 * @create 2022-08-20 19:35
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("新增分类");
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 处理分页
     */
     @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        log.info("分页查询分类");
        Page<Category> categoryPage = new Page<>(page, pageSize);
//        条件构造器
         LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
         queryWrapper.orderByDesc(Category::getSort);
         categoryService.page(categoryPage,queryWrapper);
         return R.success(categoryPage);
    }

    /**
     * 根据id删除分类
     */
    @DeleteMapping
    public R<String> deleteById(Long ids){
        log.info("删除分类");
        categoryService.remove(ids);
        return R.success("删除分类成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类");
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
//        条件构造
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
//        排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }

}

