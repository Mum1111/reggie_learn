package com.mumi.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mumi.reggie.common.R;
import com.mumi.reggie.entity.Employee;
import com.mumi.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2. 根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3.如果没有查询则返回登陆失败的结果
        if (emp == null){
            return R.error("登陆失败");
        }

        //4.密码比对，如果不一致则返回失败的结果
        if(!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        //5. 查看员工状态，
        if(emp.getStatus() ==  0){
            return R.error("账号被封禁");
        }

        //6. 登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return  R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee){
        log.info("新增员工信息 {}", employee.toString());

        // 给注册员工设置初始密码123456 md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));



        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page,int pageSize, String name){
        // 分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        employeeService.updateById(employee);
        return R.success("更新成功");
    }

    @GetMapping("/{empId}")
    public R<Employee> getById(@PathVariable Long empId){
        Employee employee = employeeService.getById(empId);
        if(employee != null) {
            return R.success(employee);
        }
        return R.error("员工不存在");
    }
}
