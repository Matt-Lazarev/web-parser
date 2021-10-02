package com.project.webparser.service.layer;

import com.project.webparser.entity.WebsiteWordsCount;

import java.util.List;

public interface WebsiteWordsCountService {
    WebsiteWordsCount saveCount(WebsiteWordsCount websiteWordsCount);

    List<WebsiteWordsCount> getAllCountsByUrl(String url);
}
