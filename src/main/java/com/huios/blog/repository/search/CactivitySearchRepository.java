package com.huios.blog.repository.search;

import com.huios.blog.domain.Cactivity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cactivity} entity.
 */
public interface CactivitySearchRepository extends ElasticsearchRepository<Cactivity, Long> {}
