package com.challenge.rental;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RentalAgreementRequest {
    String toolCode;
    LocalDate checkoutDate;
    int rentalDurationDays;
    int discountPercentage;
}
