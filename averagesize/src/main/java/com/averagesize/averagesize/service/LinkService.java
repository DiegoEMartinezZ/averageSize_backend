package com.averagesize.averagesize.service;

import java.util.List;
import java.util.Optional;

import com.averagesize.averagesize.entity.Link;

public interface LinkService {

    Link createLink(Link link);

    Optional<Link> getLinkById(long id);

    Link getLinkByShortUrl(String shortUrl);

    List<Link> getAllLinksByUserId(long userId);

    List<Link> getAllActiveLinks();

    Link updateLink(Link link);

    void deleteLink(long id);

    void deactivateLink(long id);

    void activateLink(long id);

    String generateShortUrl();

}
