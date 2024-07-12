package com.challenge.rental;

import com.challenge.rental.tools.ToolType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@EqualsAndHashCode
public class RentalAgreement {
    private String toolCode; // - Specified at checkout
    private ToolType toolType; // - From tool info
    private String brand; // - From tool info
    private int rentalDurationDays; // - Specified at checkout
    private LocalDate checkoutDate; // - Specified at checkout
    private LocalDate dueDate; // - Calculated from checkout date and rental days.
    private BigDecimal dailyRentalCharge;// - Amount per day, specified by the tool type.
    private int chargeDays; // - Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type.
    private BigDecimal preDiscountCharge; // - Calculated as charge days X daily charge. Resulting total rounded half up to cents.
    private int discountPercent; // - Specified at checkout.
    private BigDecimal discountAmount; // - calculated from discount % and pre-discount charge. Resulting amount rounded half up to cents.
    private BigDecimal finalCharge; // - Calculated as pre-discount charge - discount amount.
}
