package com.averagesize.averagesize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.averagesize.averagesize.entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.url_original = :url OR l.url_short = :url")
    public Optional<Link> findByAnyUrl(@Param("url") String url);
}
