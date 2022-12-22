package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.product.ProductRequestDto;
import com.example.autoservice.dto.product.ProductResponseDto;
import com.example.autoservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toDto(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());

        return dto;
    }

    public Product toModel(ProductRequestDto requestDto) {
        Product product = new Product();
        product.setTitle(requestDto.getTitle());
        product.setPrice(requestDto.getPrice());

        return product;
    }
}
