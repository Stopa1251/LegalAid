package com.example.lawtest.config;

//import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
//import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.stereotype.Component;

@Component
//public class MyAnalysisConfigurer implements LuceneAnalysisConfigurer {
public class MyAnalysisConfigurer {
//    @Override
//    public void configure(LuceneAnalysisConfigurationContext context) {
//        context.analyzer("ukrainian").custom()
//                .tokenizer("standard")
//                .charFilter("htmlStrip")
//                .tokenFilter("lowercase")
//                .tokenFilter("stop") // можна додати власний список стоп-слів
//                .tokenFilter("synonym").param("synonyms", "адвокат,юрист\nсправа,замовлення")
//                .tokenFilter("edgeNGram").param("minGramSize", "2").param("maxGramSize", "15"); // автодоповнення
//    }
}