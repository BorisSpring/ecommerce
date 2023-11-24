package main.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {

    private String size;

    private int quantity;
    private double price;

    private double discountPrice;
    private double discount;

    private double totalPrice;

    private String productTitle;
}
