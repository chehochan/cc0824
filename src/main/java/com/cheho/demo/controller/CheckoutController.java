package com.cheho.demo.controller;

import com.cheho.demo.tool.Chainsaw;
import com.cheho.demo.tool.Jackhammer;
import com.cheho.demo.tool.Ladder;
import com.cheho.demo.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.InputMismatchException;

@Scope("prototype")
@Component
public class CheckoutController {

    @Autowired
    BeanFactory beanFactory;
    private final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private LocalDate startDate;

    private int rentalLength;

    private String toolCode;

    private int discount;

    public CheckoutController(LocalDate startDate, int rentalLength, String toolCode, int discount) {
        this.startDate = startDate;
        this.rentalLength = rentalLength;
        this.toolCode = toolCode;
        this.discount = discount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getRentalLength() {
        return rentalLength;
    }

    public void setRentalLength(int rentalLength) {
        this.rentalLength = rentalLength;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * The function calculates the number of weekend days
     * @param startDate    The date of the rental checkout
     * @param rentalDays The number of rental days
     * @return The total number of days on weekend
     */
    public int getWeekendDays(LocalDate startDate, int rentalDays) {
        int WeekendDayCount = 0;
        LocalDate date = startDate;
        for (int days = 0; days < rentalDays; days++) {
            if (isWeekend(date)) {
                WeekendDayCount++;
            }
            date = date.plusDays(1);
        }
        return WeekendDayCount;
    }

    /**
     * The function calculates the number of holidays
     * @param startDate    The date of the rental checkout
     * @param rentalDays The number of rental days
     * @return The total number of holidays
     */
    public int checkHoliday(LocalDate startDate, int rentalDays) {
        int holidayCount = 0;
        LocalDate date = startDate;
        for (int days = 0; days <= rentalDays; days++) {
            if (date.getMonth() == Month.JULY) {
                if (checkJuly4thDiscount(date)) {
                    if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        if (startDate.isBefore(date.minusDays(1))) {
                            holidayCount++;
                        }
                    } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        if (startDate.plusDays(rentalDays).isAfter(date)) {
                            holidayCount++;
                        }
                    } else {
                        holidayCount++;
                    }
                }
            }
            if (date.getMonth() == Month.SEPTEMBER) {
                LocalDate laborDay = findLaborDay(date.getYear());
                if (checkLaborDayDiscount(date, laborDay)) {
                    holidayCount++;
                }
            }
            date = date.plusDays(1);
        }

        return holidayCount;
    }

    /**
     * The function checks if the rental date is on Labor Day.
     *
     * @param date     The date of the rental
     * @param laborDay The date of the Labor Day
     * @return A boolean value of true if the rental date is on Labor Day.
     */
    public boolean checkLaborDayDiscount(LocalDate date, LocalDate laborDay) {
        return date.isEqual(laborDay);
    }

    /**
     * The function looks for the first Monday of September with the input year.
     *
     * @param year the year of the rental date(s)
     * @return The date of the first Monday of September of the year which is the Labor Day of the year.
     */
    public LocalDate findLaborDay(Integer year) {
        LocalDate laborDay = LocalDate.of(year, 9, 1);
        while (laborDay.getMonth() == Month.SEPTEMBER && laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            laborDay = laborDay.plusDays(1);
        }
        return laborDay;
    }

    /**
     * The function checks if the date is on a Saturday or Sunday
     *
     * @param ld date to check
     * @return boolean value if it falls on a Saturday or Sunday
     */
    public boolean isWeekend(final LocalDate ld) {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }

    /**
     * The function validates if the discount percentage is out of range
     * @param percent   The percentage number from input
     * @return a boolean value if the discount is range
     */
    private boolean isDiscountValid(int percent) {
        return percent >= 0 && percent <= 100;
    }

    /**
     * The function checks if the date is July 4th
     * @param date    The date that needs to be checked
     * @return a boolean if it is July 4th
     */
    private boolean checkJuly4thDiscount(LocalDate date) {
        boolean isJuly4th = false;
        if (date.getDayOfMonth() == 4) {
            isJuly4th = true;
        }
        return isJuly4th;
    }

    /**
     * The function calculates the holidays/weekends, total charge and discount
     * @return An InvoicePrinter Object
     */
    public InvoicePrinter checkout() throws Exception {
        Tool tool = null;

        if (rentalLength < 1) {
            logger.error("The number of rental days need to be greater than 0.");
            throw new InputMismatchException("The number of rental days need to be greater than 0.");
        }

        if (!isDiscountValid(getDiscount())) {
            logger.error("The discount entered is invalid.  Please enter value from 0-100 in whole number.");
            throw new InputMismatchException("The discount entered is invalid.  Please enter value from 0-100 in whole number.");
        }

        /*
        * Depending on the tool code, it creates a new bean of the specific tool.
        * The brand is passed in as an argument to construct the object/bean.
        * */
        switch (toolCode) {
            case "CHNS":
                tool = beanFactory.getBean(Chainsaw.class, "Stihl", toolCode);
                break;
            case "LADW":
                tool = beanFactory.getBean(Ladder.class, "Werner", toolCode);
                break;
            case "JAKD":
                tool = beanFactory.getBean(Jackhammer.class, "DeWalt", toolCode);
                break;
            case "JAKR":
                tool = beanFactory.getBean(Jackhammer.class, "Ridgid", toolCode);
                break;
            default:
                logger.error("The system does not recognize the tool code: {}", toolCode);
                throw new Exception("The system does not recognize the tool code: {}, toolCode");
        }

        if (tool != null) {
            int noChargeDayCount = 0;
            int weekendDayCount = getWeekendDays(getStartDate(), getRentalLength());
            int holidayCounts = checkHoliday(getStartDate(), getRentalLength());
            if (!tool.isWeekdayCharge()) {
                noChargeDayCount = rentalLength - (weekendDayCount + holidayCounts);
            }
            if (!tool.isWeekendCharge()) {
                noChargeDayCount = noChargeDayCount + weekendDayCount;
            }
            if (!tool.isHolidayCharge()) {
                noChargeDayCount = noChargeDayCount + holidayCounts;
            }

            int totalDayCount = rentalLength - noChargeDayCount;
            double beforeDiscount = totalDayCount * tool.getDailyCharge();
            double discountAmount = beforeDiscount * ((double) getDiscount() / 100);
            double finalCost = beforeDiscount - discountAmount;
            InvoicePrinter invoicePrinter = beanFactory.getBean(InvoicePrinter.class,
                    tool.getToolCode(),
                    tool.getToolType(),
                    tool.getBrand(),
                    getRentalLength(),
                    getStartDate(),
                    getStartDate().plusDays(getRentalLength()),
                    tool.getDailyCharge(),
                    totalDayCount,
                    beforeDiscount,
                    getDiscount(),
                    discountAmount,
                    finalCost
            );
            invoicePrinter.printAgreement();
            return invoicePrinter;
        }
        return null;
    }

}
