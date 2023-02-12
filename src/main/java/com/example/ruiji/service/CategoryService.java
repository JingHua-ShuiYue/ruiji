package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.common.CustomException;
import com.example.ruiji.pojo.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id) throws CustomException;
}
