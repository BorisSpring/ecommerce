package main.model;

import lombok.Getter;
import lombok.Setter;
import main.domain.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ProductDTO {

    private static final long serialVersionUID= 8142574168906251486L;

    private List<String> colors = new ArrayList<>();
    private List<String> highlights = new ArrayList<>();
    private String name;
    private Set<Size> sizes= new HashSet<>();
    private double price;

    private double discountPrice;
    private Integer discountPrecent;
    private int quantity = 0;
    private int numOfRating = 0;
    private int numOfRewies = 0;
    private String title;

    private String description;

    private String brand;

    private String categoryName;
    private String sectionName;
    private String sectionItemName;
}
