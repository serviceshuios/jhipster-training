package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.CelebDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Celeb} and its DTO {@link CelebDTO}.
 */
@Mapper(componentModel = "spring", uses = { AppuserMapper.class })
public interface CelebMapper extends EntityMapper<CelebDTO, Celeb> {
    @Mapping(target = "appusers", source = "appusers", qualifiedByName = "idSet")
    CelebDTO toDto(Celeb s);

    @Mapping(target = "removeAppuser", ignore = true)
    Celeb toEntity(CelebDTO celebDTO);
}
