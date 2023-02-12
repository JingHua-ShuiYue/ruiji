package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.common.R;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // http://localhost/category/page?page=1&pageSize=10
    @GetMapping("/page")
    public R<Page> getCategoryInfo(@RequestParam Integer page, @RequestParam Integer pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        categoryService.page(categoryPage, queryWrapper.orderByAsc(Category::getSort));
        return R.success(categoryPage);
    }

    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        log.info("添加套餐");
        categoryService.save(category);
        return R.success("添加成功");
    }

    @DeleteMapping()
    public R<String> deleteCategory(@RequestParam Long ids) {
        // categoryService.removeById(ids);
        log.warn("删除套餐");
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping()
    public R<String> updateCategory(@RequestBody Category category) {
        log.info("更新信息");
        categoryService.update(category, new LambdaUpdateWrapper<Category>().eq(Category::getId, category.getId()));
        return R.success("更新信息成功");
    }

    // http://localhost/category/list?type=1
    @GetMapping("list")
    public R<String> getList(HttpServletRequest request) {
        return null;
    }

}
