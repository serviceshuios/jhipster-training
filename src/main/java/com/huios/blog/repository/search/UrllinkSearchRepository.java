package com.huios.blog.repository.search;

import com.huios.blog.domain.Urllink;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Urllink} entity.
 */
public interface UrllinkSearchRepository extends ElasticsearchRepository<Urllink, Long> {}
