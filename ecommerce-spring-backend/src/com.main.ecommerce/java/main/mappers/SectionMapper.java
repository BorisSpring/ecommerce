package main.mappers;

import main.domain.Section;
import main.model.SectionDTO;
import org.mapstruct.Mapper;

@Mapper
public interface SectionMapper {

    SectionDTO sectionToDto(Section section);
}
