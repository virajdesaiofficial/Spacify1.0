package org.uci.spacify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Main {
    public static void main(String[] args) {
        System.out.println("##############################################");
        System.out.print("\n");
        System.out.println("Welcome to Spacify !");
        System.out.print("\n");
        System.out.println("##############################################");
        SpringApplication.run(Main.class, args);
    }

}