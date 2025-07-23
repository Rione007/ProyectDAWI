package com.erp.dawsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@SpringBootApplication
public class DawsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DawsystemApplication.class, args);

		System.out.println("Aplicación iniciada en http://localhost:8080/");

	}
}
