package com.spring.cafer.cafe.controller;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import com.spring.cafer.client.CafeGraphQLQuery;
import com.spring.cafer.client.CafeProjectionRoot;
import com.spring.cafer.client.CafesGraphQLQuery;
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
//        직접 쿼리를 입력하는 방식
//        List<String> cafes = dgsQueryExecutor.executeAndExtractJsonPath(
//                " { cafes { id name description location } }",
//                "data.cafes[*].name");

//      쿼리 빌더 방식
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                CafesGraphQLQuery.newRequest().build(),
                new CafeProjectionRoot<>().name()
        );

        List<String> cafes = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQueryRequest.serialize(),"data.cafes[*].name");

        assertThat(cafes).contains("jackson");
    }
}