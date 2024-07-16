package com.spring.cafer.domain.cafe.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

@DgsComponent
public class CafesFetcher {

    private final List<CafeDto> cafeDtos = List.of(
            new CafeDto("1", "jackson", "blah", "songpa")
    );

    @DgsQuery
    public List<CafeDto> cafes() {
        return cafeDtos;
    }
}

record CafeDto(String id, String name, String description, String location) {
}
