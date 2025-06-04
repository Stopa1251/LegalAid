package com.example.lawtest.service;

import com.example.lawtest.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
//
//    private final ElasticsearchRestTemplate elasticsearchTemplate;
//
//    public List<Order> searchOrders(String query) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery(query)
//                        .field("title")
//                        .field("description")
//                        .field("legalField")
//                        .fuzziness(Fuzziness.AUTO)) // часткові збіги, помилки
//                .build();
//
//        SearchHits<Order> hits = elasticsearchTemplate.search(searchQuery, Order.class);
//        return hits.stream().map(SearchHit::getContent).toList();
//    }
//
//    public List<Lawyer> searchLawyers(String query) {
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery(query)
//                        .field("firstName")
//                        .field("lastName")
//                        .field("experience")
//                        .field("legalField")
//                        .fuzziness(Fuzziness.AUTO))
//                .build();
//
//        SearchHits<Lawyer> hits = elasticsearchTemplate.search(searchQuery, Lawyer.class);
//        return hits.stream().map(SearchHit::getContent).toList();
//    }
}