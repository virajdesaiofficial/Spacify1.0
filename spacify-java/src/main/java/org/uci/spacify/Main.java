package org.uci.spacify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("##############################################");
        System.out.print("\n");
        System.out.println("Welcome to Spacify !");
        System.out.print("\n");
        System.out.println("##############################################");
    }
}