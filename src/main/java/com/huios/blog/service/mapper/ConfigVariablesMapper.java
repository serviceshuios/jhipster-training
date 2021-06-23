package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.ConfigVariablesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConfigVariables} and its DTO {@link ConfigVariablesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigVariablesMapper extends EntityMapper<ConfigVariablesDTO, ConfigVariables> {}
