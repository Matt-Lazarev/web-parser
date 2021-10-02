package com.project.webparser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table( name = "websites",
        uniqueConstraints =
            @UniqueConstraint(name = "unique_url_idx", columnNames = "url"))
public class Website {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "URL cannot be blank!")
    @NotEmpty(message = "URL cannot be empty!")
    //@Pattern(regexp = "http(s)?://(www\\.)?.+\\..+", message = "Wrong URL pattern!")
    @Column(name = "url", nullable = false)
    private String url;
}
