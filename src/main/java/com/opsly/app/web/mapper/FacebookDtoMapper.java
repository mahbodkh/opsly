package com.opsly.app.web.mapper;

import com.opsly.app.domain.Facebook;
import com.opsly.app.web.dto.FacebookDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FacebookDtoMapper extends EntityMapper<FacebookDto, Facebook> {
}
