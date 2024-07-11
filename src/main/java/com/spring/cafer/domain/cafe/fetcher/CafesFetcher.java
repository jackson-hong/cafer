package com.spring.cafer.domain.cafe.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;

import java.util.List;

@DgsComponent
public class CafesFetcher {

    private final List<Cafe> cafes = List.of(
            new Cafe("1", "jackson", "blah", "songpa")
    );

    @DgsQuery
    public List<Cafe> cafes() {
        return cafes;
    }
}

record Cafe(String id, String name, String description, String location) {
}
