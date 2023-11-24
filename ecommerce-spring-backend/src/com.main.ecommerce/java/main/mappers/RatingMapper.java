package main.mappers;

import main.domain.Rating;
import main.model.RatingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RatingMapper {


    @Mapping(source = "user.email", target="userEmail")
    RatingDTO ratingToDto(Rating rating);
}
