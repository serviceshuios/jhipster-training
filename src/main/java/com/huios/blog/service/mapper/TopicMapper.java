package com.huios.blog.service.mapper;

import com.huios.blog.domain.*;
import com.huios.blog.service.dto.TopicDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Topic} and its DTO {@link TopicDTO}.
 */
@Mapper(componentModel = "spring", uses = { PostMapper.class })
public interface TopicMapper extends EntityMapper<TopicDTO, Topic> {
    @Mapping(target = "posts", source = "posts", qualifiedByName = "headlineSet")
    TopicDTO toDto(Topic s);

    @Mapping(target = "removePost", ignore = true)
    Topic toEntity(TopicDTO topicDTO);
}
