package com.mockito.controllers;

import com.mockito.commands.ProductForm;
import com.mockito.converters.ProductToProductForm;
import com.mockito.domain.Product;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mockito.services.ProductService;

import javax.validation.Valid;

/**
 * Created by Vetty on 2/19/18.
 */
@Controller
@Slf4j
public class ProductController {

	private ProductService productService;
	private ProductToProductForm productToProductForm;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	@Autowired
	public void setProductToProductForm(ProductToProductForm productToProductForm) {
		this.productToProductForm=productToProductForm;
	}

	@RequestMapping("/")
	public String redirToList() {
		return "redirect:/product/list";
	}

	@RequestMapping({ "/product/list", "/product" })
	public String listProducts(Model model) {
		model.addAttribute("products", productService.listAll());
		return "product/list";
	}

	@RequestMapping("/product/show/{id}")
	public String getProduct(@PathVariable String id, Model model) {
		model.addAttribute("product", productService.getProductByID(Long.valueOf(id)));
		return "product/show";
	}


	@RequestMapping("/product/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		Product product = productService.getProductByID(Long.valueOf(id));
		ProductForm productForm = productToProductForm.convert(product);

		model.addAttribute("productForm", productForm);
		return "product/productform";
	}

	@RequestMapping("/product/new")
	public String newProduct(Model model) {
		model.addAttribute("productForm", new ProductForm());
		return "product/productform";
	}

	@PostMapping("/product")
	public String postNewProductForm(@Valid @ModelAttribute("productForm") ProductForm productForm,
									  BindingResult bindingResult) {
		log.info("Inside error...");
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(ObjectError -> {
				log.debug(ObjectError.toString());
			});
			return "product/productform";
		}

		Product savedProduct = productService.saveOrUpdateProductForm(productForm);

		return "redirect:/product/show/" + savedProduct.getId();
	}


	@RequestMapping(value = "/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") String id){
		productService.deleteProduct(Long.valueOf(id));
		return "redirect:/product/list";
	}

}
