package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ProductTest {
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
    public void testProductPriceAndAdditionalTaxValueForBottleOfWineProduct() {
        BottleOfWine product = new BottleOfWine("Białe wino", new BigDecimal("80.0"));
        Assert.assertThat(new BigDecimal("80"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("5.56"), Matchers.comparesEqualTo(product.getAdditionalTaxValue()));
    }

    @Test
    public void testProductPriceAndPriceWithTaxForBottleOfWineProduct() {
        Product product = new BottleOfWine("Białe wino", new BigDecimal("80.0"));
        Assert.assertThat(new BigDecimal("80"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("103.96"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test
    public void testProductPriceAndAdditionalTaxValueForFuelCanisterProduct() {
        Product product = new FuelCanister("Benzyna", new BigDecimal("6.5"));
        Assert.assertThat(new BigDecimal("6.5"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal(0), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndPriceWithTaxForFuelCanisterProduct() {
        Product product = new FuelCanister("Benzyna", new BigDecimal("6.5"));
        Assert.assertThat(new BigDecimal("6.5"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("6.5"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }
}
