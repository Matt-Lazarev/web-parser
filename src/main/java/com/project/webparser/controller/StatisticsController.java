package com.project.webparser.controller;

import com.project.webparser.dto.WebsiteWordCountDTO;
import com.project.webparser.entity.Website;
import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.service.layer.WebsiteService;
import com.project.webparser.service.mapper.WebsiteWordsCountMapper;
import com.project.webparser.service.layer.WebsiteWordsCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StatisticsController {

    private final WebsiteService websiteService;
    private final WebsiteWordsCountService wordsCountService;
    private final WebsiteWordsCountMapper mapper;

    public StatisticsController(WebsiteService websiteService,
                                WebsiteWordsCountService wordsCountService,
                                WebsiteWordsCountMapper mapper) {
        this.websiteService = websiteService;
        this.wordsCountService = wordsCountService;
        this.mapper = mapper;
    }

    @GetMapping("/statistics/{websiteId}")
    public WebsiteWordCountDTO showStatistics(@PathVariable("websiteId") Long id){
        Website website = websiteService.getWebsiteById(id);
        String url = website.getUrl();
        List<WebsiteWordsCount> wordsCounts = wordsCountService.getAllCountsByUrl(url);
        return mapper.mapToDTO(wordsCounts, url);
    }
}
