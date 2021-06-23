package com.huios.blog.repository.search;

import com.huios.blog.domain.Blockuser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Blockuser} entity.
 */
public interface BlockuserSearchRepository extends ElasticsearchRepository<Blockuser, Long> {}
