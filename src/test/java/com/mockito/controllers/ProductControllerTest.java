package com.mockito.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import com.mockito.converters.ProductToProductForm;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mockito.commands.ProductForm;
import com.mockito.domain.Product;
import com.mockito.services.ProductService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vetty on 2/19/18.
 */
public class ProductControllerTest {


	@Mock
	ProductService productService;
	@Mock
    ProductToProductForm productToProductForm;

	ProductController productController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		productController = new ProductController();
		productController.setProductService(productService);
		productController.setProductToProductForm(productToProductForm);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void testRedirToList() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(view().name("redirect:/product/list"));
	}

	@Test
	public void testListProduct() throws Exception{
		List<Product> products = new ArrayList<>();
		Product product = new Product();
		product.setId(1L);
		Product product1 = new Product();
		product1.setId(2L);
		products.add(product);
		products.add(product1);
		when(productService.listAll()).thenReturn(products);
		mockMvc.perform(get("/product/list"))
				.andExpect(view().name("product/list"))
				.andExpect(model().attributeExists("products"))
				.andExpect(model().attribute("products",products));
		mockMvc.perform(get("/product"))
				.andExpect(view().name("product/list"));


	}
	@Test
	public void testGetProduct() throws Exception {
		Product product = new Product();
		product.setId(1L);

		when(productService.getProductByID(anyLong())).thenReturn(product);
		mockMvc.perform(get("/product/show/1"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("product"))
		.andExpect(model().attribute("product",product))
		.andExpect(view().name("product/show"));

	}


    @Test
    public void testEdit() throws Exception{
	    Product product = new Product();
	    ProductForm productForm = new ProductForm();
	    productForm.setId(1L);
	    product.setId(1L);
	    when(productService.getProductByID(anyLong())).thenReturn(product);
	    when(productToProductForm.convert(product)).thenReturn(productForm);
	    mockMvc.perform(get("/product/edit/1"))
                .andExpect(view().name("product/productform"))
                .andExpect(model().attributeExists("productForm"));

    }

    @Test
    public void testNewProduct() throws Exception{
	    mockMvc.perform(get("/product/new"))
                .andExpect(view().name("product/productform"))
                .andExpect(model().attributeExists("productForm"));
    }

	@Test
	public void testPostNewProductForm() throws Exception {
		Product product = new Product();
		product.setId(2L);
		when(productService.saveOrUpdateProductForm(any(ProductForm.class))).thenReturn(product);
		mockMvc.perform(post("/product")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some description")
				.param("price", "20")
				.param("imageUrl", "http://www.abc.com"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/product/show/2"));
	}

    // Testing Void Method using doNothing(), Verify() and ArgumentCaptor<T>;
	@Test
    public void testDeleteProduct() throws  Exception{
	    Product product = new Product();
	    product.setId(1L);
        ArgumentCaptor<Long> valueCapture = ArgumentCaptor.forClass(Long.class);
	    doNothing().when(productService).deleteProduct(anyLong());
	    mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));
	    verify(productService,times(1)).deleteProduct(anyLong());
        verify(productService).deleteProduct(valueCapture.capture());
        assertEquals(1L,valueCapture.getValue().longValue());

    }
}