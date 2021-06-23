package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.ActivityDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = { AppuserMapper.class })
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {
    @Mapping(target = "appusers", source = "appusers", qualifiedByName = "idSet")
    ActivityDTO toDto(Activity s);

    @Mapping(target = "removeAppuser", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);
}
