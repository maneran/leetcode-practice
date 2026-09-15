package com.leetcode.stage1.level2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderStatsTest {

    private static Order o(String customer, int quantity, double unitPrice) {
        return new Order(customer, quantity, unitPrice);
    }

    @Nested
    @DisplayName("mostExpensive - Optional")
    class MostExpensive {

        @Test
        void nullListGivesEmptyOptional() {
            Optional<Order> result = OrderStats.mostExpensive(null);
            assertNotNull(result, "must never return null, return Optional.empty()");
            assertTrue(result.isEmpty());
        }

        @Test
        void emptyListGivesEmptyOptional() {
            assertTrue(OrderStats.mostExpensive(List.of()).isEmpty());
        }

        @Test
        void listOfOnlyNullsGivesEmptyOptional() {
            List<Order> input = Arrays.asList(null, null);
            assertTrue(OrderStats.mostExpensive(input).isEmpty());
        }

        @Test
        void singleOrderIsReturned() {
            Order only = o("Ada", 2, 5.0);
            assertEquals(Optional.of(only), OrderStats.mostExpensive(List.of(only)));
        }

        @Test
        void picksHighestTotalNotHighestPrice() {
            // 10 x 2.0 = 20.0 beats 1 x 15.0 = 15.0
            Order cheapButMany = o("Ada", 10, 2.0);
            Order pricyButOne = o("Bob", 1, 15.0);
            assertEquals(Optional.of(cheapButMany),
                    OrderStats.mostExpensive(List.of(pricyButOne, cheapButMany)));
        }

        @Test
        void tieReturnsFirstInListOrder() {
            Order first = o("Ada", 2, 5.0);    // 10.0
            Order second = o("Bob", 5, 2.0);   // 10.0
            Order third = o("Cy", 1, 10.0);    // 10.0
            assertSame(first, OrderStats.mostExpensive(List.of(first, second, third)).get());
        }

        @Test
        void nullElementsAreSkipped() {
            Order real = o("Ada", 1, 1.0);
            List<Order> input = Arrays.asList(null, real, null);
            assertEquals(Optional.of(real), OrderStats.mostExpensive(input));
        }

        @Test
        void zeroQuantityOrderCanStillWin() {
            Order zero = o("Ada", 0, 100.0);   // 0.0
            assertEquals(Optional.of(zero), OrderStats.mostExpensive(List.of(zero)));
        }
    }

    @Nested
    @DisplayName("totalRevenue - reduce")
    class TotalRevenue {

        @Test
        void nullListGivesZero() {
            assertEquals(0.0, OrderStats.totalRevenue(null));
        }

        @Test
        void emptyListGivesZero() {
            assertEquals(0.0, OrderStats.totalRevenue(List.of()));
        }

        @Test
        void singleOrder() {
            assertEquals(12.5, OrderStats.totalRevenue(List.of(o("Ada", 5, 2.5))));
        }

        @Test
        void sumsAllOrders() {
            List<Order> input = List.of(
                    o("Ada", 2, 5.0),    // 10.0
                    o("Bob", 4, 2.5),    // 10.0
                    o("Cy", 1, 0.5));    // 0.5
            assertEquals(20.5, OrderStats.totalRevenue(input));
        }

        @Test
        void nullElementsAreSkipped() {
            List<Order> input = Arrays.asList(o("Ada", 2, 5.0), null, o("Bob", 1, 1.0));
            assertEquals(11.0, OrderStats.totalRevenue(input));
        }

        @Test
        void zeroQuantityContributesNothing() {
            List<Order> input = List.of(o("Ada", 0, 999.0), o("Bob", 1, 1.0));
            assertEquals(1.0, OrderStats.totalRevenue(input));
        }
    }

    @Nested
    @DisplayName("quantityStats - summaryStatistics")
    class QuantityStatsTests {

        @Test
        void nullListGivesEmptyStats() {
            assertEquals(QuantityStats.EMPTY, OrderStats.quantityStats(null));
        }

        @Test
        void emptyListGivesEmptyStatsNotIntegerLimits() {
            QuantityStats stats = OrderStats.quantityStats(List.of());
            assertEquals(0, stats.min(), "empty min must be 0, not Integer.MAX_VALUE");
            assertEquals(0, stats.max(), "empty max must be 0, not Integer.MIN_VALUE");
            assertEquals(0.0, stats.average());
            assertEquals(0L, stats.count());
        }

        @Test
        void singleOrder() {
            assertEquals(new QuantityStats(7, 7, 7.0, 1),
                    OrderStats.quantityStats(List.of(o("Ada", 7, 1.0))));
        }

        @Test
        void computesAllFourFields() {
            List<Order> input = List.of(
                    o("Ada", 2, 1.0),
                    o("Bob", 8, 1.0),
                    o("Cy", 5, 1.0));
            assertEquals(new QuantityStats(2, 8, 5.0, 3), OrderStats.quantityStats(input));
        }

        @Test
        void nullElementsAreNotCounted() {
            List<Order> input = Arrays.asList(o("Ada", 4, 1.0), null, o("Bob", 6, 1.0));
            assertEquals(new QuantityStats(4, 6, 5.0, 2), OrderStats.quantityStats(input));
        }

        @Test
        void zeroQuantityIsAValidMinimum() {
            List<Order> input = List.of(o("Ada", 0, 1.0), o("Bob", 10, 1.0));
            assertEquals(new QuantityStats(0, 10, 5.0, 2), OrderStats.quantityStats(input));
        }

        @Test
        void averageDoesNotOverflowOnLargeQuantities() {
            List<Order> input = List.of(
                    o("Ada", Integer.MAX_VALUE, 1.0),
                    o("Bob", Integer.MAX_VALUE, 1.0),
                    o("Cy", Integer.MAX_VALUE, 1.0));
            QuantityStats stats = OrderStats.quantityStats(input);
            assertEquals(Integer.MAX_VALUE, stats.min());
            assertEquals(Integer.MAX_VALUE, stats.max());
            assertEquals(2147483647.0, stats.average());
            assertEquals(3L, stats.count());
        }

        @Test
        void inputListIsNotMutated() {
            List<Order> input = new ArrayList<>(List.of(o("Ada", 1, 1.0)));
            OrderStats.quantityStats(input);
            assertEquals(1, input.size());
        }
    }
}
