package com.averagesize.averagesize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.averagesize.averagesize.entity.Click;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {
    @Query("SELECT c FROM Click c JOIN c.link l WHERE l.urlShort = :url OR l.urlOriginal = :url")
    public List<Click> findByLinkUrl(@Param("url") String url);
}
