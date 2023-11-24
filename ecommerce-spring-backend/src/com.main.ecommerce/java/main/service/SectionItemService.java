package main.service;

import main.domain.SectionItems;
import main.requests.SectionItemRequest;
import main.requests.UpdateSectionItemRequest;

public interface SectionItemService {

    void createSectionItem(SectionItemRequest sectionItemRequest);

    void deleteSectionItem(Integer sectionId, Integer sectionItemId);

    void updateSectionItem(UpdateSectionItemRequest updateSectionItemRequest);

    SectionItems findById(Integer sectionItemId);
}
