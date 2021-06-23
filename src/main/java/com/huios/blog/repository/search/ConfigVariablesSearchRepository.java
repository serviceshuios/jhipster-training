package com.huios.blog.repository.search;

import com.huios.blog.domain.ConfigVariables;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ConfigVariables} entity.
 */
public interface ConfigVariablesSearchRepository extends ElasticsearchRepository<ConfigVariables, Long> {}
