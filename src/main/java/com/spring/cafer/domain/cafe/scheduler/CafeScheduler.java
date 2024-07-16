package com.spring.cafer.domain.cafe.scheduler;

import com.spring.cafer.domain.cafe.service.CafeService;
import com.spring.cafer.global.util.CoordinateGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CafeScheduler {
    private final CafeService cafeService;

    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정에 실행
    public void scheduleCafeDataFetch() {
        double startX = 126.76;  // 서울의 서쪽 경도
        double startY = 37.40;   // 서울의 남쪽 위도
        double endX = 127.18;    // 서울의 동쪽 경도
        double endY = 37.70;     // 서울의 북쪽 위도
        double radius = 0.001;   // 약 100m 반경 (위도/경도 기준)

        CoordinateGenerator generator = new CoordinateGenerator();
        List<double[]> coordinates = generator.generateCoordinates(startX, startY, endX, endY, radius);

        try {
            cafeService.fetchAndSaveCafes(coordinates, radius);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}