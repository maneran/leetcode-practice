package com.leetcode;

import com.leetcode.stage1.level1.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        // products = [ ("milk", "dairy", 5.0), ("Milk", "dairy", 4.0), ("bread",
        // "bakery", 12.0) ]

        List<Product> products = new ArrayList<>(List.of(
                new Product("milk", "dairy", 5.0),
                new Product("milk", "dairy", 4.0),
                new Product("bread", "bakery", 12.0)));

        double maxPrice = 10.0;

        List<String> sortedCatalog = Catalog.cheapProductNames(products, maxPrice);
        sortedCatalog.forEach(System.out:: println);

        // --------------------------------------------------
        
        Product a = new Product("milk", "dairy", 5.0);
        Product b = new Product("milk", "dairy", 5.0);

        System.out.println(a.equals(b));
    }
}
