package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.pojo.Employee;
import com.example.ruiji.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 登录
     *
     * @param employee 获取前端传来的数据
     * @param request
     * @return
     */
    @PostMapping("/login")
    private R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee employeeInfo = employeeService.getOne(employeeLambdaQueryWrapper);
        if (employeeInfo == null) {
            log.info("未查询到用户 {}---------登陆失败", employee.getUsername());
            return R.error("登录失败");
        }
        if (employeeInfo.getPassword().equals(password)) {
            if (employeeInfo.getStatus() == 0) {
                log.info("{} 该账号已禁用---------登陆失败", employee.getUsername());
                return R.error("账号已禁用");
            } else {
                request.getSession().setAttribute("employee", employeeInfo.getId());
                log.info("用户 {} id{}---------登陆成功", employee.getUsername(), employeeInfo.getId());
                return R.success(employeeInfo);
            }
        } else {
            log.info("{} 密码错误---------登陆失败", employee.getUsername());
            return R.error("密码错误");
        }
    }

    @PostMapping("/logout")
    private R<String> logout(HttpServletRequest request) {
        log.info("用户 {} 退出登录", request.getSession().getAttribute("employee"));
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping("")
    private R<String> addEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("创新新用户{}", employee.getUsername());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes())); //设置初始密码
       /* employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));*/
        employeeService.save(employee);
        return R.success("添加成功");
    }

    @PutMapping("")
    private R<String> editEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("更新员工信息");
       /* employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));*/
        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    @GetMapping("/{id}")
    private R getEditEmployeeInfo(@PathVariable int id) {
        log.info("获取id为{}的员工信息", id);
        Employee employeeInfo = employeeService.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getId, id));
        return R.success(employeeInfo);
    }

    @GetMapping("/page")
    private R<Page> getEmployeePage(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam(required = false) String name) {
        if (name == null) {
            log.info("分页查询全部的员工信息");
            Page<Employee> employeePage = new Page<>(page, pageSize);
            QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
            employeeService.page(employeePage, queryWrapper.orderByAsc("create_time"));
            return R.success(employeePage);
        } else {
            log.info("根据名字查询员工信息");
            Page<Employee> employeePage = new Page<>(page, pageSize);
            QueryWrapper<Employee> queryWrapper = new QueryWrapper<>();
            employeeService.page(employeePage, queryWrapper.eq("name", name).orderByAsc("create_time"));
            return R.success(employeePage);
        }
    }

}
