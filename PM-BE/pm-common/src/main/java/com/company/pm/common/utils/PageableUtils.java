package com.company.pm.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public class PageableUtils {
    
    public static Pageable toPageable(ServerWebExchange exchange) {
        List<Integer> pageableList = extractPageLimit(exchange);
        
        return PageRequest.of(pageableList.get(0), pageableList.get(1));
    }
    
    public static List<Integer> extractPageLimit(ServerWebExchange exchange) {
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String pageStr = queryParams.getFirst("page");
        String limitStr = queryParams.getFirst("limit");
        
        if (pageStr == null) {
            pageStr = "1";
        }
        if (limitStr == null) {
            limitStr = "10";
        }
        
        int limit = Integer.parseInt(limitStr);
        int page = Integer.parseInt(pageStr);
        
        return List.of(page, limit);
    }
}
