package com.project.webparser.controller;

import com.project.webparser.dto.WebsiteWordCountDTO;
import com.project.webparser.entity.Website;
import com.project.webparser.service.layer.WebsiteService;
import com.project.webparser.service.mapper.WebsiteWordsCountMapper;
import com.project.webparser.service.layer.WebsiteWordsCountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebsiteService websiteService;

    @MockBean
    private WebsiteController websiteController;

    @MockBean
    private WebsiteWordsCountService wordsCountService;

    @MockBean
    private WebsiteWordsCountMapper mapper;

    @Test
    @DisplayName("Given - URL, When GET '/statistics/{websiteId}' request is called, Then expect OK")
    public void canGetParserStartPage() throws Exception {
        Website website = new Website(1L, "https://www.microsoft.com");

        String websiteUrl = website.getUrl();
        long websiteId = website.getId();

        String url = "/statistics/" + websiteId;

        given((websiteService.getWebsiteById(websiteId)))
                .willReturn(website);

        given(mapper.mapToDTO(anyList(), eq(websiteUrl)))
                .willReturn(new WebsiteWordCountDTO(websiteUrl, Map.of()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.url")
                        .value(websiteUrl)).andReturn();

        System.out.println(mvcResult.getResponse());
    }

}