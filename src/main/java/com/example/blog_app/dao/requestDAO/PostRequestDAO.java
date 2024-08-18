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
public class PostRequestDAO {

    @NotNull
    private Long createdBy;

    @NotNull
    private String description;

    private MultipartFile[] files;
}
