package com.example.ruiji.utils;

import com.example.ruiji.pojo.Employee;

public class ThreadLocalUtils {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static long getCurrentId() {
        return threadLocal.get();
    }


}
