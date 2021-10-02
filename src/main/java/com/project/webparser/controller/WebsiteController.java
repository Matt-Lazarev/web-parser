package com.project.webparser.controller;

import com.project.webparser.entity.Website;
import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.entity.Word;
import com.project.webparser.service.parser.WebsiteParser;
import com.project.webparser.service.layer.WebsiteService;
import com.project.webparser.service.layer.WebsiteWordsCountService;
import com.project.webparser.service.layer.WordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebsiteController {

    private final WebsiteParser websiteParser;
    private final WebsiteService websiteService;
    private final WordService wordService;
    private final WebsiteWordsCountService wordsCountService;

    public WebsiteController(
            WebsiteParser websiteParser, WebsiteService websiteService,
            WordService wordService, WebsiteWordsCountService websiteWordsCountService) {
        this.websiteParser = websiteParser;
        this.websiteService = websiteService;
        this.wordService = wordService;
        this.wordsCountService = websiteWordsCountService;
    }

    @GetMapping("/parser")
    public String getInputWebsiteUrl(Model model){
        model.addAttribute("website", new Website());
        return "input_website_url";
    }

    @PostMapping("/parser")
    public String saveWebsiteUrl(@Valid @ModelAttribute("website") Website website,
                                 BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return "input_website_url";
        }

        String url = website.getUrl();

        Map<String, Long> wordsCount = websiteParser.parseAllBodyWords(url);
        website = websiteService.saveWebsite(website);

        List<Word> words = wordsCount
                .keySet()
                .stream()
                .map(name -> new Word(name.toLowerCase()))
                .collect(Collectors.toList());

        words = wordService.saveAllWords(words);

        for(Word w: words){
            WebsiteWordsCount count = new WebsiteWordsCount(website, w, wordsCount.get(w.getName()));
            wordsCountService.saveCount(count);
        }

        return "redirect:/statistics/" + website.getId();
    }

}
