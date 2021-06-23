package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.CcelebDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cceleb} and its DTO {@link CcelebDTO}.
 */
@Mapper(componentModel = "spring", uses = { CommunityMapper.class })
public interface CcelebMapper extends EntityMapper<CcelebDTO, Cceleb> {
    @Mapping(target = "communities", source = "communities", qualifiedByName = "idSet")
    CcelebDTO toDto(Cceleb s);

    @Mapping(target = "removeCommunity", ignore = true)
    Cceleb toEntity(CcelebDTO ccelebDTO);
}
