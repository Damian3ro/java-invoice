package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    //private Collection<Product> products;
    private Map<Product, Integer> products;

    public void addProduct(Product product) {
        // TODO: implement
    }

    public void addProduct(Product product, Integer quantity) {
        // TODO: implement
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal netTotal = new BigDecimal(0);
        for(Product product : products.keySet()) {
            netTotal = netTotal.add(product.getPrice());
        }
        return netTotal;
    }

    public BigDecimal getTax() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getTotal() {
        return BigDecimal.ZERO;
    }
}
