package main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import main.model.SectionDTO;
import main.requests.SectionRequest;
import main.requests.UpdateSectionRequest;
import main.service.SectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sections")
@RequiredArgsConstructor
@Validated
public class SectionController {

    private final SectionService sectionService;
    @GetMapping("{categoryId}")
    public ResponseEntity<List<SectionDTO>> findAllSections(@NotNull(message = "Category id is required!")
                                                            @Positive(message = "Cateogry id must be greater then zero!")
                                                            @PathVariable("categoryId") Integer categoryId){
        return ResponseEntity.ok(sectionService.findAllSectionsByCategoryId(categoryId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createSection(@Valid @RequestBody SectionRequest sectionRequest){
        sectionService.createSection(sectionRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{categoryId}/{sectionId}")
    public void deleteSection(@Positive(message = "Category id must be greather then zero!") @PathVariable("categoryId") Integer categoryId,
                              @Positive(message = "Section id must be greather then zero!")  @PathVariable("sectionId") Integer sectionId){
        sectionService.deleteSection(categoryId,sectionId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updateSection(@Valid @RequestBody UpdateSectionRequest updateSectionRequest){
        sectionService.updateSection(updateSectionRequest);
    }
}
