package com.opsly.app.web.mapper;

import com.opsly.app.domain.Twitter;
import com.opsly.app.web.dto.TwitterDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TwitterDtoMapper extends EntityMapper<TwitterDto, Twitter> {
}
