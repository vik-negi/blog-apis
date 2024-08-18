package com.example.blog_app.dao.requestDAO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDAO {
    @NotNull
    private String fullName;
    @NotNull
    private String username;

    private String email;

    private String bio;

    private MultipartFile image;

    @NotNull
    private String password;
}
