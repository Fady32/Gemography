package com.repo.app.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    private static void updateCacheWithUpdatedLanguages(Map<String, HashMap<String, Object>> languagesCache, List<Map.Entry<String, HashMap<String, Object>>> sortedResult) {
        languagesCache.clear();
        sortedResult.forEach(stringHashMapEntry -> languagesCache.put(stringHashMapEntry.getKey(), stringHashMapEntry.getValue()));
    }

    public static Object sortResultDescending(Map<String, HashMap<String, Object>> languagesData, Map<String, HashMap<String, Object>> languageCache) {

        Comparator<Map.Entry<String, HashMap<String, Object>>> reposComparator = (o1, o2) -> Integer
                .compare(Integer.parseInt(o2.getValue().get("ReposCount").toString()), Integer.parseInt(o1.getValue().get("ReposCount").toString()));
        List<Map.Entry<String, HashMap<String, Object>>> sortedData = languagesData.entrySet().stream().sorted(reposComparator).collect(Collectors.toList());

        updateCacheWithUpdatedLanguages(languageCache, sortedData);

        return sortedData;
    }

}
