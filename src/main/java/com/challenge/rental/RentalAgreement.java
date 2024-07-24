package com.challenge.rental;

import com.challenge.rental.tools.ToolType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String dailyRentalCharge;
    private int chargeDays;
    private String preDiscountCharge;
    private int discountPercent;
    private String discountAmount;
    private String finalCharge;

    public String toConsolePrintable() {
        return String.join("\n"
                ,"Tool code: " + toolCode
                ,"Tool type: " + toolType
                ,"Tool brand: " + brand
                ,"Rental days: " + rentalDurationDays
                ,"Check out date: " + checkoutDate
                ,"Due date: " + dueDate
                ,"Daily rental charge: " + dailyRentalCharge
                ,"Charge days: " + chargeDays
                ,"Pre-discount charge: " + preDiscountCharge
                ,"Discount percent; " + discountPercent + "%"
                ,"Discount amount: " + discountAmount
                ,"Final charge: " + finalCharge
                );
    }
}
