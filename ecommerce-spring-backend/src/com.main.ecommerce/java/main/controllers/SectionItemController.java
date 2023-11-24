package main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main.requests.SectionItemRequest;
import main.requests.UpdateSectionItemRequest;
import main.service.SectionItemService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sectionItems")
@RequiredArgsConstructor
@Validated
public class SectionItemController {

    private final SectionItemService sectionItemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSectionItem(@Valid @RequestBody SectionItemRequest sectionItemRequest){
        sectionItemService.createSectionItem(sectionItemRequest);
    }

    @DeleteMapping("/{sectionId}/{sectionItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSectionItem(@Positive(message = "Section id must be greather then zero!") @PathVariable("sectionId") Integer sectionId,
                                  @Positive(message = "Section item id must be greatjer tjem zerp!") @PathVariable("sectionItemId") Integer sectionItemId){
        sectionItemService.deleteSectionItem(sectionId, sectionItemId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateSectionItem(@Valid @RequestBody UpdateSectionItemRequest updateSectionItemRequest){
        sectionItemService.updateSectionItem(updateSectionItemRequest);
    }

}
