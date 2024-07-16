package com.spring.cafer.domain.cafe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cafer.domain.cafe.entity.Cafe;
import com.spring.cafer.domain.cafe.repository.CafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;

    private RestTemplate restTemplate = new RestTemplate();
    private Set<String> history = new HashSet<>();

    public void fetchAndSaveCafes(List<double[]> coordinates, double radius) throws IOException {
        for (double[] coordinate : coordinates) {
            String x = String.valueOf(coordinate[0]);
            String y = String.valueOf(coordinate[1]);

            if (history.contains(x + "," + y)) {
                continue; // 이미 처리된 좌표는 건너뜁니다.
            }

            int page = 1;
            boolean isEnd = false;

            while (!isEnd) {
                String url = String.format("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=%s&y=%s&radius=100&page=%d&sort=distance", x, y, page);
                try {
                    String responseData = restTemplate.getForObject(url, String.class);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(responseData);
                    JsonNode documents = rootNode.path("documents");
                    isEnd = rootNode.path("meta").path("is_end").asBoolean();

                    for (JsonNode doc : documents) {
                        Cafe cafe = Cafe.builder()
                                .addressName(doc.path("address_name").asText())
                                .roadAddressName(doc.path("road_address_name").asText())
                                .name(doc.path("place_name").asText())
                                .x(doc.path("x").asText())
                                .y(doc.path("y").asText())
                                .build();

                        cafeRepository.save(cafe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                page++;
            }

            history.add(x + "," + y); // 처리된 좌표를 히스토리에 추가
        }
    }
}