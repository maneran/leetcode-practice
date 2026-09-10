package com.leetcode.stage1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DepartmentAnalyticsTest {

    private static Employee e(String name, String dept, int salary) {
        return new Employee(name, dept, salary);
    }

    @Nested
    @DisplayName("DepartmentStats record validation")
    class RecordValidation {

        @Test
        void rejectsNullDepartment() {
            assertThrows(IllegalArgumentException.class,
                    () -> new DepartmentStats(null, 1, 10.0, "Ada"));
        }

        @Test
        void rejectsBlankDepartment() {
            assertThrows(IllegalArgumentException.class,
                    () -> new DepartmentStats("   ", 1, 10.0, "Ada"));
        }

        @Test
        void rejectsZeroHeadcount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new DepartmentStats("Eng", 0, 10.0, "Ada"));
        }

        @Test
        void rejectsNegativeHeadcount() {
            assertThrows(IllegalArgumentException.class,
                    () -> new DepartmentStats("Eng", -3, 10.0, "Ada"));
        }

        @Test
        void rejectsBlankTopEarner() {
            assertThrows(IllegalArgumentException.class,
                    () -> new DepartmentStats("Eng", 1, 10.0, ""));
        }

        @Test
        void acceptsNegativeAverage() {
            DepartmentStats stats = new DepartmentStats("Eng", 1, -50.5, "Ada");
            assertEquals(-50.5, stats.averageSalary());
        }
    }

    @Nested
    @DisplayName("Input guards")
    class InputGuards {

        @Test
        void nullInputThrows() {
            assertThrows(IllegalArgumentException.class,
                    () -> DepartmentAnalytics.summarize(null));
        }

        @Test
        void emptyInputReturnsEmptyList() {
            assertEquals(List.of(), DepartmentAnalytics.summarize(List.of()));
        }

        @Test
        void listOfOnlyNullsReturnsEmptyList() {
            List<Employee> input = Arrays.asList(null, null, null);
            assertEquals(List.of(), DepartmentAnalytics.summarize(input));
        }

        @Test
        void nullElementsAreIgnored() {
            List<Employee> input = Arrays.asList(null, e("Ada", "Eng", 100), null);
            assertEquals(
                    List.of(new DepartmentStats("Eng", 1, 100.0, "Ada")),
                    DepartmentAnalytics.summarize(input));
        }

        @Test
        void returnedListIsUnmodifiable() {
            List<DepartmentStats> out =
                    DepartmentAnalytics.summarize(List.of(e("Ada", "Eng", 100)));
            assertThrows(UnsupportedOperationException.class,
                    () -> out.add(new DepartmentStats("X", 1, 1.0, "Y")));
        }

        @Test
        void inputListIsNotMutated() {
            List<Employee> input = new ArrayList<>(List.of(e("Ada", "Eng", 100)));
            DepartmentAnalytics.summarize(input);
            assertEquals(1, input.size());
        }
    }

    @Nested
    @DisplayName("Department key normalisation")
    class DepartmentKey {

        @Test
        void nullDepartmentBecomesUnknown() {
            List<DepartmentStats> out =
                    DepartmentAnalytics.summarize(List.of(e("Ada", null, 100)));
            assertEquals(List.of(new DepartmentStats("UNKNOWN", 1, 100.0, "Ada")), out);
        }

        @Test
        void blankDepartmentBecomesUnknown() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Ada", "   ", 100), e("Bob", null, 300), e("Cy", "\t\n", 200)));
            assertEquals(List.of(new DepartmentStats("UNKNOWN", 3, 200.0, "Bob")), out);
        }

        @Test
        void departmentNameIsTrimmedAndGroupsMerge() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Ada", "  Eng  ", 100), e("Bob", "Eng", 300)));
            assertEquals(List.of(new DepartmentStats("Eng", 2, 200.0, "Bob")), out);
        }

        @Test
        void groupingIsCaseSensitive() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Ada", "Eng", 100), e("Bob", "eng", 100)));
            assertEquals(
                    List.of(
                            new DepartmentStats("Eng", 1, 100.0, "Ada"),
                            new DepartmentStats("eng", 1, 100.0, "Bob")),
                    out);
        }
    }

    @Nested
    @DisplayName("Top earner")
    class TopEarner {

        @Test
        void picksHighestSalary() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Ada", "Eng", 100), e("Bob", "Eng", 900), e("Cy", "Eng", 500)));
            assertEquals("Bob", out.get(0).topEarner());
        }

        @Test
        void tieBreaksOnSmallestName() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Zoe", "Eng", 900), e("Ada", "Eng", 900), e("Mia", "Eng", 900)));
            assertEquals("Ada", out.get(0).topEarner());
        }

        @Test
        void tieBreakIsCaseSensitiveCompareTo() {
            // 'B' (66) is smaller than 'a' (97) in String.compareTo
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("ada", "Eng", 900), e("Bob", "Eng", 900)));
            assertEquals("Bob", out.get(0).topEarner());
        }

        @Test
        void worksWhenAllSalariesAreNegative() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("Ada", "Eng", -100), e("Bob", "Eng", -900)));
            assertEquals(new DepartmentStats("Eng", 2, -500.0, "Ada"), out.get(0));
        }
    }

    @Nested
    @DisplayName("Arithmetic")
    class Arithmetic {

        @Test
        void averageDoesNotOverflowOnIntMax() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(
                            e("Ada", "Eng", Integer.MAX_VALUE),
                            e("Bob", "Eng", Integer.MAX_VALUE),
                            e("Cy", "Eng", Integer.MAX_VALUE)));
            assertEquals(2147483647.0, out.get(0).averageSalary());
        }

        @Test
        void averageDoesNotUnderflowOnIntMin() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(
                            e("Ada", "Eng", Integer.MIN_VALUE),
                            e("Bob", "Eng", Integer.MIN_VALUE)));
            assertEquals(-2147483648.0, out.get(0).averageSalary());
        }

        @Test
        void averageOfHugeGroupStaysCorrect() {
            List<Employee> input = new ArrayList<>();
            for (int i = 0; i < 1_000; i++) {
                input.add(e("emp" + i, "Eng", Integer.MAX_VALUE));
            }
            List<DepartmentStats> out = DepartmentAnalytics.summarize(input);
            assertEquals(1_000L, out.get(0).headcount());
            assertEquals(2147483647.0, out.get(0).averageSalary());
        }
    }

    @Nested
    @DisplayName("Ordering")
    class Ordering {

        @Test
        void sortsByAverageDescending() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(
                            e("Ada", "Eng", 100),
                            e("Bob", "HR", 300),
                            e("Cy", "Ops", 200)));
            assertEquals(List.of("HR", "Ops", "Eng"),
                    out.stream().map(DepartmentStats::department).toList());
        }

        @Test
        void tieOnAverageSortsByDepartmentAscending() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(
                            e("Ada", "Zeta", 100),
                            e("Bob", "Alpha", 100),
                            e("Cy", "Mid", 100)));
            assertEquals(List.of("Alpha", "Mid", "Zeta"),
                    out.stream().map(DepartmentStats::department).toList());
        }

        @Test
        void unknownGroupTakesPartInOrdering() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(
                            e("Ada", null, 1000),
                            e("Bob", "Eng", 10),
                            e("Cy", "Eng", 20)));
            assertEquals(
                    List.of(
                            new DepartmentStats("UNKNOWN", 1, 1000.0, "Ada"),
                            new DepartmentStats("Eng", 2, 15.0, "Cy")),
                    out);
        }
    }

    @Nested
    @DisplayName("Full scenario")
    class FullScenario {

        @Test
        void mixedInput() {
            List<Employee> input = Arrays.asList(
                    e("Ada", "Eng", 100),
                    null,
                    e("Bob", " Eng ", 200),
                    e("Cy", "HR", 300),
                    e("Dee", null, 50),
                    e("Eve", "", 150),
                    e("Fay", "eng", 400),
                    e("Gil", "HR", 300));

            List<DepartmentStats> expected = List.of(
                    new DepartmentStats("eng", 1, 400.0, "Fay"),
                    new DepartmentStats("HR", 2, 300.0, "Cy"),
                    new DepartmentStats("Eng", 2, 150.0, "Bob"),
                    new DepartmentStats("UNKNOWN", 2, 100.0, "Eve"));

            assertEquals(expected, DepartmentAnalytics.summarize(input));
        }

        @Test
        void resultSizeMatchesDistinctKeys() {
            List<DepartmentStats> out = DepartmentAnalytics.summarize(
                    List.of(e("A", "x", 1), e("B", "x", 2), e("C", "y", 3), e("D", null, 4)));
            assertTrue(out.size() == 3, "expected 3 groups but got " + out.size());
        }
    }
}
