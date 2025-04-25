package com.averagesize.averagesize.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.averagesize.averagesize.dto.ClickResDTO;
import com.averagesize.averagesize.entity.Click;
import com.averagesize.averagesize.exceptions.ResourceNotFoundException;
import com.averagesize.averagesize.mapper.ClickMapper;
import com.averagesize.averagesize.repository.ClickRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClickService {
    private final ClickRepository clickRepository;
    private final ClickMapper clickMapper;

    // List clicks
    @Transactional(readOnly = true)
    public List<ClickResDTO> findAll() {
        List<Click> clicks = clickRepository.findAll();
        return clicks.stream().map(clickMapper::toDto).collect(Collectors.toList());
    }

    // Find clicks by ID
    @Transactional(readOnly = true)
    public ClickResDTO findById(Long id) {
        Click click = clickRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Doesn't Exist"));
        return clickMapper.toDto(click);
    }

    // Find clicks by Link ID
    public List<ClickResDTO> findByLinkId(Long linkId) {
        List<Click> clicks = clickRepository.findByLinkIdLink(linkId);
        return clicks.stream().map(clickMapper::toDto)
                .collect(Collectors.toList());
    }

    // Count clicks for a specific link
    public long countByLinkId(Long linkId) {
        return clickRepository.countByLinkIdLink(linkId);
    }
}
