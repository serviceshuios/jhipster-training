package com.huios.blog.repository;

import com.huios.blog.domain.ConfigVariables;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConfigVariables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConfigVariablesRepository extends JpaRepository<ConfigVariables, Long>, JpaSpecificationExecutor<ConfigVariables> {}
