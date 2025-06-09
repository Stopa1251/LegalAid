package com.example.lawtest.config;

import jakarta.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import jakarta.persistence.EntityManager;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class HibernateSearchIndexInitializer {
//    @PersistenceContext
//    private final EntityManager entityManager;
//
//    public HibernateSearchIndexInitializer(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @EventListener
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        SearchSession searchSession = Search.session(entityManager);
//        try {
//            searchSession.massIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
}