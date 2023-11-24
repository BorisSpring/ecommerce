package main.mappers;

import lombok.RequiredArgsConstructor;
import main.domain.Cart;
import main.model.CartDTO;
import main.model.CartItemDTO;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public abstract class CartMapperDecorator implements  CartMapper{

    private final CartMapper cartMapper;
    private final  CartItemMapper cartItemMapper;

    @Override
    public CartDTO cartToDto(Cart cart) {
        CartDTO cartDTO = cartMapper.cartToDto(cart);
        cartDTO.setCartItemDTO(cart.getCartItems().stream().map(cartItemMapper::cartItemToDto).collect(Collectors.toSet()));
        return  cartDTO;
    }
}
