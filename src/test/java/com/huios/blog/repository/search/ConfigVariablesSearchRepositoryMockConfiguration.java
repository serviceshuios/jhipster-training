package com.huios.blog.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ConfigVariablesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ConfigVariablesSearchRepositoryMockConfiguration {

    @MockBean
    private ConfigVariablesSearchRepository mockConfigVariablesSearchRepository;
}
