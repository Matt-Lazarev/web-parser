package com.project.webparser.service.layer;

import com.project.webparser.entity.Website;
import com.project.webparser.repository.WebsiteRepository;
import org.springframework.stereotype.Service;

@Service
public class WebsiteServiceImpl implements WebsiteService{

    private final WebsiteRepository websiteRepository;

    public WebsiteServiceImpl(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    @Override
    public Website getWebsiteById(Long id) {
        return websiteRepository.findById(id).get();
    }

    @Override
    public Website getWebsiteByUrl(String url) {
        return websiteRepository.getWebsiteByUrl(url);
    }

    @Override
    public Website saveWebsite(Website website) {
        Website updatedWebsite = websiteRepository.getWebsiteByUrl(website.getUrl());

        if(updatedWebsite == null){
            return websiteRepository.save(website);
        }
        else {
            return updatedWebsite;
        }
    }
}
