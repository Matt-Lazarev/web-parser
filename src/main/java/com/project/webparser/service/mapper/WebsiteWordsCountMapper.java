package com.project.webparser.service.mapper;

import com.project.webparser.dto.WebsiteWordCountDTO;
import com.project.webparser.entity.WebsiteWordsCount;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebsiteWordsCountMapper {
    public WebsiteWordCountDTO mapToDTO(List<WebsiteWordsCount> counts, String url) {

        Map<String, Long> wordCount = counts
                .stream()
                .collect(Collectors.toMap(
                        x -> x.getWord().getName(),
                        WebsiteWordsCount::getCount
                ));

        WebsiteWordCountDTO dto = new WebsiteWordCountDTO();
        dto.setUrl(url);
        dto.setWordCount(wordCount);

        return dto;
    }
}
