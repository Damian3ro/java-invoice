package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<>();
    private static int nextNumber = 0;
    private final int number = ++nextNumber;
    private List<String> invoicePositions = new ArrayList<>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }

        if (checkDuplicatedProducts(product)) {
            for (Product productAdded : products.keySet()) {
                if (productAdded.getName().equals(product.getName())) {
                    quantity = quantity + products.get(productAdded);
                    products.put(productAdded, quantity);
                }
            }
        } else {
            products.put(product, quantity);
        }
    }

    public boolean checkDuplicatedProducts(Product product) {
        for (Product productAdded : products.keySet()) {
            if (productAdded.getName().equals(product.getName())
                    && productAdded.getPrice().equals(product.getPrice())) {
                return true;
            }
        }
        return false;
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return number;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void addInvoicePosition(Product product) {
        String position = product.getName() + ", quantity: " + products.get(product) + ", price: "
                + product.getPrice();
        invoicePositions.add(position);
    }

    public List<String> getInvoicePositions() {
        return invoicePositions;
    }

    public void printInvoice() {
        for (Product product  : products.keySet()) {
            addInvoicePosition(product);
        }

        System.out.println("Faktura nr " + getNumber() + "\n");

        for (int i = 0; i < invoicePositions.size(); i++) {
            System.out.println(i + 1 + ". " + invoicePositions.get(i));
        }

        System.out.println("\nLiczba pozycji: " + invoicePositions.size());
    }

}