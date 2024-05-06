package com.ssafy.businesscard.domain.team.service.impl;

import com.ssafy.businesscard.domain.card.dto.request.FilterRequest;
import com.ssafy.businesscard.domain.card.entity.Filter;
import com.ssafy.businesscard.domain.card.mapper.FilterMapper;
import com.ssafy.businesscard.domain.team.entity.TeamAlbum;
import com.ssafy.businesscard.domain.team.entity.TeamAlbumMember;
import com.ssafy.businesscard.domain.team.repository.TeamAlbumDetailRepository;
import com.ssafy.businesscard.domain.team.repository.TeamAlbumFilterRepository;
import com.ssafy.businesscard.domain.team.repository.TeamAlbumMemberRepository;
import com.ssafy.businesscard.domain.team.repository.TeamAlbumRepository;
import com.ssafy.businesscard.domain.team.service.TeamFilterService;
import com.ssafy.businesscard.global.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamFilterServiceImpl implements TeamFilterService {

    private final FilterMapper filterMapper;
    private final TeamAlbumRepository teamAlbumRepository;
    private final TeamAlbumDetailRepository teamAlbumDetailRepository;
    private final TeamAlbumFilterRepository teamAlbumFilterRepository;
    private final TeamAlbumMemberRepository teamAlbumMemberRepository;

    // 필터 생성
    @Override
    public void create(Long teamAlbumId, FilterRequest request) {
        Filter filter = filterMapper.toEntity(request);
        teamAlbumFilterRepository.save(filter);
        saveFilter(teamAlbumId, filter.getFilterId());
    }

    // 필터 생성 후 filterId와 TeamAlbumId를 중계 테이블에 저장
    private void saveFilter(Long teamAlbumId, Long filterId) {
        TeamAlbum teamAlbum = teamAlbumRepository.findById(teamAlbumId)
                .orElseThrow(() -> new GlobalExceptionHandler.UserException(
                        GlobalExceptionHandler.UserErrorCode.NOT_EXITSTS_TEAM
                ));

        Filter filter = teamAlbumFilterRepository.findById(filterId)
                .orElseThrow(() -> new GlobalExceptionHandler.UserException(
                        GlobalExceptionHandler.UserErrorCode.NOT_EXISTS_FILTER
                ));

        teamAlbumMemberRepository.save(TeamAlbumMember.builder()
                .filter(filter)
                .teamAlbum(teamAlbum)
                .build());
    }
}
