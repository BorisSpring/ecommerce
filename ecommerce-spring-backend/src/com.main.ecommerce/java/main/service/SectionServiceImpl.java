package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Category;
import main.domain.Section;
import main.exception.ResourceNotFoundException;
import main.exception.SectionException;
import main.mappers.SectionMapper;
import main.model.SectionDTO;
import main.repository.SectionRepository;
import main.requests.SectionRequest;
import main.requests.UpdateSectionRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    private final CategoryService categoryService;

    private final SectionMapper sectionMapper;
    @Transactional
    @Override
    public void createSection(SectionRequest sectionRequest) {
        if(sectionRepository.existsByCategoryIdAndSectionName(sectionRequest.getCategoryId(),sectionRequest.getSectionName()))
            throw new SectionException("There is alerdy section with the same name!");

        Category category = categoryService.findById(sectionRequest.getCategoryId());

        sectionRepository.save(Section.builder()
                        .sectionName(sectionRequest.getSectionName())
                        .category(category)
                        .build());
    }

    @Transactional
    @Override
    public void deleteSection(Integer categoryId, Integer sectionId) {
            if(!sectionRepository.existsByIdAndCategoryId(sectionId,categoryId))
                throw new ResourceNotFoundException("Section doesnt exists!");

        sectionRepository.deleteById(sectionId);
    }

    @Transactional
    @Override
    public void updateSection(UpdateSectionRequest upadateSectionRequest) {
        if(!sectionRepository.existsByIdAndCategoryId(upadateSectionRequest.getSectionId(), upadateSectionRequest.getCategoryId()))
            throw new ResourceNotFoundException("Section doesnt exists!");

        Section section = sectionRepository.findById(upadateSectionRequest.getSectionId()).get();
        section.setSectionName(upadateSectionRequest.getSectionName());
        sectionRepository.save(section);
    }

    @Override
    public List<SectionDTO> findAllSectionsByCategoryId(Integer categoryId) {
        return  sectionRepository.findByCategoryId(categoryId)
                .stream()
                .map(sectionMapper::sectionToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Section findById(Integer sectionId) {
        return  sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Section with id " + sectionId + " not found!"));
    }
}
