package com.challenge.rental;

import com.challenge.rental.tools.ToolRepository;
import com.challenge.rental.tools.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgreementGeneratorTest {

    RentalAgreementGenerator rentalAgreementGenerator = new RentalAgreementGenerator(new ToolRepository());

    @Test
    public void test_0days_notAllowed() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDurationDays = 0;
        int discountPercentage = 50;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage),
                "Rental duration of 0 days makes no sense!");

        assertEquals("Number of days for rental period must be >= 1", exception.getMessage());
    }

    @Test
    public void test_JAKR_20150903_5days_101discount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDurationDays = 5;
        int discountPercentage = 101;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage),
                "Discount above 100% should not be allowed!");

        assertEquals("Discount cannot exceed 100%", exception.getMessage());
    }

    @Test
    public void LADW_20200702_3days_10discount() {
        String expectedOutputString = """
                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Rental days: 3
                Check out date: 2020/07/02
                Due date: 2020/07/05
                Daily rental charge: $1.99
                Charge days: 3
                Pre-discount charge: $5.97
                Discount percent; 10%
                Discount amount: $0.60
                Final charge: $5.37""";

        String toolCode = "LADW";
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        int rentalDurationDays = 3;
        int discountPercentage = 10;

        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.LADDER)
                .brand("Werner")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(3))
                .dailyRentalCharge(ToolType.LADDER.getDailyCharge())
                .chargeDays(rentalDurationDays) // TODO fix this to account for holidays!
                .preDiscountCharge(BigDecimal.valueOf(5.97))
                .discountPercent(10)
                .discountAmount(BigDecimal.valueOf(0.6).setScale(2, RoundingMode.HALF_UP)) // TODO fix this to account for holidays!
                .finalCharge(BigDecimal.valueOf(5.37)) // TODO fix this to account for holidays!
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }
}