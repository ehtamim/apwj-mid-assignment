package com.domain;

public class taxCalculator {
    private long id;
    private double basicSalary;
    private double houseRent;
    private double medicalAllowance;
    private double conveyance;
    private double festivalBonus;
    private double taxAbleIncome;
    private double taxLiability;
    private double rebate;
    private double netTax;
    public taxCalculator(long id,double basicSalary,double houseRent,double medicalAllowance, double conveyance,double festivalBonus,double taxAbleIncome, double taxLiability, double rebate, double netTax)
    {
        this.id=id;
        this.basicSalary=basicSalary;
        this.houseRent=houseRent;
        this.medicalAllowance=medicalAllowance;
        this.conveyance=conveyance;
        this.festivalBonus=festivalBonus;
        this.taxAbleIncome=taxAbleIncome;
        this.taxLiability=taxLiability;
        this.rebate=rebate;
        this.netTax=netTax;
    }

    public long getId() {
        return id;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public double getHouseRent() {
        return houseRent;
    }

    public double getMedicalAllowance() {
        return medicalAllowance;
    }

    public double getConveyance() {
        return conveyance;
    }

    public double getFestivalBonus() {
        return festivalBonus;
    }

    public double getTaxAbleIncome() {
        return taxAbleIncome;
    }

    public double getTaxLiability() {
        return taxLiability;
    }

    public double getRebate() {
        return rebate;
    }

    public double getNetTax() {
        return netTax;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public void setHouseRent(double houseRent) {
        this.houseRent = houseRent;
    }

    public void setMedicalAllowance(double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public void setFestivalBonus(double festivalBonus) {
        this.festivalBonus = festivalBonus;
    }

    public void setConveyance(double conveyance) {
        this.conveyance = conveyance;
    }
}
