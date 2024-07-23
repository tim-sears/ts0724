package com.challenge.rental;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ToolRentalController {

	private RentalAgreementGenerator rentalAgreementGenerator;

    public ToolRentalController(RentalAgreementGenerator rentalAgreementGenerator) {
        this.rentalAgreementGenerator = rentalAgreementGenerator;
    }

    @PostMapping("/rental-agreements")
	public RentalAgreement createRentalAgreement(@RequestBody RentalAgreementRequest rentalAgreementRequest) throws JsonProcessingException {
		log.info("POST rental-agreements-- request: {}", rentalAgreementRequest);
		return rentalAgreementGenerator.generate(rentalAgreementRequest);
	}

}
