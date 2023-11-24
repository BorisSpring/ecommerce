package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.domain.SectionItems;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class SectionDTO {

    private List<SectionItems> items = new ArrayList<>();

    private String sectionName;
}
