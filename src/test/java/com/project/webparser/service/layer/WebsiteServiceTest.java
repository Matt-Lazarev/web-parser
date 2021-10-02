package com.project.webparser.service.layer;

import com.project.webparser.entity.Website;
import com.project.webparser.repository.WebsiteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class WebsiteServiceTest {

    @MockBean
    private WebsiteRepository websiteRepository;

    @Captor
    private ArgumentCaptor<Website> captor;

    @Autowired
    private WebsiteService websiteService;

    @Test
    public void canGetWebsiteById(){
        Website website = new Website(1L, "https://www.microsoft.com");

        given(websiteRepository.findById(website.getId()))
                .willReturn(Optional.of(website));

        Website retrievedWebsite = websiteService.getWebsiteById(website.getId());

        assertThat(retrievedWebsite.getUrl()).isEqualTo(website.getUrl());
        assertThat(retrievedWebsite.getId()).isEqualTo(website.getId());
    }

    @Test
    public void canGetWebsiteByUrl(){
        Website website = new Website(1L, "https://www.microsoft.com");

        given(websiteRepository.getWebsiteByUrl(website.getUrl()))
                .willReturn(website);

        Website retrievedWebsite = websiteService.getWebsiteByUrl(website.getUrl());

        assertThat(retrievedWebsite.getUrl()).isEqualTo(website.getUrl());
        assertThat(retrievedWebsite.getId()).isEqualTo(website.getId());
    }

    @Test
    public void canSaveWebsiteWithSaving(){
        Website website = new Website(1L, "https://www.microsoft.com");

        given(websiteRepository.getWebsiteByUrl(website.getUrl()))
                .willReturn(null);

        websiteService.saveWebsite(website);

        verify(websiteRepository).save(captor.capture());
        Website savedWebsite = captor.getValue();

        assertThat(savedWebsite.getUrl()).isEqualTo(website.getUrl());
        assertThat(savedWebsite.getId()).isGreaterThan(0);
    }

    @Test
    public void canSaveWebsiteWithoutSaving(){
        Website website = new Website(1L, "https://www.microsoft.com");
        Website foundWebsite = new Website(10L, "https://www.microsoft.com");

        given(websiteRepository.getWebsiteByUrl(website.getUrl()))
                .willReturn(foundWebsite);

        Website savedWebsite = websiteService.saveWebsite(website);

        verify(websiteRepository, never()).save(any());

        assertThat(savedWebsite.getUrl()).isEqualTo(website.getUrl());
        assertThat(savedWebsite.getId()).isGreaterThan(0);
        assertThat(savedWebsite).isNotEqualTo(website);
    }
}