package org.uci.spacifyEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EntityScan(basePackages = {"org.uci.spacifyLib.entity"})
@EnableJpaRepositories(basePackages = {"org.uci.spacifyLib.repository"})
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);
    }
}
