package com.leetcode.interview;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GoldTradingTest {

    private final GoldTrading solver = new GoldTrading();

    @Nested
    @DisplayName("LeetCode examples")
    class Examples {

        @Test
        void buyLowSellHigh() {
            assertEquals(5, solver.findBestProfit(new int[] {7, 1, 5, 3, 6, 4}));
        }

        @Test
        void priceOnlyGoesDown() {
            assertEquals(0, solver.findBestProfit(new int[] {7, 6, 4, 3, 1}));
        }
    }

    @Nested
    @DisplayName("tiny inputs")
    class Tiny {

        @Test
        void nullArray() {
            assertEquals(0, solver.findBestProfit(null));
        }

        @Test
        void noDays() {
            assertEquals(0, solver.findBestProfit(new int[] {}));
        }

        @Test
        void oneDayCannotBuyAndSell() {
            assertEquals(0, solver.findBestProfit(new int[] {5}));
        }

        @Test
        void twoDaysUp() {
            assertEquals(1, solver.findBestProfit(new int[] {1, 2}));
        }

        @Test
        void twoDaysDown() {
            assertEquals(0, solver.findBestProfit(new int[] {2, 1}));
        }

        @Test
        void flatPrice() {
            assertEquals(0, solver.findBestProfit(new int[] {3, 3, 3, 3, 3, 3, 3}));
        }
    }

    @Nested
    @DisplayName("traps: you must buy before you sell")
    class Order {

        @Test
        void maxMinusMinIsWrongWhenTheMinComesLast() {
            // max - min = 8 - 1 = 7, but 1 comes after 8
            assertEquals(5, solver.findBestProfit(new int[] {3, 8, 1, 2}));
        }

        @Test
        void buyAtTheLowestDayIsNotAlwaysBest() {
            // lowest day is 1 -> best after it is 4 -> 3. But 2 -> 9 = 7
            assertEquals(7, solver.findBestProfit(new int[] {2, 9, 1, 4}));
        }

        @Test
        void newLowLaterGivesTheBestTrade() {
            // 5 -> 6 = 1, then new low 1 -> 10 = 9
            assertEquals(9, solver.findBestProfit(new int[] {5, 6, 1, 3, 10}));
        }

        @Test
        void bestTradeIsOnTheLastTwoDays() {
            assertEquals(9, solver.findBestProfit(new int[] {9, 8, 7, 1, 10}));
        }

        @Test
        void bestTradeIsFirstToLastDay() {
            assertEquals(6, solver.findBestProfit(new int[] {1, 3, 2, 5, 4, 6, 7}));
        }

        @Test
        void dipInTheMiddle() {
            assertEquals(8, solver.findBestProfit(new int[] {4, 7, 2, 9, 3, 10, 5}));
        }
    }

    @Nested
    @DisplayName("price extremes")
    class Extremes {

        @Test
        void zeroToMaxValue() {
            assertEquals(Integer.MAX_VALUE,
                    solver.findBestProfit(new int[] {0, Integer.MAX_VALUE}));
        }

        @Test
        void maxValueThenZero() {
            assertEquals(0, solver.findBestProfit(new int[] {Integer.MAX_VALUE, 0}));
        }

        @Test
        void callerArrayIsNotChanged() {
            int[] prices = {7, 1, 5, 3, 6, 4};
            solver.findBestProfit(prices);
            assertArrayEquals(new int[] {7, 1, 5, 3, 6, 4}, prices);
        }
    }

    @Nested
    @DisplayName("large input - 10^5 days")
    class Large {

        private static final int N = 100_000;
        private static final Duration LIMIT = Duration.ofMillis(200);

        @Test
        void priceGoesUpEveryDay() {
            int[] prices = new int[N];
            for (int i = 0; i < N; i++) {
                prices[i] = i;
            }
            assertTimeoutPreemptively(LIMIT,
                    () -> assertEquals(N - 1, solver.findBestProfit(prices)));
        }

        @Test
        void priceGoesDownEveryDay() {
            int[] prices = new int[N];
            for (int i = 0; i < N; i++) {
                prices[i] = N - i;
            }
            assertTimeoutPreemptively(LIMIT,
                    () -> assertEquals(0, solver.findBestProfit(prices)));
        }
    }
}
