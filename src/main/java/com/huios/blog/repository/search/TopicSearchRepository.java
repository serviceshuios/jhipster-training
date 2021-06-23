package com.huios.blog.repository.search;

import com.huios.blog.domain.Topic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Topic} entity.
 */
public interface TopicSearchRepository extends ElasticsearchRepository<Topic, Long> {}
