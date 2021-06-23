package com.huios.blog.repository.search;

import com.huios.blog.domain.Appuser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Appuser} entity.
 */
public interface AppuserSearchRepository extends ElasticsearchRepository<Appuser, Long> {}
