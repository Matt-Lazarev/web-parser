package com.project.webparser.service.parser;

import com.project.webparser.exception.WrongWebsiteUrlException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebsiteParser {

    public Map<String, Long> parseAllBodyWords(String url) throws IOException {

        if(!checkUrl(url)){
            throw new WrongWebsiteUrlException("Url '" + url + "' is incorrect");
        }

        Document doc = Jsoup.connect(url)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .header("accept-language", "ru,en;q=0.9")
                .header("accept-encoding", "gzip, deflate, br")
                .header("cache-control", "max-age=0")
                .timeout(10_000)
                .get();

        String text = doc.body().text();
        String[] words = text.split(" |,|\\.|;|:|!|\\?|(, )|(\\. )|\r|\n|\t");

        return Arrays
                .stream(words)
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(
                        x->x,
                        Collectors.counting()));
    }


    private boolean checkUrl(String url){
        return url.matches("https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)");
    }
}
