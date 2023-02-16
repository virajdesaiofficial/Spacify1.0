package org.uci.spacify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbInitialization implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("##############################################");
        System.out.print("\n");
        System.out.println("Welcome to Spacify !");
        System.out.print("\n");
        System.out.println("##############################################");

        String query = "INSERT INTO corespacify.MacAddress (userId, macAddress) VALUES ('desaivh', '12:2H:35:FF')";

        int rows = jdbcTemplate.update(query);
        if (rows > 0) {
            System.out.println("successfully inserted a row");
        }
    }
}
