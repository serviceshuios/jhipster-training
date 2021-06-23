package com.huios.blog.repository.search;

import com.huios.blog.domain.Community;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Community} entity.
 */
public interface CommunitySearchRepository extends ElasticsearchRepository<Community, Long> {}
