package com.speedorder.orderapp.util;

import com.speedorder.orderapp.entity.Order;
import com.speedorder.orderapp.exception.custom.DateTimeFormatException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public abstract class OrderUtils {
    private static String FORMAT_PATTERN = "[MMM yyyy][d-MMM-yyyy]";
    private static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_PATTERN);

    private static LocalDate parseDate(String date) {
        LocalDate dateToParse = null;
        try {
            dateToParse = LocalDate.parse(date, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateTimeFormatException(String.format("Input date [ %s ] is not following the rules of date formatter ," +
                    " Use this date formats [ %s ] ", date, FORMAT_PATTERN));
        }
        return dateToParse;
    }

    public static Predicate<? super Order> filterCalendar(String startDate, String endDate) {

        return order -> {
            LocalDate localDateStart = parseDate(startDate);
            LocalDate localDateEnd = parseDate(endDate);
            if (localDateStart.isAfter(localDateEnd)) {
                throw new DateTimeException(String.format("End Date [ %s ] cant be before start Date [ %s ]", endDate, startDate));
            }
            LocalDate orderedDate = order.getOrderDate();
            return
                    orderedDate.isEqual(localDateStart) || orderedDate.isEqual(localDateEnd)
                            || (orderedDate.isAfter(localDateStart) && orderedDate.isBefore(localDateEnd));
        };

    }

    public static Predicate<? super Order> filterCalendar(String orderedDate) {
        return order -> {
            LocalDate targetDate = parseDate(orderedDate);
            return order.getOrderDate().isEqual(targetDate);
        };
    }

    public static Predicate<? super Order> filterCalendarOnSpecificMonthOfYear(String orderedDate) {
        return order -> {
            YearMonth targetYearMonth = YearMonth.parse(orderedDate, DATE_TIME_FORMATTER);
            return targetYearMonth.getYear() == order.getOrderDate().getYear() &&
                    targetYearMonth.getMonthValue() == order.getOrderDate().getMonthValue();
        };
    }
}
