package main.requests;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private List<String> color = new ArrayList<>();

    @Positive(message = "page Number must be greatjer then zero!")
    private Integer page = 1;

    @Positive(message = "Page size must be greather then zero!")
    private Integer pageSize = 12;
    private List<String> size = new ArrayList<>();

    private List<String> price = new ArrayList<>() ;
    private List<Integer> discountRange = new ArrayList<>();
    private String sort;
    private String stock;

    private String category;

}
