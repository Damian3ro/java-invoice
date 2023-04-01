package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class FuelCanister extends Product {
    private final BigDecimal additionalTaxValue = new BigDecimal(0);

    public FuelCanister(String name, BigDecimal price) {
        super(name, price, BigDecimal.ZERO);
    }

    public BigDecimal getAdditionalTaxValue() {
        return additionalTaxValue;
    }

    @Override
    public BigDecimal getPriceWithTax() {
        return getPrice().multiply(getTaxPercent()).add(getPrice()).add(additionalTaxValue);
    }
}
