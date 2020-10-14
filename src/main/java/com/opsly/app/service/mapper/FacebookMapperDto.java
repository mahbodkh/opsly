package com.opsly.app.service.mapper;

import com.opsly.app.domain.Facebook;
import com.opsly.app.web.dto.FacebookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacebookMapperDto extends EntityMapper<FacebookDto, Facebook> {
}
