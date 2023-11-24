package main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import main.domain.Section;
import main.domain.SectionItems;
import main.exception.ResourceNotFoundException;
import main.exception.SectionException;
import main.repository.SectionItemRepository;
import main.requests.SectionItemRequest;
import main.requests.UpdateSectionItemRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SectionItemServiceImpl implements SectionItemService {

    private final SectionService sectionService;
    private final SectionItemRepository sectionItemRepository;

    @Transactional
    @Override
    public void createSectionItem(SectionItemRequest sectionItemRequest) {
        Section section = sectionService.findById(sectionItemRequest.getSectionId());

        sectionItemRepository.save(SectionItems.builder()
                        .itemName(sectionItemRequest.getSectionItemName())
                        .section(section)
                        .build());
    }

    @Transactional
    @Override
    public void deleteSectionItem(Integer sectionId, Integer sectionItemId) {
        if(sectionItemRepository.existsByIdAndSectionId(sectionItemId, sectionId))
            throw new ResourceNotFoundException("Section item with id " + sectionItemId + " doesnt exists!");

        sectionItemRepository.deleteById(sectionItemId);
    }

    @Transactional
    @Override
    public void updateSectionItem(UpdateSectionItemRequest updateSectionItemRequest) {

        if(sectionItemRepository.existsBySectionSectionNameAndSectionId(updateSectionItemRequest.getSectionItemName(), updateSectionItemRequest.getSectionId()))
            throw new SectionException("There is alerdy section item with same name!");

        SectionItems sectionItem = sectionItemRepository.findById(updateSectionItemRequest.getSectionItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Section item with id " + updateSectionItemRequest.getSectionItemId() + " doesnt exists"));

        sectionItem.setItemName(updateSectionItemRequest.getSectionItemName());
        sectionItemRepository.save(sectionItem);
    }

    @Override
    public SectionItems findById(Integer sectionItemId) {
        return sectionItemRepository.findById(sectionItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Section item wit id " + sectionItemId + " doesnt exists!"));
    }
}
