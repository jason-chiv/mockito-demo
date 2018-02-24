package com.mockito.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mockito.commands.ProductForm;
import com.mockito.converters.ProductToProductForm;
import com.mockito.services.ProductService;

@Controller
public class KhmerController {
	private ProductService productService;
	private ProductToProductForm productToProductForm;

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	@Autowired
	public void setProductToProductForm(ProductToProductForm productToProductForm) {
		this.productToProductForm = productToProductForm;
	}

	@RequestMapping("/kh")
	public String redirToList() {
		return "redirect:kh/product/list";
	}

	@RequestMapping({ "kh/product/list", "kh/product" })
	public String listProducts(Model model) {
		model.addAttribute("products", productService.listAll());
		return "product/list_kh";
	}

	@RequestMapping("kh/product/new")
	public String newProduct(Model model) {
		model.addAttribute("productForm", new ProductForm());
		return "product/productform";
	}
}
