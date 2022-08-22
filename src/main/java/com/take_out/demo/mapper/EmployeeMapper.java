package com.take_out.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author YAO
 * @create 2022-07-27 17:20
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
