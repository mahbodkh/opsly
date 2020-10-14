package com.opsly.app.web.mapper;

import com.opsly.app.domain.Instagram;
import com.opsly.app.web.dto.InstagramDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InstagramDtoMapper extends EntityMapper<InstagramDto, Instagram> {

}
