package com.example.blog_app;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogAppApplication {
	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String secretKey = dotenv.get("SECRET_KEY");

		SpringApplication.run(BlogAppApplication.class, args);
	}
}
