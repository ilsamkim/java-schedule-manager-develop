package com.schedulemanagerdevelop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
public class ScheduleManagerDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleManagerDevelopApplication.class, args);
    }

}
