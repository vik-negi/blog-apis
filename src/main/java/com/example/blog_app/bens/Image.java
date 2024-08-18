package com.example.blog_app.bens;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String name;

    private String imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = true)
    @JsonIgnore
    private Post post;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore()
    private User user;


    public Image(String url, String name, String imageId) {
        this.url = url;
        this.name = name;
        this.imageId = imageId;
    }
}
