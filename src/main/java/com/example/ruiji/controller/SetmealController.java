package com.example.ruiji.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    SetmealService setmealService;
    @GetMapping("/page")
    public R<Page> getInfo() {
        Page<Setmeal> setmealPage = new Page<>();
        setmealService.page(setmealPage);
        return R.success(setmealPage);
    }


}
