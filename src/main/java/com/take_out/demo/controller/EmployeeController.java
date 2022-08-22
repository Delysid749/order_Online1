package com.take_out.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YAO
 * @create 2022-07-27 17:26
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//    将页面提交的密码进行md5加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        对传进来的username进行数据库查询
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        if(emp==null){
            return R.error("登陆失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("密码不正确");
        }
//        若员工状态为禁用
        if(emp.getStatus()==0){
            return R.error("该员工已被禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /**
     * 设计退出方法
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
//        清除员工id
        request.getSession().removeAttribute("employee");
        return R.success( "退出成功");
    }


    /**
     * 处理新增员工
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，信息： {}", employee.toString());
//        设置md5加密的123456初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 处理员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page: {},pageSize: {},name: {}",page,pageSize,name);

//         构造分页构造器
        Page pageInfo = new Page(page,pageSize);
//        构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
//        过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
//        排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
//        查询
        employeeService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id处理员工修改
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info("修改员工，信息： {}", employee.toString());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("修改员工成功");
    }

    /**
     * 根据id处理员工删除
     */

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询员工，id： {}", id);

        Employee employee = employeeService.getById(id);
        if(employee!=null){
            return R.success(employee);
        }
        return R.error("查询失败");
    }

}
