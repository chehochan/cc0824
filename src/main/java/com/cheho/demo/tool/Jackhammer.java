package com.cheho.demo.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class Jackhammer extends Tool {

    @Value("${jackhammer.daily-charge}")
    private double dailyCharge;

    @Value("${jackhammer.weekday-charge}")
    private boolean isWeekDayCharge;

    @Value("${jackhammer.weekend-charge}")
    private boolean isWeekendCharge;

    @Value("${jackhammer.holiday-charge}")
    private boolean isholidayCharge;

    public Jackhammer(String brand, String toolCode) {
        super(brand, toolCode, "Jackhammer");
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        super.setDailyCharge(dailyCharge);
    }

    public boolean isWeekdayCharge() {
        return isWeekDayCharge;
    }

    public void setWeekdayCharge(boolean weekDayCharge) {
        super.setWeekendCharge(isWeekDayCharge);
    }

    public boolean isWeekendCharge() {
        return isWeekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        super.setWeekendCharge(isWeekendCharge);
    }

    public boolean isHolidayCharge() {
        return isholidayCharge;
    }

    public void setHolidayCharge(boolean isholidayCharge) {
        super.setHolidayCharge(isholidayCharge);
    }
}
