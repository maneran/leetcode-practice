package com.leetcode.stage1;

import java.util.List;

/**
 * STAGE 1 - WARM UP: Modern Java, Streams and Records.
 *
 * <p>Write the body of {@link #summarize(List)} using the Stream API only.
 * No {@code for} loop, no {@code while} loop, no external mutable state.
 *
 * <p><b>The contract</b>
 *
 * <ol>
 *   <li><b>Null input:</b> {@code employees == null} -> throw
 *       {@link IllegalArgumentException}.</li>
 *   <li><b>Null elements:</b> a {@code null} inside the list is ignored.</li>
 *   <li><b>Department key:</b> trim the department name. If it is {@code null}
 *       or blank after the trim, use the key {@code "UNKNOWN"}. Grouping is
 *       case sensitive, so {@code "Eng"} and {@code "eng"} are two groups.</li>
 *   <li><b>headcount:</b> number of employees in the group.</li>
 *   <li><b>averageSalary:</b> arithmetic mean of the salaries.
 *       <b>Careful:</b> the sum of many {@code int} salaries can overflow.</li>
 *   <li><b>topEarner:</b> name of the employee with the highest salary in the
 *       group. On a tie, pick the name that is smallest by
 *       {@link String#compareTo}.</li>
 *   <li><b>Order:</b> sort the result by {@code averageSalary} descending,
 *       then by {@code department} ascending.</li>
 *   <li><b>Immutability:</b> the returned list must be unmodifiable.</li>
 * </ol>
 *
 * <p>Example:
 * <pre>
 *   [ ("Ada", "Eng", 100), ("Bob", "Eng", 200), ("Cy", "HR", 300) ]
 *   ->
 *   [ DepartmentStats("HR", 1, 300.0, "Cy"),
 *     DepartmentStats("Eng", 2, 150.0, "Bob") ]
 * </pre>
 */
public final class DepartmentAnalytics {

    private DepartmentAnalytics() {
    }

    public static List<DepartmentStats> summarize(List<Employee> employees) {
        // TODO: your code here.
        throw new UnsupportedOperationException("not implemented yet");
    }
}
