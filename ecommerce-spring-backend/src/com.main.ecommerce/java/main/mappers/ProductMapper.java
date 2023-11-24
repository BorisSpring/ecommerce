package main.mappers;

import main.domain.Product;
import main.model.ProductDTO;
import main.model.ProductDetailsDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    ProductDTO productToDTO(Product product);

    ProductDetailsDTO productToProductDetailsDTO(Product product);
}
