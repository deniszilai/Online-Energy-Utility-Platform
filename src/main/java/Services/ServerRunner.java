package Services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("Persistence")
@ComponentScan("Services")
@SpringBootApplication
public class ServerRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServerRunner.class, args);
    }
}
