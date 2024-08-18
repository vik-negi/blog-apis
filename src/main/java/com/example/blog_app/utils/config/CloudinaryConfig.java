package com.example.blog_app.utils.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final Dotenv dotenv = Dotenv.load();
    private final String cloudName = dotenv.get("cloudName");
    private final String apiKey= dotenv.get("apiKey");
    private final String apiSecret = dotenv.get("apiSecret");

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}
