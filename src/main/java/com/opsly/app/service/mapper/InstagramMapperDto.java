package com.opsly.app.service.mapper;

import com.opsly.app.domain.Instagram;
import com.opsly.app.web.dto.InstagramDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstagramMapperDto extends EntityMapper<InstagramDto, Instagram> {
}
