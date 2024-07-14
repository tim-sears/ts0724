package com.challenge.rental.days;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;

@Service
public class DaysTypeCounter {
    public static int countHolidaysInDateRange(LocalDate beginDate, LocalDate endDate) {
        LocalDate july4th = LocalDate.parse(beginDate.getYear() + "-07-04");
        LocalDate observedJuly4th = july4th;
        if(DayOfWeek.SATURDAY.equals(july4th.getDayOfWeek())) {
            observedJuly4th = july4th.minusDays(1);
        } else if(DayOfWeek.SUNDAY.equals(july4th.getDayOfWeek())) {
            observedJuly4th = july4th.plusDays(1);
        }

        if(observedJuly4th.equals(beginDate)
                || observedJuly4th.equals(endDate)
                || (observedJuly4th.isAfter(beginDate) && observedJuly4th.isBefore(endDate))) {
            return 1;
        }

        LocalDate laborDay = LocalDate.of(beginDate.getYear(), Month.SEPTEMBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        if(laborDay.equals(beginDate)
                || laborDay.equals(endDate)
                || (laborDay.isAfter(beginDate) && laborDay.isBefore(endDate))) {
            return 1;
        }

        return 0;
    }

    public static int countWeekendDaysInDateRange(LocalDate beginDate, LocalDate endDate) {
        List<DayOfWeek> weekendDays = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return (int) beginDate.datesUntil(endDate.plusDays(1))
                .map(LocalDate::getDayOfWeek)
                .filter(weekendDays::contains)
                .count();
    }

    public static int countWeekdaysInDateRange(LocalDate beginDate, LocalDate endDate) {
        List<DayOfWeek> weekendDays = Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        return (int) beginDate.datesUntil(endDate.plusDays(1))
                .map(LocalDate::getDayOfWeek)
                .filter(day -> !weekendDays.contains(day))
                .count();
    }
}
