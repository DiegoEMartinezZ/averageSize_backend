package com.averagesize.averagesize.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.averagesize.averagesize.entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    @Query("SELECT l FROM Link l WHERE l.urlOriginal = :url OR l.urlShort = :url")
    Optional<Link> findByAnyUrl(@Param("url") String url);

    Optional<Link> findByUrlShort(String shortUrl);

    @Query("SELECT l FROM Link l WHERE l.user.idUser = :userId")
    List<Link> findByUserIdUser(@Param("userId") long userId);

    List<Link> findByActiveTrue();
}
