package com.leetcode.stage1.level2;

/**
 * One order line. Given to you - do not change it.
 *
 * @param customer  customer name, never null
 * @param quantity  how many units, always zero or more
 * @param unitPrice price of one unit, always zero or more
 */
public record Order(String customer, int quantity, double unitPrice) {

    /** Total value of this order line. */
    public double total() {
        return quantity * unitPrice;
    }
}
