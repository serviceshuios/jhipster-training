package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.FrontpageconfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frontpageconfig} and its DTO {@link FrontpageconfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FrontpageconfigMapper extends EntityMapper<FrontpageconfigDTO, Frontpageconfig> {}
