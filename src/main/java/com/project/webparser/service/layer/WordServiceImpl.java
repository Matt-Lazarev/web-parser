package com.project.webparser.service.layer;

import com.project.webparser.entity.Word;
import com.project.webparser.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public List<Word> saveAllWords(List<Word> words) {

        List<Word> savedWords = new ArrayList<>();

        for(Word w : words){
            Word updatedWord = wordRepository.getWordByName(w.getName());
            if(updatedWord == null){
                wordRepository.save(w);
                savedWords.add(w);
            }
            else {
                savedWords.add(updatedWord);
            }
        }
        return savedWords;
    }
}
