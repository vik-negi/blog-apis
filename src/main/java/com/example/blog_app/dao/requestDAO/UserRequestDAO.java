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
public class UserRequestDAO {

    @NotNull
    private Long id;
    @NotNull
    private String fullName;

    @NotNull
    private String username;

    private String bio;

    private MultipartFile image;
}
