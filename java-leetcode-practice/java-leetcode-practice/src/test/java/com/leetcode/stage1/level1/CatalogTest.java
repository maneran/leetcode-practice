package com.leetcode.stage1.level1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CatalogTest {

    private static Product p(String name, String category, double price) {
        return new Product(name, category, price);
    }

    @Test
    @DisplayName("null list -> empty list")
    void nullListReturnsEmptyList() {
        assertEquals(List.of(), Catalog.cheapProductNames(null, 10.0));
    }

    @Test
    @DisplayName("empty list -> empty list")
    void emptyListReturnsEmptyList() {
        assertEquals(List.of(), Catalog.cheapProductNames(List.of(), 10.0));
    }

    @Test
    @DisplayName("keeps only products under maxPrice")
    void filtersByPrice() {
        List<Product> input = List.of(
                p("milk", "dairy", 5.0),
                p("bread", "bakery", 12.0),
                p("eggs", "dairy", 9.5));
        assertEquals(List.of("EGGS", "MILK"), Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("price exactly equal to maxPrice is excluded")
    void priceOnTheBoundaryIsExcluded() {
        List<Product> input = List.of(p("milk", "dairy", 10.0));
        assertEquals(List.of(), Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("names become upper case")
    void namesAreUpperCased() {
        List<Product> input = List.of(p("cheese", "dairy", 1.0));
        assertEquals(List.of("CHEESE"), Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("names that differ only by case collapse into one")
    void duplicatesAfterUpperCasingAreRemoved() {
        List<Product> input = List.of(
                p("milk", "dairy", 5.0),
                p("MILK", "dairy", 4.0),
                p("Milk", "fridge", 3.0));
        assertEquals(List.of("MILK"), Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("result is sorted alphabetically")
    void resultIsSorted() {
        List<Product> input = List.of(
                p("zucchini", "veg", 1.0),
                p("apple", "fruit", 2.0),
                p("mango", "fruit", 3.0));
        assertEquals(List.of("APPLE", "MANGO", "ZUCCHINI"),
                Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("nothing is cheap enough -> empty list")
    void nothingMatchesReturnsEmptyList() {
        List<Product> input = List.of(p("caviar", "luxury", 900.0), p("truffle", "luxury", 800.0));
        assertEquals(List.of(), Catalog.cheapProductNames(input, 10.0));
    }

    @Test
    @DisplayName("maxPrice of zero excludes free products too")
    void zeroMaxPriceExcludesEverything() {
        List<Product> input = List.of(p("sample", "promo", 0.0));
        assertEquals(List.of(), Catalog.cheapProductNames(input, 0.0));
    }

    @Test
    @DisplayName("the input list is not changed")
    void inputListIsNotMutated() {
        List<Product> input = new ArrayList<>(List.of(p("milk", "dairy", 5.0)));
        Catalog.cheapProductNames(input, 10.0);
        assertEquals(1, input.size());
        assertEquals("milk", input.get(0).name());
    }
}
