package com.challenge.rental.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum ToolType {
    LADDER("Ladder", BigDecimal.valueOf(1.99), true, true, false),
    CHAINSAW("Chainsaw", BigDecimal.valueOf(1.49), true, false, true),
    JACKHAMMER("Jackhammer", BigDecimal.valueOf(2.99), true, false, false);

    private final String typeName;
    private final BigDecimal dailyCharge;
    private final boolean hasWeekdayCharge;
    private final boolean hasWeekendCharge;
    private final boolean hasHolidayCharge;
}
