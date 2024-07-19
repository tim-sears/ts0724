package com.challenge.rental;

import com.challenge.rental.days.DaysTypeCounter;
import com.challenge.rental.tools.Tool;
import com.challenge.rental.tools.ToolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@Slf4j
public class RentalAgreementGenerator {

    private final ToolRepository toolRepository;

    public RentalAgreementGenerator(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public RentalAgreement generate(String toolCode, LocalDate checkoutDate, int rentalDurationDays, int discountPercentage) {
        if(rentalDurationDays < 1) throw new IllegalArgumentException("Number of days for rental period must be >= 1");
        if(discountPercentage > 100) throw new IllegalArgumentException("Discount cannot exceed 100%");

        log.info("Generating rental agreement for: toolCode={}, checkoutDate={}, rentalDurationDays={}, discountPercentage={}",
                toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        Tool tool = toolRepository.getToolByToolCode(toolCode);

        // currently the requirements don't allow for tools to not have a weekday charge,
        // but this seems like a logical extension given the data model supplied
        int noChargeWeekdaysCount = tool.getToolType().isHasWeekdayCharge()
                ? 0
                : DaysTypeCounter.countWeekdaysInDateRange(checkoutDate, checkoutDate.plusDays(rentalDurationDays));
        int noChargeWeekendDaysCount = tool.getToolType().isHasWeekendCharge()
                ? 0
                : DaysTypeCounter.countWeekendDaysInDateRange(checkoutDate, checkoutDate.plusDays(rentalDurationDays));
        int noChargeHolidaysCount = tool.getToolType().isHasHolidayCharge()
                ? 0
                : DaysTypeCounter.countHolidaysInDateRange(checkoutDate, checkoutDate.plusDays(rentalDurationDays));
        int chargeDays = rentalDurationDays - noChargeWeekdaysCount - noChargeWeekendDaysCount - noChargeHolidaysCount;

        log.info("Rental period for {} has {} no charge weekdays, {} no charge weekend days, adn {} no charge holidays",
                toolCode, noChargeWeekdaysCount, noChargeWeekendDaysCount, noChargeHolidaysCount);

        BigDecimal preDiscountCharge = tool.getToolType().getDailyCharge()
                .multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountAmount = preDiscountCharge
                .multiply(BigDecimal.valueOf(discountPercentage / 100.0)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalAmount = preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);

        RentalAgreement rentalAgreement = RentalAgreement.builder()
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

        log.info("Resulting rentalAgreement: \n" + rentalAgreement.toConsolePrintable());
        return rentalAgreement;
    }
}
