package com.project.webparser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table( name = "website_words_count",
        uniqueConstraints = @UniqueConstraint(
                name = "website_word_unique_idx",
                columnNames = {"website_id", "word_id"}))
public class WebsiteWordsCount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count", nullable = false)
    private Long count;

    @ManyToOne()
    @JoinColumn(name = "website_id", referencedColumnName = "id")
    private Website website;

    @ManyToOne()
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Word word;


    public WebsiteWordsCount(Website website, Word word, Long count){
        this.website = website;
        this.word = word;
        this.count = count;
    }

}
