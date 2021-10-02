package com.project.webparser.service.layer;

import com.project.webparser.entity.Word;
import com.project.webparser.repository.WordRepository;
import org.junit.jupiter.api.Test;
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
class WordServiceTest {

    @MockBean
    private WordRepository wordRepository;

    @Autowired
    private WordService wordService;

    @Test
    public void canSaveAllWordsWithoutSaveInvocation(){
        List<Word> words = List.of(
                new Word(1L, "one"),
                new Word(2L, "two"),
                new Word(3L, "three"),
                new Word(4L, "four"),
                new Word(5L, "five"));

        given(wordRepository.getWordByName(any()))
                .willReturn(words.get(0))
                .willReturn(words.get(1))
                .willReturn(words.get(2))
                .willReturn(words.get(3))
                .willReturn(words.get(4));

        List<Word> savedWords = wordService.saveAllWords(words);

        verify(wordRepository, never()).save(any());
        assertThat(savedWords.size()).isEqualTo(words.size());
    }

    @Test
    public void canSaveAllWordsWith3SaveInvocation(){
        List<Word> words = List.of(
                new Word(1L, "one"),
                new Word(2L, "two"),
                new Word(3L, "three"));

        given(wordRepository.getWordByName(any()))
                .willReturn(null)
                .willReturn(null)
                .willReturn(null);

        List<Word> savedWords = wordService.saveAllWords(words);

        verify(wordRepository, times(3)).save(any());
        assertThat(savedWords.size()).isEqualTo(words.size());
    }
}