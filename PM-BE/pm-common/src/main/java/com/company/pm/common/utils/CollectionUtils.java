package com.company.pm.common.utils;

import java.util.Set;
import java.util.stream.Collectors;

public class CollectionUtils {
    
    public static Set<String> intersection(Set<String> s1, Set<String> s2) {
        return s1.stream()
            .filter(el1 -> s2.stream()
                .anyMatch(el2 -> el2.equals(el1))
            )
            .collect(Collectors.toSet());
    }
}
