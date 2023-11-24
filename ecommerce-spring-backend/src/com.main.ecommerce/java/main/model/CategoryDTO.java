package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import main.domain.Section;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryDTO {

    private List<Section> sections;

    private String categoryName;
}
