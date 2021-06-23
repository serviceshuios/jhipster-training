package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.UrllinkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Urllink} and its DTO {@link UrllinkDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UrllinkMapper extends EntityMapper<UrllinkDTO, Urllink> {}
