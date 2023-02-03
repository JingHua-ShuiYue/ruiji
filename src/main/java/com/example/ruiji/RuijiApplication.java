package com.example.ruiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class RuijiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuijiApplication.class, args);
    }

}
