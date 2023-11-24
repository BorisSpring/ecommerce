package main.model;

import main.domain.Product;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ProductPageList extends PageImpl<Product> {

    public ProductPageList(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public ProductPageList(List content) {
        super(content);
    }
}
