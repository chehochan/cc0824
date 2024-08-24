package com.cheho.demo.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Scope("prototype")
@Component
public class Chainsaw extends Tool {

    @Value("${chainsaw.daily-charge}")
    private double dailyCharge;

    @Value("${chainsaw.weekday-charge}")
    private boolean isWeekDayCharge;

    @Value("${chainsaw.weekend-charge}")
    private boolean isWeekEndCharge;

    @Value("${chainsaw.holiday-charge}")
    private boolean isHolidayCharge;

    public Chainsaw(String brand, String toolCode) {
        super(brand, toolCode, "Chainsaw");
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
        super.setWeekdayCharge(isWeekDayCharge);
    }

    public boolean isWeekendCharge() {
        return isWeekEndCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        super.setWeekendCharge(isWeekEndCharge);
    }

    public boolean isHolidayCharge() {
        return isHolidayCharge;
    }

    public void setHolidayCharge(boolean isHolidayCharge) {
        super.setHolidayCharge(isHolidayCharge);
    }

}
