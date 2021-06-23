package com.huios.blog.repository.search;

import com.huios.blog.domain.Celeb;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Celeb} entity.
 */
public interface CelebSearchRepository extends ElasticsearchRepository<Celeb, Long> {}
