package com.example.ruiji.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.ruiji.utils.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("自动插入操作执行");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", ThreadLocalUtils.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", ThreadLocalUtils.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("自动更新操作执行");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", ThreadLocalUtils.getCurrentId());
    }
}
