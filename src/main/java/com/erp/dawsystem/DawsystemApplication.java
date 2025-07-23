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
		//abrir el localhost
		openHomePage();
	}

	private static void openHomePage() {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI("http://localhost:8080"));
			} catch (IOException | RuntimeException | java.net.URISyntaxException e) {
				System.err.println("No se pudo abrir el navegador: " + e.getMessage());
			}
		} else {
			System.out.println("Desktop no es compatible en este sistema.");
		}
	}

}
