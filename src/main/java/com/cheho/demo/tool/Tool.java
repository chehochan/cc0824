package com.cheho.demo.tool;

public class Tool {

    private String brand;

    private String toolCode;

    private String toolType;

    private double dailyCharge;

    private boolean isWeekdayCharge;

    private boolean isWeekendCharge;

    private boolean isHolidayCharge;

    public Tool(String brand, String toolCode, String toolType) {
        this.brand = brand;
        this.toolCode = toolCode;
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(double dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return isWeekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        isWeekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return isWeekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        isWeekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return isHolidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        isHolidayCharge = holidayCharge;
    }
}
