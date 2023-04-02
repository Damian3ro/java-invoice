package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FuelCanister extends Product {
    private final BigDecimal exciseTaxValue = new BigDecimal("5.56");
    private LocalDate currentDate = LocalDate.now();
    private final int dayOfMotherInLawDay = 5;
    private final int monthOfmotherInLawDay = 3;

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    public BigDecimal getExciseTaxValue() {
        if (currentDate.getDayOfMonth() == dayOfMotherInLawDay
                && currentDate.getMonthValue() == monthOfmotherInLawDay) {
            return BigDecimal.ZERO;
        }
        return exciseTaxValue;
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(int year, int month, int dayOfMonth) {
        this.currentDate = LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    public BigDecimal getTaxPercent() {
        if (currentDate.getDayOfMonth() == dayOfMotherInLawDay
                && currentDate.getMonthValue() == monthOfmotherInLawDay) {
            return BigDecimal.ZERO;
        } else {
            return super.getTaxPercent();
        }
    }

    @Override
    public BigDecimal getPriceWithTax() {
        if (currentDate.getDayOfMonth() == dayOfMotherInLawDay
                && currentDate.getMonthValue() == monthOfmotherInLawDay) {
            return super.getPrice();
        } else {
            return super.getPriceWithTax().add(exciseTaxValue);
        }
    }
}
