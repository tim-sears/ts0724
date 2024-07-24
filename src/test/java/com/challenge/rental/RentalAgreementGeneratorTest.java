package com.challenge.rental;

import com.challenge.rental.exception.InvalidDiscountAmountException;
import com.challenge.rental.exception.InvalidRentalDurationException;
import com.challenge.rental.tools.ToolRepository;
import com.challenge.rental.tools.ToolType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalAgreementGeneratorTest {

    RentalAgreementGenerator rentalAgreementGenerator = new RentalAgreementGenerator(new ToolRepository());

    @Test
    public void test_0days_notAllowed() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDurationDays = 0;
        int discountPercentage = 50;

        InvalidRentalDurationException exception = assertThrows(
                InvalidRentalDurationException.class,
                () -> rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage),
                "Rental duration of 0 days makes no sense!");

        assertEquals("Number of days for rental period must be >= 1", exception.getMessage());
    }

    @Test
    public void JAKR_20150903_5days_101discount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDurationDays = 5;
        int discountPercentage = 101;

        InvalidDiscountAmountException exception = assertThrows(
                InvalidDiscountAmountException.class,
                () -> rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage),
                "Discount above 100% should not be allowed!");

        assertEquals("Discount cannot exceed 100%", exception.getMessage());
    }

    @Test
    public void LADW_20200702_3days_10discount() {
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
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.LADDER.getDailyCharge()))
                .chargeDays(2)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(3.98)))
                .discountPercent(10)
                .discountAmount(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(0.4).setScale(2, RoundingMode.HALF_UP)))
                .finalCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(3.58)))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }

    @Test
    public void CHNS_20150702_5days_25discount() {
        String toolCode = "CHNS";
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        int rentalDurationDays = 5;
        int discountPercentage = 25;

        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.CHAINSAW)
                .brand("Stihl")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.CHAINSAW.getDailyCharge()))
                .chargeDays(3)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(4.47)))
                .discountPercent(25)
                .discountAmount(DecimalFormat.getCurrencyInstance().format((BigDecimal.valueOf(1.12).setScale(2, RoundingMode.HALF_UP))))
                .finalCharge(DecimalFormat.getCurrencyInstance().format((BigDecimal.valueOf(3.35))))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }

    @Test
    public void JAKD_20150903_6days_0discount() {
        String toolCode = "JAKD";
        LocalDate checkoutDate = LocalDate.parse("2015-09-03");
        int rentalDurationDays = 6;
        int discountPercentage = 0;

        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .brand("DeWalt")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.JACKHAMMER.getDailyCharge()))
                .chargeDays(3)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(8.97)))
                .discountPercent(0)
                .discountAmount(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP)))
                .finalCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(8.97)))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }

    @Test
    public void JAKR_20150702_9days_0discount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2015-07-02");
        int rentalDurationDays = 9;
        int discountPercentage = 0;

        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .brand("Rigid")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.JACKHAMMER.getDailyCharge()))
                .chargeDays(5)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(14.95)))
                .discountPercent(0)
                .discountAmount(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP)))
                .finalCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(14.95)))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }

    @Test
    public void JAKR_20200702_4days_50discount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        int rentalDurationDays = 4;
        int discountPercentage = 50;

        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .brand("Rigid")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.JACKHAMMER.getDailyCharge()))
                .chargeDays(1)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(2.99)))
                .discountPercent(50)
                .discountAmount(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP)))
                .finalCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(1.49)))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }

    @Test
    public void JAKR_20200702_1000days_0discount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.parse("2020-07-02");
        int rentalDurationDays = 1000;
        int discountPercentage = 0;


        RentalAgreement expectedOutput = RentalAgreement.builder()
                .toolCode(toolCode)
                .toolType(ToolType.JACKHAMMER)
                .brand("Rigid")
                .rentalDurationDays(rentalDurationDays)
                .checkoutDate(checkoutDate)
                .dueDate(checkoutDate.plusDays(rentalDurationDays))
                .dailyRentalCharge(DecimalFormat.getCurrencyInstance().format(ToolType.JACKHAMMER.getDailyCharge()))
                .chargeDays(713)
                .preDiscountCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(2131.87)))
                .discountPercent(0)
                .discountAmount(DecimalFormat.getCurrencyInstance().format(0))
                .finalCharge(DecimalFormat.getCurrencyInstance().format(BigDecimal.valueOf(2131.87)))
                .build();

        RentalAgreement rentalAgreement = rentalAgreementGenerator.generate(toolCode, checkoutDate, rentalDurationDays, discountPercentage);

        assertEquals(expectedOutput, rentalAgreement);
    }
}