package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    //private Collection<Product> products;
    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal netTotal = new BigDecimal(0);
        for(Product product : products.keySet()) {
            netTotal = netTotal.add(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
        }
        return netTotal;
    }

    public BigDecimal getTax() {
        if(products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal tax = new BigDecimal(0);
        for(Product product : products.keySet()) {
            tax = tax.add(product.getPrice().multiply(product.getTaxPercent()));
        }
        return tax;
    }

    public BigDecimal getTotal() {
        if(products.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = new BigDecimal(0);
        for(Product product : products.keySet()) {
            total = total.add(product.getPriceWithTax().multiply(BigDecimal.valueOf(products.get(product))));
        }
        return total;
    }
}
