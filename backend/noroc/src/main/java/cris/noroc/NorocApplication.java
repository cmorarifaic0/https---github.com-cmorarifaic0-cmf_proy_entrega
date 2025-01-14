package cris.noroc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NorocApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NorocApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Application startup logic here
        System.out.println("Application started successfully!");
    }
}
