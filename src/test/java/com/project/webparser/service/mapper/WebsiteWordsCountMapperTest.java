package com.project.webparser.service.mapper;

import com.project.webparser.dto.WebsiteWordCountDTO;
import com.project.webparser.entity.Website;
import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.entity.Word;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebsiteWordsCountMapperTest {

    @Autowired
    private WebsiteWordsCountMapper mapper;

    @Test
    public void canMapToDto(){
        String url = "https://www.microsoft.com";

        List<Word> words = List.of(
                new Word(1L, "one"),
                new Word(2L, "two"),
                new Word(3L, "three"));


        List<Website> websites = List.of(
                new Website(1L, "https://www.microsoft.com"));


        List<WebsiteWordsCount> wordsCounts =  List.of(
                new WebsiteWordsCount(1L, 30L, websites.get(0), words.get(0)),
                new WebsiteWordsCount(2L, 40L, websites.get(0), words.get(1)),
                new WebsiteWordsCount(3L, 50L, websites.get(0), words.get(2))
        );

        WebsiteWordCountDTO dto = mapper.mapToDTO(wordsCounts, url);

        assertThat(dto.getUrl()).isEqualTo(url);
        assertThat(dto.getWordCount().size()).isEqualTo(wordsCounts.size());
        assertThat(dto.getWordCount().values()).containsAll(List.of(30L, 40L, 50L));
    }

}