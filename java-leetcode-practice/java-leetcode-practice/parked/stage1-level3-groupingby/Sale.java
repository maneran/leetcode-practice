package com.leetcode.stage1.level3;

/**
 * One sale line. Given to you - do not change it.
 *
 * @param region    sales region, never null
 * @param product   product name, never null
 * @param quantity  how many units, always zero or more
 * @param unitPrice price of one unit, always zero or more
 */
public record Sale(String region, String product, int quantity, double unitPrice) {

    /** Total value of this sale line. */
    public double total() {
        return quantity * unitPrice;
    }
}
