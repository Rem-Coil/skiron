package com.remcoil.skiron.database.entity;

import java.util.HashMap;
import java.util.Map;

public enum ControlType {
    OTK,
    TESTING;

    public static Map<ControlType, Integer> getCountMap() {
        Map<ControlType, Integer> countMap = new HashMap<>();

        countMap.put(OTK, 0);
        countMap.put(TESTING, 0);

        return countMap;
    }
}
