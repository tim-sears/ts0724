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
    private String toolCode;
    private ToolType toolType;
    private String brand;
    private int rentalDurationDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public String toConsolePrintable() {
        return String.join("\n"
                ,"Tool code: " + toolCode
                ,"Tool type: " + toolType
                ,"Tool brand: " + brand
                ,"Rental days: " + rentalDurationDays
                ,"Check out date: " + checkoutDate
                ,"Due date: " + dueDate
                ,"Daily rental charge: $" + dailyRentalCharge.toPlainString()
                ,"Charge days: " + chargeDays
                ,"Pre-discount charge: $" + preDiscountCharge.toPlainString()
                ,"Discount percent; " + discountPercent + "%"
                ,"Discount amount: $" + discountAmount
                ,"Final charge: $" + finalCharge.toPlainString()
                );
    }
}
