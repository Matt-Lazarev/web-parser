package com.project.webparser.controller;

import com.project.webparser.entity.Website;
import com.project.webparser.exception.WrongWebsiteUrlException;
import com.project.webparser.service.parser.WebsiteParser;
import com.project.webparser.service.layer.WebsiteService;
import com.project.webparser.service.layer.WebsiteWordsCountService;
import com.project.webparser.service.layer.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.mockito.BDDMockito.given;

@WebMvcTest(WebsiteController.class)
class WebsiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebsiteService websiteService;

    @MockBean
    private WebsiteParser websiteParser;

    @MockBean
    private WordService wordService;

    @MockBean
    private WebsiteWordsCountService wordsCountService;

    @Test
    @DisplayName("Given - URL, When GET '/parser' request is called, Then expect OK")
    public void canGetParserStartPage() throws Exception {
        String url = "/parser";

        mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Given - URL & Website, When POST '/parser' request is called, Then expect OK")
    public void canSaveWebsiteSuccess() throws Exception {
        String url = "/parser";
        Website website = new Website(1L, "https://www.microsoft.com");
        given(websiteService.saveWebsite(website))
                .willReturn(website);

        mockMvc.perform(MockMvcRequestBuilders.post(url))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Given - wrong website URL & Website, When POST '/parser' request is called, Then expect BAD_REQUEST")
    public void canSaveWebsiteBadRequest() throws Exception {
        String url = "/parser";
        String websiteURL = "https://www.microsoft";
        Website website = new Website(1L, websiteURL);

        given(websiteParser.parseAllBodyWords(websiteURL))
                .willAnswer(invocationOnMock -> {throw new IOException();});

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("id",Long.toString(website.getId()))
                .param("url", website.getUrl())
                .flashAttr("website", new Website()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Given - wrong website URL & Website, When POST '/parser' request is called, Then expect NOT_FOUND")
    public void canSaveWebsiteNotFound() throws Exception {
        String url = "/parser";
        String websiteURL = "wrong.url";
        Website website = new Website(1L, websiteURL);

        given(websiteParser.parseAllBodyWords(websiteURL))
                .willThrow(WrongWebsiteUrlException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("id",Long.toString(website.getId()))
                .param("url", website.getUrl())
                .flashAttr("website", new Website()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}