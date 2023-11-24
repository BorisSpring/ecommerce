package main.mappers;

import lombok.NoArgsConstructor;
import main.domain.Product;
import main.model.ProductDTO;
import main.model.ProductDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public abstract  class ProductMapperDecorator implements  ProductMapper{

    @Autowired
    private  ProductMapper productMapper;

    @Autowired
    private  ReviewMapper reviewMapper;

    @Autowired
    private  RatingMapper ratingMapper;



    @Override
    public ProductDTO productToDTO(Product product) {
        System.out.println("izvrsava se");
        return  productMapper.productToDTO(product);
    }

    @Override
    public ProductDetailsDTO productToProductDetailsDTO(Product product) {
        return  ProductDetailsDTO.builder()
                .product(productToDTO(product))
                .ratingsDto(product.getRaitings().stream().map(ratingMapper::ratingToDto).collect(Collectors.toList()))
                .reviewsDto(product.getReviews().stream().map(reviewMapper::reviewToDto).collect(Collectors.toList()))
                .build();
    }
}
