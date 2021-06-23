package com.huios.blog.repository.search;

import com.huios.blog.domain.Interest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Interest} entity.
 */
public interface InterestSearchRepository extends ElasticsearchRepository<Interest, Long> {}
