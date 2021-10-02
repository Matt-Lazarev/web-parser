package com.project.webparser.repository;

import com.project.webparser.entity.Website;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WebsiteRepositoryTest {

    @Autowired
    private WebsiteRepository websiteRepository;

    @BeforeEach
    private void init(){
        websiteRepository.saveAll(List.of(
                new Website(1L, "https://www.microsoft.com"),
                new Website(2L, "https://www.google.com"),
                new Website(3L, "https://www.site.com")
        ));
    }

    @Test
    public void canSaveWebsite(){
        Website website = new Website(null, "https://www.new-site.com");

        Website savedWebsite = websiteRepository.save(website);

        assertThat(savedWebsite.getId()).isNotNull();
        assertThat(savedWebsite.getUrl()).isEqualTo("https://www.new-site.com");
    }

    @Test
    public void canGetWebsiteByUrl(){
        Website website = websiteRepository.getWebsiteByUrl("https://www.site.com");

        assertThat(website.getId()).isNotNull();
        assertThat(website.getUrl()).isEqualTo("https://www.site.com");
    }

    @Test
    public void canGetWebsiteByUrlFailure(){
        Website website = websiteRepository.getWebsiteByUrl("https://www.wrong-site.com");

        assertThat(website).isNull();
    }

}