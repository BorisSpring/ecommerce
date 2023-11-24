package main.mappers;

import main.domain.Review;
import main.model.ReviewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReviewMapper {


    @Mapping(target = "userEmail", source = "user.email")
    @Mapping(target = "reviewContent", source = "review")
    @Mapping(target = "productTitle", source = "product.title")
    ReviewDTO reviewToDto(Review review);
}
