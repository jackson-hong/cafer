package com.spring.cafer.domain.cafe.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CafeController {

    private final List<Cafe> cafes = List.of(
            new Cafe("1", "jackson", "blah", "songpa")
    );

    @QueryMapping
    public List<Cafe> cafes() {
        return cafes;
    }
}

record Cafe(String id, String name, String description, String location) {}