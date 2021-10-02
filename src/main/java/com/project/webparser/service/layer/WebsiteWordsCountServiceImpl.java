package com.project.webparser.service.layer;

import com.project.webparser.entity.WebsiteWordsCount;
import com.project.webparser.repository.WebsiteWordsCountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteWordsCountServiceImpl implements WebsiteWordsCountService {

    private final WebsiteWordsCountRepository repository;

    public WebsiteWordsCountServiceImpl(WebsiteWordsCountRepository repository) {
        this.repository = repository;
    }

    @Override
    public WebsiteWordsCount saveCount(WebsiteWordsCount websiteWordsCount) {

        Long websiteId = websiteWordsCount.getWebsite().getId();
        Long wordId = websiteWordsCount.getWord().getId();

        WebsiteWordsCount updatedCount =
                repository.findWebsiteWordsCountByWebsiteIdAndWordId(websiteId, wordId);

        if(updatedCount == null){
            return repository.save(websiteWordsCount);
        }
        else {
            return updatedCount;
        }
    }

    @Override
    public List<WebsiteWordsCount> getAllCountsByUrl(String url) {
        return repository.findWebsiteWordsCountByWebsiteUrl(url);
    }
}
