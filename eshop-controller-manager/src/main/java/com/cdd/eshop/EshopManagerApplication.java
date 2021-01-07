package com.cdd.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableCaching
@EnableSwagger2
@SuppressWarnings("ALL")
@EnableJpaRepositories(basePackages={"com.cdd.eshop.*"})
@SpringBootApplication(scanBasePackages = "com.cdd.eshop")
public class EshopManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopManagerApplication.class, args);
	}

}
