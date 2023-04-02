package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {
    private final int dayOfMonth = 1;
    private final int month = 1;
    private final int motherInLawDayOfMonth = 5;
    private final int motherInLawMonth = 3;
    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }

    @Test
    public void testProductPriceAndTaxForBottleOfWineProduct() {
        BottleOfWine product = new BottleOfWine("Białe wino", new BigDecimal("80.0"));
        Assert.assertThat(new BigDecimal("80"), Matchers.comparesEqualTo(product.getPrice()));
        // excise tax: 5.56
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getExciseTaxValue()));
        // tax: 0.23
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndPriceWithTaxForBottleOfWineProduct() {
        Product product = new BottleOfWine("Białe wino", new BigDecimal("80.0"));
        Assert.assertThat(new BigDecimal("80"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("103.96"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testProductTaxForFuelCanisterProductOnNormalDay() {
        FuelCanister product = new FuelCanister("Benzyna", new BigDecimal("50"));
        if (product.getCurrentDate().getMonthValue() == motherInLawMonth
                && product.getCurrentDate().getDayOfMonth() == motherInLawDayOfMonth) {
            product.setCurrentDate(product.getCurrentDate().getYear(), month, dayOfMonth);
        }
        // excise tax: 5.56 - normal day
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getExciseTaxValue()));
        // tax: 0.23 - normal day
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndPriceWithTaxForFuelCanisterProductOnNormalDay() {
        FuelCanister product = new FuelCanister("Benzyna", new BigDecimal("50"));
        if (product.getCurrentDate().getMonthValue() == motherInLawMonth
                && product.getCurrentDate().getDayOfMonth() == motherInLawDayOfMonth) {
            product.setCurrentDate(product.getCurrentDate().getYear(), month, dayOfMonth);
        }
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("67.06"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testProductTaxForFuelCanisterProductOnMotherInLawDay() {
        FuelCanister product = new FuelCanister("Benzyna", new BigDecimal("50"));
        product.setCurrentDate(product.getCurrentDate().getYear(), motherInLawMonth, motherInLawDayOfMonth);
        // excise tax: 0 - Mother-in-law Day
        Assert.assertThat(new BigDecimal("0"), Matchers.comparesEqualTo(product.getExciseTaxValue()));
        // tax: 0 - Mother-in-law Day
        Assert.assertThat(new BigDecimal("0"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndPriceWithTaxForFuelCanisterProductOnMotherInLawDay() {
        FuelCanister product = new FuelCanister("Benzyna", new BigDecimal("50"));
        product.setCurrentDate(product.getCurrentDate().getYear(), motherInLawMonth, motherInLawDayOfMonth);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
}
