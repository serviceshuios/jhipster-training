package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.AppphotoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Appphoto} and its DTO {@link AppphotoDTO}.
 */
@Mapper(componentModel = "spring", uses = { AppuserMapper.class })
public interface AppphotoMapper extends EntityMapper<AppphotoDTO, Appphoto> {
    @Mapping(target = "appuser", source = "appuser", qualifiedByName = "id")
    AppphotoDTO toDto(Appphoto s);
}
