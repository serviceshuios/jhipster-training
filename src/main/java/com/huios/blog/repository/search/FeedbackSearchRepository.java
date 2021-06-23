package com.huios.blog.repository.search;

import com.huios.blog.domain.Feedback;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Feedback} entity.
 */
public interface FeedbackSearchRepository extends ElasticsearchRepository<Feedback, Long> {}
