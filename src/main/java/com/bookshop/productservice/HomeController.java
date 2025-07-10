package com.bookshop.productservice;

import com.bookshop.productservice.config.BookProductProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	private final BookProductProperties bookProductProperties;

	public HomeController(BookProductProperties bookProductProperties){
		this.bookProductProperties = bookProductProperties;
	}
	/*
	@GetMapping("/")
	public String getGreeting() {
		return "Welcome to the book catalog!";
	}*/
	@GetMapping("/")
	public String getGreeting() {
		return bookProductProperties.getGreeting();
	}
}
