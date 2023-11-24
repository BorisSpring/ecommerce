package main.mappers;

import main.domain.CartItem;
import main.model.CartItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CartItemMapper {

    @Mapping(source = "product.title", target = "productTitle")
    CartItemDTO cartItemToDto(CartItem cartItem);
}
