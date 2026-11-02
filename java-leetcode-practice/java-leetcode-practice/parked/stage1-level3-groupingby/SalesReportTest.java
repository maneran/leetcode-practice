package com.leetcode.stage1.level3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SalesReportTest {

    private static Sale s(String region, String product, int quantity, double unitPrice) {
        return new Sale(region, product, quantity, unitPrice);
    }

    @Nested
    @DisplayName("salesCountByRegion - counting")
    class SalesCountByRegion {

        @Test
        void nullListGivesEmptyMap() {
            Map<String, Long> result = SalesReport.salesCountByRegion(null);
            assertNotNull(result, "must never return null, return an empty map");
            assertTrue(result.isEmpty());
        }

        @Test
        void emptyListGivesEmptyMap() {
            assertEquals(Map.of(), SalesReport.salesCountByRegion(List.of()));
        }

        @Test
        void listOfOnlyNullsGivesEmptyMap() {
            List<Sale> input = Arrays.asList(null, null);
            assertEquals(Map.of(), SalesReport.salesCountByRegion(input));
        }

        @Test
        void singleSaleCountsAsOne() {
            assertEquals(Map.of("North", 1L),
                    SalesReport.salesCountByRegion(List.of(s("North", "Pen", 1, 1.0))));
        }

        @Test
        void countsLinesNotQuantities() {
            // two lines in North, total quantity 100 - the answer is 2, not 100
            List<Sale> input = List.of(
                    s("North", "Pen", 99, 1.0),
                    s("North", "Ink", 1, 1.0),
                    s("South", "Pen", 5, 1.0));
            assertEquals(Map.of("North", 2L, "South", 1L),
                    SalesReport.salesCountByRegion(input));
        }

        @Test
        void nullElementsAreSkipped() {
            List<Sale> input = Arrays.asList(null, s("North", "Pen", 1, 1.0), null);
            assertEquals(Map.of("North", 1L), SalesReport.salesCountByRegion(input));
        }

        @Test
        void zeroQuantitySaleStillCountsAsALine() {
            assertEquals(Map.of("North", 1L),
                    SalesReport.salesCountByRegion(List.of(s("North", "Pen", 0, 9.0))));
        }

        @Test
        void regionNamesAreCaseSensitive() {
            List<Sale> input = List.of(s("North", "Pen", 1, 1.0), s("north", "Pen", 1, 1.0));
            assertEquals(Map.of("North", 1L, "north", 1L),
                    SalesReport.salesCountByRegion(input));
        }
    }

    @Nested
    @DisplayName("revenueByRegion - summingDouble + sorted keys")
    class RevenueByRegion {

        @Test
        void nullListGivesEmptyMap() {
            Map<String, Double> result = SalesReport.revenueByRegion(null);
            assertNotNull(result, "must never return null, return an empty map");
            assertTrue(result.isEmpty());
        }

        @Test
        void emptyListGivesEmptyMap() {
            assertEquals(Map.of(), SalesReport.revenueByRegion(List.of()));
        }

        @Test
        void sumsTotalNotUnitPrice() {
            // 3 x 2.0 + 1 x 4.0 = 10.0 (not 2.0 + 4.0 = 6.0)
            List<Sale> input = List.of(s("North", "Pen", 3, 2.0), s("North", "Ink", 1, 4.0));
            Map<String, Double> result = SalesReport.revenueByRegion(input);
            assertEquals(1, result.size());
            assertEquals(10.0, result.get("North"), 1e-9);
        }

        @Test
        void eachRegionSumsOnlyItsOwnSales() {
            List<Sale> input = List.of(
                    s("North", "Pen", 1, 1.0),
                    s("South", "Pen", 1, 100.0),
                    s("North", "Ink", 2, 1.5));
            Map<String, Double> result = SalesReport.revenueByRegion(input);
            assertEquals(4.0, result.get("North"), 1e-9);
            assertEquals(100.0, result.get("South"), 1e-9);
        }

        @Test
        void regionWithOnlyZeroQuantityStillAppearsWithZeroRevenue() {
            Map<String, Double> result =
                    SalesReport.revenueByRegion(List.of(s("North", "Pen", 0, 50.0)));
            assertTrue(result.containsKey("North"), "region must appear even with 0.0 revenue");
            assertEquals(0.0, result.get("North"), 1e-9);
        }

        @Test
        void nullElementsAreSkipped() {
            List<Sale> input = Arrays.asList(null, s("North", "Pen", 2, 3.0), null);
            Map<String, Double> result = SalesReport.revenueByRegion(input);
            assertEquals(1, result.size());
            assertEquals(6.0, result.get("North"), 1e-9);
        }

        @Test
        void keysIterateInAlphabeticalOrder() {
            // inserted in a scrambled order on purpose
            List<Sale> input = List.of(
                    s("Zeta", "Pen", 1, 1.0),
                    s("Mu", "Pen", 1, 1.0),
                    s("Alpha", "Pen", 1, 1.0),
                    s("Omega", "Pen", 1, 1.0),
                    s("Beta", "Pen", 1, 1.0),
                    s("Kappa", "Pen", 1, 1.0),
                    s("Gamma", "Pen", 1, 1.0));
            List<String> keys = new ArrayList<>(SalesReport.revenueByRegion(input).keySet());
            assertEquals(List.of("Alpha", "Beta", "Gamma", "Kappa", "Mu", "Omega", "Zeta"), keys);
        }

        @Test
        void manySmallAmountsAddUpWithoutBigDrift() {
            // 1000 x 0.1 - floating point is not exact, so we allow a small delta
            List<Sale> input = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                input.add(s("North", "Pen", 1, 0.1));
            }
            assertEquals(100.0, SalesReport.revenueByRegion(input).get("North"), 1e-6);
        }
    }

    @Nested
    @DisplayName("productsByRegion - mapping into a Set")
    class ProductsByRegion {

        @Test
        void nullListGivesEmptyMap() {
            Map<String, Set<String>> result = SalesReport.productsByRegion(null);
            assertNotNull(result, "must never return null, return an empty map");
            assertTrue(result.isEmpty());
        }

        @Test
        void emptyListGivesEmptyMap() {
            assertEquals(Map.of(), SalesReport.productsByRegion(List.of()));
        }

        @Test
        void singleSaleGivesSetOfOne() {
            assertEquals(Map.of("North", Set.of("Pen")),
                    SalesReport.productsByRegion(List.of(s("North", "Pen", 1, 1.0))));
        }

        @Test
        void sameProductSoldTwiceAppearsOnce() {
            List<Sale> input = List.of(
                    s("North", "Pen", 1, 1.0),
                    s("North", "Pen", 7, 1.0),
                    s("North", "Pen", 2, 2.0));
            assertEquals(Map.of("North", Set.of("Pen")), SalesReport.productsByRegion(input));
        }

        @Test
        void sameProductInTwoRegionsAppearsInBoth() {
            List<Sale> input = List.of(s("North", "Pen", 1, 1.0), s("South", "Pen", 1, 1.0));
            assertEquals(Map.of("North", Set.of("Pen"), "South", Set.of("Pen")),
                    SalesReport.productsByRegion(input));
        }

        @Test
        void differentProductsAreAllKept() {
            List<Sale> input = List.of(
                    s("North", "Pen", 1, 1.0),
                    s("North", "Ink", 1, 1.0),
                    s("North", "Pad", 1, 1.0));
            assertEquals(Map.of("North", Set.of("Pen", "Ink", "Pad")),
                    SalesReport.productsByRegion(input));
        }

        @Test
        void nullElementsAreSkipped() {
            List<Sale> input = Arrays.asList(null, s("North", "Pen", 1, 1.0), null);
            assertEquals(Map.of("North", Set.of("Pen")), SalesReport.productsByRegion(input));
        }

        @Test
        void regionWithNoSalesIsNotAKey() {
            Map<String, Set<String>> result =
                    SalesReport.productsByRegion(List.of(s("North", "Pen", 1, 1.0)));
            assertFalse(result.containsKey("South"));
        }
    }
}
