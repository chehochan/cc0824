package com.cheho.demo.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Scope("prototype")
@Component
public class InvoicePrinter {
    private String toolCode;

    private String toolType;

    private String toolBrand;

    private int rentalDays;

    private LocalDate checkoutDate;

    private LocalDate dueDate;

    private double dailyRentalCharge;

    private int chargeDays;

    private double preDiscountCharge;

    private int discountPercentage;

    private double discountAmount;

    private double finalCharge;

    public InvoicePrinter(String toolCode, String toolType, String toolBrand, int rentalDays, LocalDate checkoutDate, LocalDate dueDate, double dailyRentalCharge, int chargeDays, double preDiscountCharge, int discountPercentage, double discountAmount, double finalCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.chargeDays = chargeDays;
        this.preDiscountCharge = preDiscountCharge;
        this.discountPercentage = discountPercentage;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
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

    public String getToolBrand() {
        return toolBrand;
    }

    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public void setDailyRentalCharge(double dailyRentalCharge) {
        this.dailyRentalCharge = dailyRentalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(int chargeDays) {
        this.chargeDays = chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public void setPreDiscountCharge(double preDiscountCharge) {
        this.preDiscountCharge = preDiscountCharge;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public void setFinalCharge(double finalCharge) {
        this.finalCharge = finalCharge;
    }

    public void printAgreement(){
        NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");


        System.out.println("Tool code: " + getToolCode());
        System.out.println("Tool type: " + getToolType());
        System.out.println("Tool brand: " + getToolBrand());
        System.out.println("Rental Days: " + getRentalDays());
        String checkoutDateString = getCheckoutDate().format(dateTimeFormatter);
        System.out.println("checkoutDate: " +  checkoutDateString);
        String dueDateString = getDueDate().format(dateTimeFormatter);
        System.out.println("Due date: " +  dueDateString);
        String dailyRentalCharge = moneyFormatter.format(getDailyRentalCharge());
        System.out.println("Daily rental charge: " +  dailyRentalCharge);
        System.out.println("Charge days: " +  getChargeDays());
        String preDiscountChargeString = moneyFormatter.format(getPreDiscountCharge());
        System.out.println("Pre-Discount Charge: " +  preDiscountChargeString);
        System.out.println("Discount Percentage: " +  getDiscountPercentage() + "%");
        String discountAmountString = moneyFormatter.format(getDiscountAmount());
        System.out.println("Discount Amount: " +  discountAmountString);
        String finalChargeString = moneyFormatter.format(getFinalCharge());
        System.out.println("Final Charge: " +  finalChargeString);
    }

}
