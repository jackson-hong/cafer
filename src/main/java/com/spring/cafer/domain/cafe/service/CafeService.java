package com.spring.cafer.domain.cafe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.cafer.domain.cafe.entity.Cafe;
import com.spring.cafer.domain.cafe.entity.CoordinateHistory;
import com.spring.cafer.domain.cafe.repository.CafeRepository;
import com.spring.cafer.domain.cafe.repository.CoordinateHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CafeService {
    private final CafeRepository cafeRepository;
    private final CoordinateHistoryRepository coordinateHistoryRepository;

    private RestTemplate restTemplate = new RestTemplate();

    private String fetchDataWithAuth(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 84e62d0c8047ce8e31ea69639097fbad");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        log.info("Fetching data from URL: {}", url);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    @Transactional
    public void fetchAndSaveCafes(List<double[]> coordinates, double radius) throws IOException {
        for (double[] coordinate : coordinates) {
            String x = String.valueOf(coordinate[0]);
            String y = String.valueOf(coordinate[1]);

            log.info("Processing coordinates: x={}, y={}", x, y);

            if (coordinateHistoryRepository.existsByXAndY(x, y)) {
                log.info("Skipping already processed coordinates: x={}, y={}", x, y);
                continue; // 이미 처리된 좌표는 건너뜁니다.
            }

            int page = 1;
            boolean isEnd = false;

            while (!isEnd) {
                String url = String.format("https://dapi.kakao.com/v2/local/search/category.json?category_group_code=CE7&x=%s&y=%s&radius=%d&page=%d&sort=distance", x, y, (int) radius, page);
                try {
                    String responseData = fetchDataWithAuth(url);
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode rootNode = mapper.readTree(responseData);
                    JsonNode documents = rootNode.path("documents");
                    isEnd = rootNode.path("meta").path("is_end").asBoolean();

                    log.info("Fetched {} documents from API", documents.size());

                    for (JsonNode doc : documents) {
                        Cafe cafe = Cafe.builder()
                                .addressName(doc.path("address_name").asText())
                                .roadAddressName(doc.path("road_address_name").asText())
                                .name(doc.path("place_name").asText())
                                .x(doc.path("x").asText())
                                .y(doc.path("y").asText())
                                .build();

                        saveCafe(cafe);
                        log.info("Saved cafe: {}", cafe);
                    }
                } catch (Exception e) {
                    log.error("Error fetching or saving data for coordinates: x={}, y={}", x, y, e);
                    e.printStackTrace();
                }

                page++;
            }

            saveCoordinateHistory(x, y);
            log.info("Added coordinates to history: x={}, y={}", x, y);
        }
    }

    @Transactional
    public void saveCafe(Cafe cafe) {
        cafeRepository.save(cafe);
        log.info("Saved cafe: {}", cafe);
    }

    @Transactional
    public void saveCoordinateHistory(String x, String y) {
        CoordinateHistory coordinateHistory = CoordinateHistory.builder()
                .x(x)
                .y(y)
                .build();
        coordinateHistoryRepository.save(coordinateHistory);
    }
}