package main.service;

import main.domain.Section;
import main.model.SectionDTO;
import main.requests.SectionRequest;
import main.requests.UpdateSectionRequest;

import java.util.List;

public interface SectionService {

    void createSection (SectionRequest sectionRequest);

    void deleteSection(Integer categoryId, Integer sectionId);

    void updateSection(UpdateSectionRequest upadateSectionRequest);

    List<SectionDTO> findAllSectionsByCategoryId(Integer categoryId);

    public Section findById(Integer sectionId);
}
