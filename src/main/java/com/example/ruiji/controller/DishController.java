package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    //http://localhost/dish/page?page=1&pageSize=10

    @Autowired
    private DishService dishService;

    // 需要重写
    @GetMapping("/page")
    private R<Page> getDishList(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        dishService.page(dishPage, new LambdaQueryWrapper<Dish>().orderByAsc(Dish::getSort));
        return R.success(dishPage);
    }

}
