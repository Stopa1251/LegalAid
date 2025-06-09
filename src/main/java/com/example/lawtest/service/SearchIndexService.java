package com.example.lawtest.service;

import com.example.lawtest.entity.Lawyer;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
//import org.hibernate.search.mapper.orm.Search;
import jakarta.persistence.EntityManager;
//import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchIndexService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void reindex() {
        Search.session(entityManager)
                .massIndexer()
                .start();
    }


    public List<Lawyer> searchLawyers(String keyword) {
        SearchSession searchSession = Search.session(entityManager);
        return searchSession.search(Lawyer.class)
                .where(f -> f.match()
//                        .fields("firstName", "lastName", "specialization", "portfolio")
                        .fields("specialization", "portfolio")
                        .matching(keyword)
                        .fuzzy(2)) // для неточних збігів
//                .sort(TypedSearchSortFactory::score) // сортування за релевантністю
                .fetchHits(20);
    }
}