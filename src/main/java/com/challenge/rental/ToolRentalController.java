package com.challenge.rental;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToolRentalController {

	@GetMapping("/")
	public String index() {
		return "Welcome to CF Tool Rental!";
	}

}
