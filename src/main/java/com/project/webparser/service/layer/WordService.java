package com.project.webparser.service.layer;

import com.project.webparser.entity.Word;

import java.util.List;

public interface WordService {
    List<Word> saveAllWords(List<Word> words);
}
