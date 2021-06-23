package com.huios.blog.repository.search;

import com.huios.blog.domain.Follow;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Follow} entity.
 */
public interface FollowSearchRepository extends ElasticsearchRepository<Follow, Long> {}
