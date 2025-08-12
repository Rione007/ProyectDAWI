package com.erp.dawsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DawsystemApplication {

	public static void main(String[] args) {

//		//comentar de aqui
//		Dotenv dotenv = Dotenv.load();
//
//		System.out.println("Conectando a DB en: " + dotenv.get("DB_URL"));
//		//hasta aqui para usar mysql local
		SpringApplication.run(DawsystemApplication.class, args);
		System.out.println("Aplicación iniciada en http://localhost:9090/");
	}
}
