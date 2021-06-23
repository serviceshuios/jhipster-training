package com.huios.blog.repository.search;

import com.huios.blog.domain.Cinterest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cinterest} entity.
 */
public interface CinterestSearchRepository extends ElasticsearchRepository<Cinterest, Long> {}
