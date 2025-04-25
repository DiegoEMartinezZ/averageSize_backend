package com.averagesize.averagesize.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.averagesize.averagesize.dto.ClickReqDTO;
import com.averagesize.averagesize.dto.ClickResDTO;
import com.averagesize.averagesize.entity.Click;
import com.averagesize.averagesize.entity.Link;

@Component
public class ClickMapper {

    public Click toEntity(ClickReqDTO dto, Link link) {
        return Click.builder()
                .link(link)
                .ipUser(dto.getIpUser())
                .navigator(dto.getNavigator())
                .device(dto.getDevice())
                .location(dto.getLocation())
                .clickAt(LocalDateTime.now())
                .build();
    }

    public ClickResDTO toDto(Click click) {
        return ClickResDTO.builder()
                .idClick(click.getIdClick())
                .linkId(click.getLink().getIdLink())
                .linkShortUrl(click.getLink().getUrlShort())
                .ipUser(click.getIpUser())
                .navigator(click.getNavigator())
                .device(click.getDevice())
                .location(click.getLocation())
                .clickAt(click.getClickAt())
                .build();
    }
}