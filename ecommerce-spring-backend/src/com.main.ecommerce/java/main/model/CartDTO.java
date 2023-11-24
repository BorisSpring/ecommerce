package main.model;

import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CartDTO {

    private Set<CartItemDTO> cartItemDTO = new HashSet<>();
    private double totalPrice = 0;

    private int  totalQuantity = 0;

    private double totalDiscountPrice = 0;
    private double discount = 0;
}
