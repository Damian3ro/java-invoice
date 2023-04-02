package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.*;

public class InvoiceTest {
    private Invoice invoice;
    private final int dayOfMonth = 1;
    private final int month = 1;
    private final int motherInLawDayOfMonth = 5;
    private final int motherInLawMonth = 3;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithTwoDifferentProducts() {
        Product onions = new TaxFreeProduct("Warzywa", new BigDecimal("10"));
        Product apples = new TaxFreeProduct("Owoce", new BigDecimal("10"));
        invoice.addProduct(onions);
        invoice.addProduct(apples);
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithManySameProducts() {
        Product onions = new TaxFreeProduct("Warzywa", BigDecimal.valueOf(10));
        invoice.addProduct(onions, 100);
        Assert.assertThat(new BigDecimal("1000"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Cukier", new BigDecimal("10")));
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")));
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("50")));
        Assert.assertThat(new BigDecimal("380"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProductOnNormalDay() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        // tax: 10.16
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")));
        // tax: 17.06
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), month, dayOfMonth);
        Assert.assertThat(new BigDecimal("37.52"), Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProductOnMotherInLawDay() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        // tax: 10.16
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")));
        // tax: 0 - Mother-in-law Day
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), motherInLawMonth, motherInLawDayOfMonth);
        Assert.assertThat(new BigDecimal("20.46"), Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProductOnNormalDay() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        // price with tax: 30.16
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")));
        // price with tax: 67.06
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), month, dayOfMonth);
        Assert.assertThat(new BigDecimal("417.52"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProductOnMotherInLawDay() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        // price with tax: 30.16
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")));
        // price with tax: 50 - Mother-in-law Day
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), motherInLawMonth, motherInLawDayOfMonth);
        Assert.assertThat(new BigDecimal("400.46"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        // 5x wino - price: 100
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")), 5);
        // 10x benzyna - price: 500
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("50")), 10);
        Assert.assertThat(new BigDecimal("650"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOneOnNormalDay() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        // 5x wino - price with tax: 150.80
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")), 5);
        // 10x wino - price with tax: 670.60
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister, 10);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), month, dayOfMonth);
        Assert.assertThat(new BigDecimal("876.10"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOneOnMotherInLawDay() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        // 5x wino - price with tax: 150.80
        invoice.addProduct(new BottleOfWine("Wino", new BigDecimal("20")), 5);
        // 10x wino - price with tax: 500 - Mother-in-law Day
        FuelCanister fuelCanister = new FuelCanister("Benzyna", new BigDecimal("50"));
        invoice.addProduct(fuelCanister, 10);
        fuelCanister.setCurrentDate(fuelCanister.getCurrentDate().getYear(), motherInLawMonth, motherInLawDayOfMonth);
        Assert.assertThat(new BigDecimal("705.50"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullProduct() {
        invoice.addProduct(null);
    }

    @Test
    public void testInvoiceHasNumberGreaterThan0() {
        int number = invoice.getNumber();
        Assert.assertThat(number, Matchers.greaterThan(0));
    }

    @Test
    public void testTwoInvoicesHaveDifferentNumbers() {
        int number1 = new Invoice().getNumber();
        int number2 = new Invoice().getNumber();
        Assert.assertNotEquals(number1, number2);
    }

    @Test
    public void testInvoiceDoesNotChangeItsNumber() {
        Assert.assertEquals(invoice.getNumber(), invoice.getNumber());
    }

    @Test
    public void testTheFirstInvoiceNumberIsLowerThanTheSecond() {
        int number1 = new Invoice().getNumber();
        int number2 = new Invoice().getNumber();
        Assert.assertThat(number1, Matchers.lessThan(number2));
    }

    @Test
    public void testIfAllPositionsOnInvoicePrintAreCorrect () {
        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("4")), 10);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 4);
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("6")), 2);
        invoice.addProduct(new BottleOfWine("Wino białe", new BigDecimal("70")), 3);
        List<String> invoicePositions = new ArrayList<>();

        for (Product product : invoice.getProducts().keySet()) {
            invoice.addInvoicePosition(product);
            String invoicePositionCheck = product.getName()
                    + ", quantity: " + invoice.getProducts().get(product)
                    + ", price: " + product.getPrice();
            invoicePositions.add(invoicePositionCheck);
        }
        for (int i = 0; i < invoicePositions.size(); i++) {
            Assert.assertEquals(invoicePositions.get(i), invoice.getInvoicePositions().get(i));
        }
    }

    @Test
    public void testIfPositionsNumberForDifferentProductsOnInvoicePrintIsCorrect () {
        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("4")), 10);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 4);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("70")), 2);
        for (Product product : invoice.getProducts().keySet()) {
            invoice.addInvoicePosition(product);
        }
        Assert.assertEquals(4,invoice.getInvoicePositions().size());
    }

    @Test
    public void testIfPositionsNumberForTheSameProductsButDifferentPricesOnInvoicePrintIsCorrect () {
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("5")), 2);
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("6")), 2);
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("7")), 2);
        for (Product product : invoice.getProducts().keySet()) {
            invoice.addInvoicePosition(product);
        }
        Assert.assertEquals(3,invoice.getInvoicePositions().size());
    }

    @Test
    public void testIfPositionsNumberDoesntChangeAfterAddingTheSameProductMultipleTimes () {
        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("4")), 10);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 4);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 16);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 7);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 12);
        invoice.addProduct(new BottleOfWine("Wino czerwone", new BigDecimal("10")), 2);
        invoice.addProduct(new FuelCanister("Benzyna", new BigDecimal("6")), 1);
        for (Product product : invoice.getProducts().keySet()) {
            invoice.addInvoicePosition(product);
        }
        Assert.assertEquals(5,invoice.getInvoicePositions().size());
    }

    @Test
    public void testIfProductQuantityOnInvoicePrintIsCorrectAfterAddingTheSameProductMultipleTimes () {
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 4);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 16);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 7);
        invoice.addProduct(new OtherProduct("Buty", new BigDecimal("100")), 12);
        int quantity1 = 0;
        for (Product product : invoice.getProducts().keySet()) {
            quantity1 = quantity1 + invoice.getProducts().get(product);
        }
        Assert.assertEquals(39, quantity1);

        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("4")), 10);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("4")), 1500);
        int quantity2 = 0;
        for (Product product : invoice.getProducts().keySet()) {
            if (product.getName().equals("Mleko")) {
                quantity2 = quantity2 + invoice.getProducts().get(product);
            }
        }
        Assert.assertEquals(1510, quantity2);

        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        invoice.addProduct(new TaxFreeProduct("Papryka", new BigDecimal("10")), 5);
        int quantity3 = 0;
        for (Product product : invoice.getProducts().keySet()) {
            if (product.getName().equals("Papryka")) {
                quantity3 = quantity3 + invoice.getProducts().get(product);
            }
        }
        Assert.assertEquals(15, quantity3);

        invoice.addProduct(new BottleOfWine("Wino białe", new BigDecimal("70")), 4);
        invoice.addProduct(new BottleOfWine("Wino białe", new BigDecimal("70")), 10);
        invoice.addProduct(new BottleOfWine("Wino białe", new BigDecimal("70")), 3);
        int quantity4 = 0;
        for (Product product : invoice.getProducts().keySet()) {
            if (product.getName().equals("Wino białe")) {
                quantity4 = quantity4 + invoice.getProducts().get(product);
            }
        }
        Assert.assertEquals(17, quantity4);

        invoice.addProduct(new FuelCanister("Benzyna Pb95", new BigDecimal("6")), 1);
        invoice.addProduct(new FuelCanister("Benzyna Pb95", new BigDecimal("6")), 2);
        invoice.addProduct(new FuelCanister("Benzyna Pb95", new BigDecimal("6")), 3);
        int quantity5 = 0;
        for (Product product : invoice.getProducts().keySet()) {
            if (product.getName().equals("Benzyna Pb95")) {
                quantity5 = quantity5 + invoice.getProducts().get(product);
            }
        }
        Assert.assertEquals(6, quantity5);
    }

}
