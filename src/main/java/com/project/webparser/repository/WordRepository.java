package com.project.webparser.repository;

import com.project.webparser.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<Word, Long> {
    Word getWordByName(String name);
}
