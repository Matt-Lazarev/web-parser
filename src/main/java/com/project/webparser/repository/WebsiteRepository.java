package com.project.webparser.repository;

import com.project.webparser.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
    Website getWebsiteByUrl(String url);
}
