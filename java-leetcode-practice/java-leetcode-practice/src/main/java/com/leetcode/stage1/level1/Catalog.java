package com.leetcode.stage1.level1;

import java.util.List;

/**
 * STAGE 1 - LEVEL 1: first steps with the Stream API.
 *
 * <p>Write the body of {@link #cheapProductNames(List, double)}.
 * Use the Stream API. No {@code for} loop and no {@code while} loop.
 *
 * <p><b>The contract</b>
 *
 * <ol>
 *   <li>Keep only products whose price is <b>strictly less than</b>
 *       {@code maxPrice}. A price equal to {@code maxPrice} is out.</li>
 *   <li>Return the product <b>names in UPPER CASE</b>.</li>
 *   <li>No duplicate names in the result.</li>
 *   <li>Sort the names in alphabetical order.</li>
 *   <li>If {@code products} is {@code null}, return an empty list.</li>
 * </ol>
 *
 * <p>Example:
 * <pre>
 *   products = [ ("milk", "dairy", 5.0), ("Milk", "dairy", 4.0), ("bread", "bakery", 12.0) ]
 *   maxPrice = 10.0
 *   -> [ "MILK" ]
 * </pre>
 *
 * <p>Hint - the tools you need are all on {@code Stream}:
 * {@code filter}, {@code map}, {@code distinct}, {@code sorted}, {@code toList}.
 */
public final class Catalog {

    private Catalog() {
    }

    public static List<String> cheapProductNames(List<Product> products, double maxPrice) {
        if (products == null) {
            return List.of();
        }
        return products.stream().
            filter(product -> product.price() < maxPrice).
            map(product -> product.name().toUpperCase()).
            distinct().
            sorted().
            toList();
    }
}
