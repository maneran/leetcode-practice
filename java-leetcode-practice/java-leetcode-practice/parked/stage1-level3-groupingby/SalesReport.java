package com.leetcode.stage1.level3;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * STAGE 1 - LEVEL 3: {@code Collectors.groupingBy} with downstream collectors.
 *
 * <p>Three small methods. All three group the sales by region.
 * Each one uses a different <b>downstream collector</b> - the collector that
 * decides what to do with all the sales that landed in the same group.
 * Stream API only. No {@code for} loop, no {@code while} loop.
 *
 * <p><b>Shared rules for all three methods</b>
 * <ul>
 *   <li>{@code sales == null} is treated the same as an empty list.</li>
 *   <li>A {@code null} element inside the list is ignored.</li>
 *   <li>Never return {@code null}. No sales -> empty map.</li>
 *   <li>A region appears as a key only if it has at least one sale.</li>
 * </ul>
 */
public final class SalesReport {

    private SalesReport() {
    }

    /**
     * Method 1 - downstream: {@code counting()}.
     *
     * <p>How many sale lines does each region have?
     *
     * <p>Example: three sales in "North" and one in "South" ->
     * {@code {North=3, South=1}}.
     */
    public static Map<String, Long> salesCountByRegion(List<Sale> sales) {
        // TODO
        return null;
    }

    /**
     * Method 2 - downstream: {@code summingDouble(...)}, plus a map factory.
     *
     * <p>Total revenue ({@link Sale#total()}) of each region.
     *
     * <ul>
     *   <li>The keys of the returned map must iterate in <b>alphabetical
     *       order</b>. {@code groupingBy} has an overload that lets you choose
     *       which kind of {@code Map} it builds. Find it.</li>
     *   <li>A region with only zero-quantity sales still appears, with
     *       revenue {@code 0.0}.</li>
     * </ul>
     */
    public static Map<String, Double> revenueByRegion(List<Sale> sales) {
        // TODO
        return null;
    }

    /**
     * Method 3 - downstream: {@code mapping(...)} feeding another collector.
     *
     * <p>Which distinct product names were sold in each region?
     *
     * <ul>
     *   <li>Each product name appears once per region, even if it was sold
     *       many times there.</li>
     *   <li>The order inside each set does not matter.</li>
     * </ul>
     */
    public static Map<String, Set<String>> productsByRegion(List<Sale> sales) {
        // TODO
        return null;
    }
}
