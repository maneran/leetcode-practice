package com.leetcode;

import com.leetcode.stage1.level1.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.leetcode.stage1.level2.Order;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        //---------------------- 1.1 ----------------------
        // products = [ ("milk", "dairy", 5.0), ("Milk", "dairy", 4.0), ("bread",
        // "bakery", 12.0) ]

        // List<Product> products = new ArrayList<>(List.of(
        //         new Product("milk", "dairy", 5.0),
        //         new Product("milk", "dairy", 4.0),
        //         new Product("bread", "bakery", 12.0)));

        // double maxPrice = 10.0;

        // List<String> sortedCatalog = Catalog.cheapProductNames(products, maxPrice);
        // sortedCatalog.forEach(System.out:: println);

        // --------------------------------------------------
        
        // Product a = new Product("milk", "dairy", 5.0);
        // Product b = new Product("milk", "dairy", 5.0);

        // System.out.println(a.equals(b));


        //---------------------- 1.2 ----------------------
        //explaining the different between orElse and orElseGet:

        //Optional<Order> found = Optional.of(new Order("Ada", 1, 1.0));
        Optional<Order> found = Optional.empty();

        System.out.println("--- before orElse ---");
        Order aa = found.orElse(buildDefault());
        System.out.println("aa = " + aa);

        System.out.println("--- before orElseGet ---");
        Order bb = found.orElseGet(() -> buildDefault());
        System.out.println("bb = " + bb);
    }

    static Order buildDefault() {
        System.out.println("building default...");
        return new Order("nobody", 0, 0.0);
    }
}
