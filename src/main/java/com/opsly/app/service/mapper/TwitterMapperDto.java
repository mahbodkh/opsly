package com.opsly.app.service.mapper;

import com.opsly.app.web.dto.TwitterDto;
import com.opsly.app.domain.Twitter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TwitterMapperDto extends EntityMapper<TwitterDto, Twitter> {
}
