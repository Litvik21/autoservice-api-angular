package com.example.autoservice;

import com.example.autoservice.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoserviceApplication {

    public static void main(String[] args) {
        Config.init();
        SpringApplication.run(AutoserviceApplication.class, args);
    }

}
