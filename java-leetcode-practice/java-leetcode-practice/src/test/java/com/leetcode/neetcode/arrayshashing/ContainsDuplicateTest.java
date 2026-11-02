package com.leetcode.neetcode.arrayshashing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ContainsDuplicateTest {

    private final ContainsDuplicate solver = new ContainsDuplicate();

    @Nested
    @DisplayName("LeetCode examples")
    class Examples {

        @Test
        void firstAndLastAreEqual() {
            assertTrue(solver.containsDuplicate(new int[] {1, 2, 3, 1}));
        }

        @Test
        void allDistinct() {
            assertFalse(solver.containsDuplicate(new int[] {1, 2, 3, 4}));
        }

        @Test
        void manyDuplicates() {
            assertTrue(solver.containsDuplicate(new int[] {1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));
        }
    }

    @Nested
    @DisplayName("tiny inputs")
    class Tiny {

        @Test
        void emptyArrayHasNoDuplicate() {
            assertFalse(solver.containsDuplicate(new int[] {}));
        }

        @Test
        void singleElementHasNoDuplicate() {
            assertFalse(solver.containsDuplicate(new int[] {7}));
        }

        @Test
        void twoEqualElements() {
            assertTrue(solver.containsDuplicate(new int[] {7, 7}));
        }

        @Test
        void twoDifferentElements() {
            assertFalse(solver.containsDuplicate(new int[] {7, 8}));
        }
    }

    @Nested
    @DisplayName("negatives and int extremes")
    class Extremes {

        @Test
        void sameAbsoluteValueIsNotADuplicate() {
            assertFalse(solver.containsDuplicate(new int[] {-1, 1, -2, 2, 0}));
        }

        @Test
        void negativeDuplicate() {
            assertTrue(solver.containsDuplicate(new int[] {-5, 3, -5}));
        }

        @Test
        void minAndMaxAreDistinct() {
            assertFalse(solver.containsDuplicate(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}));
        }

        @Test
        void minValueTwice() {
            assertTrue(solver.containsDuplicate(new int[] {Integer.MIN_VALUE, 0, Integer.MIN_VALUE}));
        }

        @Test
        void maxValueTwice() {
            assertTrue(solver.containsDuplicate(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}));
        }
    }

    @Nested
    @DisplayName("side effects")
    class SideEffects {

        @Test
        void doesNotChangeCallersArray() {
            int[] nums = {3, 1, 2, 1};
            int[] copy = nums.clone();
            solver.containsDuplicate(nums);
            assertArrayEquals(copy, nums, "the caller's array must stay as it was");
        }
    }

    @Nested
    @DisplayName("large input - 10^5 elements")
    class Large {

        private static final int N = 100_000;
        private static final Duration LIMIT = Duration.ofMillis(100);

        @Test
        void allDistinctIsFast() {
            int[] nums = IntStream.range(0, N).map(i -> N - i).toArray();
            assertTimeoutPreemptively(LIMIT, () -> assertFalse(solver.containsDuplicate(nums)));
        }

        @Test
        void onlyDuplicateIsFirstAndLast() {
            int[] nums = IntStream.range(0, N).toArray();
            nums[N - 1] = nums[0];
            assertTimeoutPreemptively(LIMIT, () -> assertTrue(solver.containsDuplicate(nums)));
        }
    }

    /**
     * Follow-up: O(1) extra space, and changing the input is allowed.
     * No "does not change caller's array" test here - this version may sort in place.
     */
    @Nested
    @DisplayName("containsDuplicateWithSorting - follow-up")
    class WithSorting {

        private static final int N = 100_000;
        private static final Duration LIMIT = Duration.ofMillis(100);

        @Test
        void emptyArrayHasNoDuplicate() {
            assertFalse(solver.containsDuplicateWithSorting(new int[] {}));
        }

        @Test
        void singleElementHasNoDuplicate() {
            assertFalse(solver.containsDuplicateWithSorting(new int[] {7}));
        }

        @Test
        void twoEqualElements() {
            assertTrue(solver.containsDuplicateWithSorting(new int[] {7, 7}));
        }

        @Test
        void allDistinct() {
            assertFalse(solver.containsDuplicateWithSorting(new int[] {4, 1, 3, 2}));
        }

        @Test
        void duplicatesFarApartBeforeSorting() {
            assertTrue(solver.containsDuplicateWithSorting(new int[] {3, 1, 2, 3}));
        }

        @Test
        void duplicateAtTheStartAfterSorting() {
            assertTrue(solver.containsDuplicateWithSorting(new int[] {5, 1, 9, 1}));
        }

        @Test
        void duplicateAtTheEndAfterSorting() {
            assertTrue(solver.containsDuplicateWithSorting(new int[] {9, 1, 5, 9}));
        }

        @Test
        void negativesAndExtremes() {
            assertFalse(solver.containsDuplicateWithSorting(
                    new int[] {Integer.MAX_VALUE, -1, 1, Integer.MIN_VALUE, 0}));
            assertTrue(solver.containsDuplicateWithSorting(
                    new int[] {Integer.MIN_VALUE, 0, Integer.MIN_VALUE}));
        }

        @Test
        void largeAllDistinctIsFast() {
            int[] nums = IntStream.range(0, N).map(i -> N - i).toArray();
            assertTimeoutPreemptively(LIMIT, () -> assertFalse(solver.containsDuplicateWithSorting(nums)));
        }

        @Test
        void largeOnlyDuplicateIsFirstAndLast() {
            int[] nums = IntStream.range(0, N).toArray();
            nums[N - 1] = nums[0];
            assertTimeoutPreemptively(LIMIT, () -> assertTrue(solver.containsDuplicateWithSorting(nums)));
        }
    }
}
