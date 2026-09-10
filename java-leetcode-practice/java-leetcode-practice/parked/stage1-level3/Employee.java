package com.leetcode.stage1;

/**
 * Input record. Given to you - do not change it.
 *
 * @param name       employee name, never null and never blank
 * @param department department name, MAY be null or blank
 * @param salary     yearly salary, may be any int value (also negative)
 */
public record Employee(String name, String department, int salary) {
}
