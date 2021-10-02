package com.project.webparser.service.layer;

import com.project.webparser.entity.Website;
import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.entity.Word;
import com.project.webparser.repository.WebsiteWordsCountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class WebsiteWordsCountServiceTest {

    @MockBean
    private WebsiteWordsCountRepository repository;

    @Captor
    private ArgumentCaptor<WebsiteWordsCount> captor;

    @Autowired
    private WebsiteWordsCountService service;


    @Test
    public void canSaveWordCountWithoutSaveInvocation(){
        Website website = new Website(1L, "https://www.microsoft.com");
        Word word = new Word(1L, "one");

        WebsiteWordsCount wwc = new WebsiteWordsCount(1L, 30L, website, word);
        WebsiteWordsCount retrievedWwc = new WebsiteWordsCount(5L, 40L, website, word);

        given(repository.findWebsiteWordsCountByWebsiteIdAndWordId(website.getId(), word.getId()))
                .willReturn(retrievedWwc);

        WebsiteWordsCount savedWwc = service.saveCount(wwc);
        verify(repository, never()).save(any());
        assertThat(savedWwc).isNotEqualTo(wwc);
    }

    @Test
    public void canSaveWordCountWithSaveInvocation(){
        Website website = new Website(1L, "https://www.microsoft.com");
        Word word = new Word(1L, "one");

        WebsiteWordsCount wwc = new WebsiteWordsCount(1L, 30L, website, word);

        given(repository.findWebsiteWordsCountByWebsiteIdAndWordId(website.getId(), word.getId()))
                .willReturn(null);

        service.saveCount(wwc);

        verify(repository).save(captor.capture());
        WebsiteWordsCount savedWwc = captor.getValue();

        assertThat(savedWwc).isEqualTo(wwc);
    }

    @Test
    public void canGetAllCountsByWebsiteUrl(){
        List<Word> words = List.of(
                new Word(1L, "one"),
                new Word(2L, "two"),
                new Word(3L, "three"),
                new Word(4L, "four"));

        List<Website> websites = List.of(
                new Website(1L, "https://www.microsoft.com"));

         List<WebsiteWordsCount> wordsCounts =  List.of(
                new WebsiteWordsCount(1L, 30L, websites.get(0), words.get(0)),
                new WebsiteWordsCount(2L, 40L, websites.get(0), words.get(1)),
                new WebsiteWordsCount(3L, 50L, websites.get(0), words.get(2)),
                new WebsiteWordsCount(4L, 60L, websites.get(0), words.get(3)));

         given(repository.findWebsiteWordsCountByWebsiteUrl(websites.get(0).getUrl()))
                 .willReturn(wordsCounts);

         List<WebsiteWordsCount> retrievedWordCounts = service.getAllCountsByUrl(websites.get(0).getUrl());

         assertThat(retrievedWordCounts.size()).isEqualTo(wordsCounts.size());
    }

}