package com.comparekaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.comparekaro")
@SpringBootApplication
public class CompareKaroApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompareKaroApplication.class, args);
        System.out.println("---- Application Started Successfully ---");
    }
}
