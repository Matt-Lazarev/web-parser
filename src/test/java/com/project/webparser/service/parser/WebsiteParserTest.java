package com.project.webparser.service.parser;

import com.project.webparser.exception.WrongWebsiteUrlException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebsiteParserTest {

    @Autowired
    private WebsiteParser parser;


    @Test
    public void canParseWebsite_WrongUrl(){
        String wrongUrl = "vk.com";

        assertThatThrownBy(()->parser.parseAllBodyWords(wrongUrl))
                .isInstanceOf(WrongWebsiteUrlException.class);
    }

    @Test
    public void canParseWebsite_CannotHandshake(){
        String wrongUrl = "https://microsoft.co";

        assertThatThrownBy(()->parser.parseAllBodyWords(wrongUrl))
                .isInstanceOf(Exception.class);
    }

    @SneakyThrows
    @Test
    public void canParseWebsite_Success(){
        String url = "https://microsoft.com";

        Map<String, Long> map = parser.parseAllBodyWords(url);

        assertThat(map.size()).isGreaterThan(0);
    }
}