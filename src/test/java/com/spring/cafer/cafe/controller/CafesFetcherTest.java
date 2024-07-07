package com.spring.cafer.cafe.controller;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.spring.cafer.domain.cafe.controller.CafesFetcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {DgsAutoConfiguration.class, CafesFetcher.class})
public class CafesFetcherTest {

    @Autowired
    private DgsQueryExecutor dgsQueryExecutor;

    @Test
    void testCafesQuery() {
        List<String> cafes = dgsQueryExecutor.executeAndExtractJsonPath(
                " { cafes { id name description location } }",
                "data.cafes[*].name");

        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
        )

        assertThat(cafes).contains("jackson");
    }
}