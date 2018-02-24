package com.mockito.services;

import com.mockito.commands.ProductForm;
import com.mockito.converters.ProductFormToProduct;
import com.mockito.domain.Product;
import com.mockito.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vetty on 2/19/18.
 */
@Slf4j
@Service
public class ProductService {

    private ProductRepository productRepository;
    private ProductFormToProduct productFormToProduct;
    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct){
        this.productFormToProduct = productFormToProduct;
    }


    public Product getProductByID(Long id){
    	Optional<Product> productOptional = productRepository.findById(id);
    	if(productOptional.isPresent()){
            log.info(productOptional.get().toString());
            return productOptional.get();
        }else throw new RuntimeException("Product not found");
    }

    public List<Product> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); // fun with Java 8
        return products;
    }

    public Product saveOrUpdate(Product product){
        return productRepository.save(product);
    }

    public  Product saveOrUpdateProductForm(ProductForm productForm){
        Product savedProduct = saveOrUpdate(productFormToProduct.convert(productForm));
        return savedProduct;
    }

    public void deleteProduct(Long id){
         productRepository.delete(id);
    }
}
