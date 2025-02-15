package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class BottleOfWine extends Product {
    private final BigDecimal exciseTaxValue = new BigDecimal("5.56");

    public BottleOfWine(String name, BigDecimal price) {
        super(name, price, new BigDecimal("0.23"));
    }

    public BigDecimal getExciseTaxValue() {
        return exciseTaxValue;
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return super.getPriceWithTax().add(exciseTaxValue);
    }
}
