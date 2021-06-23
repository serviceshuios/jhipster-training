package com.huios.blog.repository.search;

import com.huios.blog.domain.Cceleb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cceleb} entity.
 */
public interface CcelebSearchRepository extends ElasticsearchRepository<Cceleb, Long> {}
