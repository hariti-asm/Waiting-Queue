package ma.hariti.asmaa.wrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ma.hariti.asmaa.wrm.repository")
@EntityScan(basePackages = "ma.hariti.asmaa.wrm.entity")
public class WrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(WrmApplication.class, args);
        System.out.println("i am being executed");

    }
}
