package com.core.utils;

import static com.core.utils.CommonConstants.CHAR_COLON;
import static com.core.utils.CommonConstants.CHAR_COMMA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MultiFiltersSearch {

    public static Map<String, String> getFilters(String filters, Map<String, String> filtersMap) {
        String[] matchFilters = filters.split(String.valueOf(CHAR_COMMA));
        for (int index = 0; index < matchFilters.length; index++) {
            String[] matchFilter = matchFilters[index].split(String.valueOf(CHAR_COLON));
            if (matchFilter.length > 1 && !matchFilter[0].trim().isEmpty() && !matchFilter[1].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                String match = matchFilter[1].trim();
                filtersMap.put(filter, match);
            } else if (matchFilter.length == 1 && !matchFilter[0].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                filtersMap.put(filter, null);
            }
        }
        return filtersMap;

    }

    public static Map<String, List<String>> getFiltersMatchList(String filters, Map<String, List<String>> filtersMap) {
        String[] matchFilters = filters.split(String.valueOf(CHAR_COMMA));
        for (int index = 0; index < matchFilters.length; index++) {
            List<String> matchStrings = new ArrayList<>();
            String[] matchFilter = matchFilters[index].split(String.valueOf(CHAR_COLON));
            if (matchFilter.length > 1 && !matchFilter[0].trim().isEmpty() && !matchFilter[1].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                String match = matchFilter[1].trim();
                if (filtersMap.get(filter) != null) {
                    matchStrings = filtersMap.get(filter);
                }
                matchStrings.add(match);
                filtersMap.put(filter, matchStrings);
            } else if (matchFilter.length == 1 && !matchFilter[0].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                filtersMap.put(filter, null);
            }
        }
        return filtersMap;

    }

    

}
