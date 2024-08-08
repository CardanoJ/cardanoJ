package com.cardanoj.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the CardanoJ API Spring Boot application.
 * <p>
 * This class contains the main method that bootstraps the Spring Boot application. It sets up the
 * application context and starts the embedded web server.
 * </p>
 */
@SpringBootApplication
public class CardanoJApiApplication {

    /**
     * The main method that serves as the entry point for the application.
     * <p>
     * This method uses SpringApplication.run() to launch the application. It initializes the Spring context,
     * performs classpath scanning, and starts the embedded server.
     * </p>
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(CardanoJApiApplication.class, args);
    }

}
