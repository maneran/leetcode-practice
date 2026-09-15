package com.leetcode.stage1.level2;

/**
 * Summary of the quantity field across many orders. Given to you.
 *
 * @param min     smallest quantity
 * @param max     largest quantity
 * @param average arithmetic mean of the quantities
 * @param count   how many orders were counted
 */
public record QuantityStats(int min, int max, double average, long count) {

    /** The value to return when there are no orders at all. */
    public static final QuantityStats EMPTY = new QuantityStats(0, 0, 0.0, 0);
}
