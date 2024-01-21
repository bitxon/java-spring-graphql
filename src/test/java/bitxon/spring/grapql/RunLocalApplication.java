package bitxon.spring.grapql;

import bitxon.spring.grapql.ext.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class RunLocalApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
            .with(TestcontainersConfig.class)
            .run(args);
    }
}
