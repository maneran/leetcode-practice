package com.leetcode.stage1.level2;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * STAGE 1 - LEVEL 2: Optional, reduce, summaryStatistics.
 *
 * <p>Three small methods. Each one introduces one tool.
 * Stream API only. No {@code for} loop, no {@code while} loop.
 *
 * <p><b>Shared rules for all three methods</b>
 * <ul>
 *   <li>{@code orders == null} is treated the same as an empty list.</li>
 *   <li>A {@code null} element inside the list is ignored.</li>
 * </ul>
 *
 * <p>Hint: write a small private helper that turns the input into a clean
 * {@code Stream<Order>} and use it from all three methods.
 */
public final class OrderStats {

    private OrderStats() {
    }

    /**
     * Method 1 - tool: {@link Optional}.
     *
     * <p>Return the order with the highest {@link Order#total()}.
     *
     * <ul>
     *   <li>No orders -> {@code Optional.empty()}. Never return {@code null}.</li>
     *   <li>On a tie, return the one that appears <b>first</b> in the list.</li>
     * </ul>
     */
    public static Optional<Order> mostExpensive(List<Order> orders) {
            
        return filterNullOrders(orders).max(Comparator.comparingDouble(Order::total));
    }

    private static Stream<Order> filterNullOrders(List<Order> orders) {
        if(orders == null) {
            return Stream.empty();
        }
        return orders.stream().filter(Objects::nonNull);
    }

    /**
     * Method 2 - tool: {@code reduce}.
     *
     * <p>Return the sum of {@link Order#total()} over all orders.
     *
     * <ul>
     *   <li>No orders -> {@code 0.0}.</li>
     *   <li>Use {@code reduce}. Do not use {@code sum()} or {@code collect}.
     *       The point of this method is to learn what an identity value is.</li>
     * </ul>
     */
    public static double totalRevenue(List<Order> orders) {
        return filterNullOrders(orders)
                .mapToDouble(Order::total)
                .reduce(0.0, (a,b) -> a+b);
    }

    /**
     * Method 3 - tool: {@code summaryStatistics}.
     *
     * <p>Return min, max, average and count of the {@link Order#quantity()} field.
     *
     * <ul>
     *   <li>No orders -> {@link QuantityStats#EMPTY}.
     *       <b>Careful:</b> the statistics object has surprising min and max
     *       values when it is empty. Check the count first.</li>
     *   <li>The sum of many quantities can be large. The statistics object
     *       already handles that - read its Javadoc.</li>
     * </ul>
     */
    public static QuantityStats quantityStats(List<Order> orders) {
        IntSummaryStatistics stat = filterNullOrders(orders)
            .mapToInt(Order::quantity)
            .summaryStatistics();

        if(stat.getCount() == 0) {
            return QuantityStats.EMPTY;
        }

        return new QuantityStats(stat.getMin(),
                        stat.getMax(),
                        stat.getAverage(),
                        stat.getCount()
                    );
    }
}
