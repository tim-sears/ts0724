package com.challenge.rental.exception;

import com.challenge.rental.RentalAgreementGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = RentalAgreementGenerator.NUMBER_OF_DAYS_FOR_RENTAL_PERIOD_MUST_BE_1)
public class InvalidDiscountAmountException extends IllegalArgumentException {
    public InvalidDiscountAmountException(String message) {
        super(message);
    }
}
