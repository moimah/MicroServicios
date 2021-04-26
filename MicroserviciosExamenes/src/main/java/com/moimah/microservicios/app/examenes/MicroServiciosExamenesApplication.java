package com.moimah.microservicios.app.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication@EntityScan({"com.moimah.commons.examenes.models.entity"})
public class MicroServiciosExamenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiciosExamenesApplication.class, args);
	}

}
