package com.averagesize.averagesize.service;

import java.util.List;
import java.util.UUID;

import com.averagesize.averagesize.dto.LinkReqDTO;
import com.averagesize.averagesize.dto.LinkResDTO;
import com.averagesize.averagesize.dto.LinkUpdateDTO;

public interface LinkService {

    LinkResDTO createLink(LinkReqDTO linkDTO, UUID userId);

    LinkResDTO getLinkById(long id);

    LinkResDTO getLinkByShortUrl(String shortUrl);

    List<LinkResDTO> getAllLinksByUserId(UUID userId);

    List<LinkResDTO> getAllActiveLinks();

    LinkResDTO updateLink(long linkId, LinkUpdateDTO linkDTO);

    void deleteLink(long id);

    LinkResDTO deactivateLink(long id);

    LinkResDTO activateLink(long id);

    String generateShortUrl();

}
