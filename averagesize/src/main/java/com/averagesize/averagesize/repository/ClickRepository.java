package com.averagesize.averagesize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.averagesize.averagesize.entity.Click;

@Repository
public interface ClickRepository extends JpaRepository<Click, Long> {
    // Existing method
    @Query("SELECT c FROM Click c JOIN c.link l WHERE l.urlShort = :url OR l.urlOriginal = :url")
    List<Click> findByLinkUrl(@Param("url") String url);

    // New method to find clicks by link ID
    List<Click> findByLinkIdLink(Long linkId);

    // Count clicks for a specific link
    long countByLinkIdLink(Long link);

    // Get clicks for a specific link, ordered by click timestamp (newest first)
    @Query("SELECT c FROM Click c WHERE c.link.idLink = :linkId ORDER BY c.clickAt DESC")
    List<Click> findByLinkIdLinkOrderByClickAtDesc(@Param("linkId") Long linkId);

    // Count clicks grouped by device
    @Query("SELECT c.device, COUNT(c) FROM Click c WHERE c.link.idLink = :linkId GROUP BY c.device")
    List<Object[]> countClicksByDevice(@Param("linkId") Long linkId);

    // Count clicks grouped by navigator (browser)
    @Query("SELECT c.navigator, COUNT(c) FROM Click c WHERE c.link.idLink = :linkId GROUP BY c.navigator")
    List<Object[]> countClicksByNavigator(@Param("linkId") Long linkId);

    // Count clicks grouped by location
    @Query("SELECT c.location, COUNT(c) FROM Click c WHERE c.link.idLink = :linkId GROUP BY c.location")
    List<Object[]> countClicksByLocation(@Param("linkId") Long linkId);
}