package com.challenge.rental.holidays;

import com.challenge.rental.days.DaysTypeCounter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaysTypeCounterTest {

    @Nested
    class Holidays {
        @Test
        public void test_noHolidaysInRange_reports0() {
            LocalDate beginDate = LocalDate.parse("2019-05-02");
            LocalDate endDate = LocalDate.parse("2019-05-07");

            int result = DaysTypeCounter.countHolidaysInDateRange(beginDate, endDate);

            assertEquals(0, result);
        }

        @Test
        public void test_July4th_weekday_reports1() {
            LocalDate beginDate = LocalDate.parse("2019-07-02");
            LocalDate endDate = LocalDate.parse("2019-07-05");

            int result = DaysTypeCounter.countHolidaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }

        @Test
        public void test_July4th_saturday_rangeIncludesFriday_reports1() {
            LocalDate beginDate = LocalDate.parse("2020-07-02");
            LocalDate endDate = LocalDate.parse("2020-07-03");

            int result = DaysTypeCounter.countHolidaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }

        @Test
        public void test_July4th_sunday_rangeIncludesMonday_reports1() {
            LocalDate beginDate = LocalDate.parse("2027-07-05");
            LocalDate endDate = LocalDate.parse("2027-07-08");

            int result = DaysTypeCounter.countHolidaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }

        @Test
        public void test_laborDay_reports1() {
            LocalDate beginDate = LocalDate.parse("2015-09-03");
            LocalDate endDate = LocalDate.parse("2015-09-08");

            int result = DaysTypeCounter.countHolidaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }
    }

    @Nested
    class Weekend {
        @Test
        public void test_fullWeekend_reports2() {
            LocalDate beginDate = LocalDate.parse("2024-07-12");
            LocalDate endDate = LocalDate.parse("2024-07-15");

            int result = DaysTypeCounter.countWeekendDaysInDateRange(beginDate, endDate);

            assertEquals(2, result);
        }

        @Test
        public void test_onlySaturday_reports1() {
            LocalDate beginDate = LocalDate.parse("2024-07-11");
            LocalDate endDate = LocalDate.parse("2024-07-13");

            int result = DaysTypeCounter.countWeekendDaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }

        @Test
        public void test_onlySunday_reports1() {
            LocalDate beginDate = LocalDate.parse("2024-07-14");
            LocalDate endDate = LocalDate.parse("2024-07-15");

            int result = DaysTypeCounter.countWeekendDaysInDateRange(beginDate, endDate);

            assertEquals(1, result);
        }
    }
}