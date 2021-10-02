package com.project.webparser.service.layer;

import com.project.webparser.entity.Website;

import java.util.Optional;

public interface WebsiteService {
    Website getWebsiteById(Long id);

    Website getWebsiteByUrl(String url);

    Website saveWebsite(Website website);
}
