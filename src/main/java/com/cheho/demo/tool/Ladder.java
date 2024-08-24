package com.cheho.demo.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Ladder extends Tool {

    @Value("${ladder.daily-charge}")
    private double dailyCharge;

    @Value("${ladder.weekday-charge}")
    private boolean isWeekDayCharge;

    @Value("${ladder.weekend-charge}")
    private boolean isWeekendCharge;

    @Value("${ladder.holiday-charge}")
    private boolean isholidayCharge;

    public Ladder(String brand, String toolCode) {
        super(brand, toolCode, "Ladder");
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
