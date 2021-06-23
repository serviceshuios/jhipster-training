package com.huios.blog.repository.search;

import com.huios.blog.domain.Appphoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Appphoto} entity.
 */
public interface AppphotoSearchRepository extends ElasticsearchRepository<Appphoto, Long> {}
