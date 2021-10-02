package com.project.webparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class WebsiteWordCountDTO {
    private String url;
    private Map<String, Long> wordCount;
}
