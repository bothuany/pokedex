package com.obss.pokedexapi.util;

import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class CollectionUtils {
    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if (pageSize<=0 || page<0) {
            throw new IllegalArgumentException("Page size and page number must be positive");
        }

        int fromIndex = (page-1) * pageSize;
        if (sourceList==null || sourceList.size()<fromIndex) {
            return Collections.emptyList();
        }

        return sourceList.subList(fromIndex, Math.min(fromIndex+pageSize, sourceList.size()));
    }

    public static boolean isEmpty(List<?> list) {
        return Objects.isNull(list) || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }
}