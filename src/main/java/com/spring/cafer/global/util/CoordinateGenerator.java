package com.spring.cafer.global.util;

import java.util.ArrayList;
import java.util.List;

public class CoordinateGenerator {
    public List<double[]> generateCoordinates(double startX, double startY, double endX, double endY, double radius) {
        List<double[]> coordinates = new ArrayList<>();

        for (double x = startX; x <= endX; x += radius) {
            for (double y = startY; y <= endY; y += radius) {
                coordinates.add(new double[]{x, y});
            }
        }

        return coordinates;
    }
}