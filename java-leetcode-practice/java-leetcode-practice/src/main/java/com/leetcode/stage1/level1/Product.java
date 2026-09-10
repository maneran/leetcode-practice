package com.leetcode.stage1.level1;

/**
 * A product in a shop catalog. Given to you - do not change it.
 *
 * @param name     product name, never null
 * @param category category name, never null
 * @param price    price in shekels, always zero or more
 */
public record Product(String name, String category, double price) {
}
