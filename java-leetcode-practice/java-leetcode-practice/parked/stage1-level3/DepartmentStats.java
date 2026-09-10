package com.leetcode.stage1;

/**
 * Output record.
 *
 * <p>TODO (you): add a compact constructor that validates the state.
 * A record must never exist in an invalid state.
 *
 * <p>Validation rules:
 * <ul>
 *   <li>{@code department} is null or blank -> throw IllegalArgumentException</li>
 *   <li>{@code headcount} is less than 1 -> throw IllegalArgumentException</li>
 *   <li>{@code topEarner} is null or blank -> throw IllegalArgumentException</li>
 * </ul>
 *
 * @param department    department name
 * @param headcount     how many employees in this department
 * @param averageSalary arithmetic mean of the salaries in this department
 * @param topEarner     name of the employee with the highest salary
 */
public record DepartmentStats(String department, long headcount, double averageSalary, String topEarner) {
    // TODO: compact constructor here.
}
