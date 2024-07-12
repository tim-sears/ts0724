package com.challenge.rental;

import com.challenge.rental.tools.Tool;
import com.challenge.rental.tools.ToolRepository;
import com.challenge.rental.tools.ToolType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class RentalAgreementGenerator {

    private final ToolRepository toolRepository;

    public RentalAgreementGenerator(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public RentalAgreement generate(String toolCode, LocalDate checkoutDate, int rentalDurationDays, int discountPercentage) {
        if(rentalDurationDays < 1) throw new IllegalArgumentException("Number of days for rental period must be >= 1");
        if(discountPercentage > 100) throw new IllegalArgumentException("Discount cannot exceed 100%");

        Tool tool = toolRepository.getToolByToolCode(toolCode);

        int chargeDays = rentalDurationDays; // TODO fix this to account for holidays!
        BigDecimal preDiscountCharge = tool.getToolType().getDailyCharge().multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP); // TODO fix this to account for holidays!
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercentage / 100.0)).setScale(2, RoundingMode.HALF_UP); // TODO fix this to account for holidays!
        BigDecimal finalAmount = preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP); // TODO fix this to account for holidays!
        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(tool.getToolType())
                .brand(tool.getBrand())
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(tool.getToolType().getDailyCharge())
                .chargeDays(chargeDays)
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(discountPercentage)
                .discountAmount(discountAmount)
                .finalCharge(finalAmount)
                .build();

        return expectedOutput;
    }
}
