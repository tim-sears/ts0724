package com.challenge.rental;

import com.challenge.rental.eception.InvalidDiscountAmountException;
import com.challenge.rental.eception.InvalidRentalDurationException;
import com.challenge.rental.tools.ToolType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ToolRentalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RentalAgreementGenerator rentalAgreementGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test_0dayRental() throws Exception {
        RentalAgreementRequest rentalAgreementRequest = RentalAgreementRequest.builder()
                .toolCode("JAKR")
                .checkoutDate(LocalDate.parse("2024-07-18"))
                .rentalDurationDays(0)
                .discountPercentage(0)
                .build();
        when(rentalAgreementGenerator.generate(rentalAgreementRequest))
                .thenThrow(new InvalidRentalDurationException(RentalAgreementGenerator.NUMBER_OF_DAYS_FOR_RENTAL_PERIOD_MUST_BE_1));

        this.mockMvc.perform(post("/rental-agreements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalAgreementRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(InvalidRentalDurationException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(RentalAgreementGenerator.NUMBER_OF_DAYS_FOR_RENTAL_PERIOD_MUST_BE_1,
                        result.getResolvedException().getMessage()));
    }

    @Test
    public void test_excessiveDiscountPercentage() throws Exception {
        RentalAgreementRequest rentalAgreementRequest = RentalAgreementRequest.builder()
                .toolCode("JAKR")
                .checkoutDate(LocalDate.parse("2024-07-18"))
                .rentalDurationDays(5)
                .discountPercentage(101)
                .build();
        when(rentalAgreementGenerator.generate(rentalAgreementRequest))
                .thenThrow(new InvalidDiscountAmountException(RentalAgreementGenerator.DISCOUNT_CANNOT_EXCEED_100));

        this.mockMvc.perform(post("/rental-agreements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalAgreementRequest)))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertInstanceOf(InvalidDiscountAmountException.class, result.getResolvedException()))
                .andExpect(result -> assertEquals(RentalAgreementGenerator.DISCOUNT_CANNOT_EXCEED_100,
                        result.getResolvedException().getMessage()));
    }

    @Test
    public void test_validRequest_200() throws Exception {
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
                .dailyRentalCharge(ToolType.LADDER.getDailyCharge())
                .chargeDays(2)
                .preDiscountCharge(BigDecimal.valueOf(3.98))
                .discountPercent(10)
                .discountAmount(BigDecimal.valueOf(0.4).setScale(2, RoundingMode.HALF_UP))
                .finalCharge(BigDecimal.valueOf(3.58))
                .build();

        RentalAgreementRequest rentalAgreementRequest = RentalAgreementRequest.builder()
                .toolCode(toolCode)
                .checkoutDate(checkoutDate)
                .rentalDurationDays(rentalDurationDays)
                .discountPercentage(discountPercentage)
                .build();
        when(rentalAgreementGenerator.generate(rentalAgreementRequest))
                .thenReturn(expectedOutput);

        this.mockMvc.perform(post("/rental-agreements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rentalAgreementRequest)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(objectMapper.writeValueAsString(expectedOutput)));
    }
}