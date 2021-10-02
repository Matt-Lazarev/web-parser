package com.project.webparser.repository;

import com.project.webparser.entity.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class WordRepositoryTest {

    @Autowired
    private WordRepository wordRepository;

    @BeforeEach
    private void init(){
        wordRepository.saveAll(List.of(
                new Word(1L, "one"),
                new Word(2L, "two"),
                new Word(3L, "three")
        ));
    }

    @Test
    public void canSaveWord(){
        Word word = new Word(null, "four");

        Word savedWord = wordRepository.save(word);

        assertThat(savedWord.getId()).isNotNull();
        assertThat(savedWord.getName()).isEqualTo("four");
    }

    @Test
    public void canGetWordByName(){
        Word word = wordRepository.getWordByName("two");

        assertThat(word.getId()).isNotNull();
        assertThat(word.getName()).isEqualTo("two");
    }

    @Test
    public void canGetWordByNameFailure(){
        Word word = wordRepository.getWordByName("five");

        assertThat(word).isNull();
    }
}