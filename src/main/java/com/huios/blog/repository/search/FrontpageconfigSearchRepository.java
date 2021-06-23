package com.huios.blog.repository.search;

import com.huios.blog.domain.Frontpageconfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Frontpageconfig} entity.
 */
public interface FrontpageconfigSearchRepository extends ElasticsearchRepository<Frontpageconfig, Long> {}
