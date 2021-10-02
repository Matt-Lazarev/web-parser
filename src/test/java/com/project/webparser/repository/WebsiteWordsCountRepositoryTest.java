package com.project.webparser.repository;

import com.project.webparser.entity.Website;
import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.entity.Word;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WebsiteWordsCountRepositoryTest {

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WebsiteWordsCountRepository wordsCountRepository;

    private static long wordsCount;
    private static long websitesCount;
    private static long websiteWordCount;

    @BeforeEach
    private void init(){
        List<Word> words = List.of(
                new Word(++wordsCount, "one"),
                new Word(++wordsCount, "two"),
                new Word(++wordsCount, "three"),
                new Word(++wordsCount, "four"),
                new Word(++wordsCount, "five"));

        wordRepository.saveAll(words);

        List<Website> websites = List.of(
                new Website(++websitesCount, "https://www.microsoft.com"),
                new Website(++websitesCount, "https://www.google.com"),
                new Website(++websitesCount, "https://www.site.com")
        );

        websiteRepository.saveAll(websites);

        wordsCountRepository.saveAll(List.of(
                new WebsiteWordsCount(++websiteWordCount, 30L, websites.get(0), words.get(0)),
                new WebsiteWordsCount(++websiteWordCount, 40L, websites.get(0), words.get(1)),
                new WebsiteWordsCount(++websiteWordCount, 50L, websites.get(0), words.get(2)),
                new WebsiteWordsCount(++websiteWordCount, 60L, websites.get(0), words.get(3)),
                new WebsiteWordsCount(++websiteWordCount, 35L, websites.get(0), words.get(4)),
                new WebsiteWordsCount(++websiteWordCount, 20L, websites.get(1), words.get(0)),
                new WebsiteWordsCount(++websiteWordCount, 25L, websites.get(1), words.get(1)),
                new WebsiteWordsCount(++websiteWordCount, 70L, websites.get(2), words.get(2)),
                new WebsiteWordsCount(++websiteWordCount, 15L, websites.get(2), words.get(3))
        ));
    }

    @Test
    @Order(4)
    public void canSaveWebsiteWordCountFailure(){
        Website website = new Website(1L, "https://www.microsoft.com");
        Word word = new Word(1L, "name");

        WebsiteWordsCount wwc =  new WebsiteWordsCount(10L, 15L, website, word);
        System.out.println("\n\n\n\n");
        assertThrows(Exception.class, () -> wordsCountRepository.save(wwc));
    }

    @Test
    @Order(3)
    public void canSaveWebsiteWordCountSuccess(){
        Website website = new Website(9L, "https://www.site.com");
        Word word = new Word(15L, "five");

        WebsiteWordsCount wwc =  new WebsiteWordsCount(28L, 15L, website, word);
        WebsiteWordsCount savedWwc = wordsCountRepository.save(wwc);

        assertThat(savedWwc).isEqualTo(wwc);
    }


    @Test
    @Order(2)
    public void canGetWebsiteWordCountByWebsiteIdAndWordId(){
        Long wordId = 10L;
        Long websiteId = 4L;

        WebsiteWordsCount wwc =  wordsCountRepository
                .findWebsiteWordsCountByWebsiteIdAndWordId(websiteId, wordId);

        assertThat(wwc.getWebsite().getUrl()).isEqualTo("https://www.microsoft.com");
        assertThat(wwc.getWord().getName()).isEqualTo("five");
    }

    @Test
    @Order(1)
    public void canGetListWebsiteWordCountByUrl(){
        String url = "https://www.microsoft.com";

        List<WebsiteWordsCount> wwcList =  wordsCountRepository
                .findWebsiteWordsCountByWebsiteUrl(url);

        assertThat(wwcList.size()).isEqualTo(5);
    }
}