package kz.onetech.cv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CVHosterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CVHosterApplication.class, args);
    }

}
