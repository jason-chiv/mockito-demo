package com.mockito.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mockito.domain.Product;
import com.mockito.repositories.ProductRepository;


/**
 * Created by Vetty on 2/19/18.
 */
public class ProductServiceTest {

	@Mock
	ProductRepository productRepository;
	ProductService productService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		productService = new ProductService();
		productService.setProductRepository(productRepository);
	}

	@Test
	public void getProductByID() throws Exception {
		Product product = new Product();
		product.setId(1L);
		Optional<Product> productOptional = Optional.of(product);
		when(productRepository.findById(anyLong())).thenReturn(productOptional);
		Product productReturned = productService.getProductByID(1L);
		assertNotNull("null product returned", productReturned);
		verify(productRepository, times(1)).findById(anyLong());
		// verify(productRepository, never().findAll());

	}

}