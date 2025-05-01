package az.vtb.iticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IticketApplication {

    public static void main(String[] args) {
        SpringApplication.run(IticketApplication.class, args);
    }

}
