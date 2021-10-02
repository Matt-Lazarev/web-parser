package com.project.webparser.repository;

import com.project.webparser.entity.WebsiteWordsCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WebsiteWordsCountRepository extends JpaRepository<WebsiteWordsCount, Long> {
    WebsiteWordsCount findWebsiteWordsCountByWebsiteIdAndWordId(Long websiteId, Long wordId);
    List<WebsiteWordsCount> findWebsiteWordsCountByWebsiteUrl(String url);
}
