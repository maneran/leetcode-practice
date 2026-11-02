package com.leetcode.interview;

/**
 * Real interview question (2026-10-22).
 * Same as LeetCode 121: Best Time to Buy and Sell Stock (Easy).
 *
 * <p>{@code goldPrices[i]} is the gold price on day {@code i} of the week.
 * You may buy gold on one day and sell it on a <b>later</b> day. Return the
 * best profit. If no trade makes money, return {@code 0}.
 *
 * <pre>
 * [7, 1, 5, 3, 6, 4]  -> 5   (buy at 1, sell at 6)
 * [7, 6, 4, 3, 1]     -> 0   (price only goes down: do not trade)
 * </pre>
 *
 * <p><b>Contract</b>
 * <ul>
 *   <li>{@code null}, empty, or one day returns {@code 0}.</li>
 *   <li>Buy and sell on the same day is not a trade.</li>
 *   <li>{@code 0 <= goldPrices[i] <= Integer.MAX_VALUE}.</li>
 *   <li>A week has 7 days, but we also test 10^5 days.</li>
 *   <li>Do not change the caller's array.</li>
 * </ul>
 */
public class GoldTrading {

    public Integer findBestProfit(int[] goldPrices) {
        if (goldPrices == null) {
            return 0;
        }
        int lowestSoFar = Integer.MAX_VALUE;
        int bestProfit = 0;
        for (int price : goldPrices) {
            lowestSoFar = Math.min(lowestSoFar, price);
            bestProfit = Math.max(bestProfit, price - lowestSoFar);
        }
        return bestProfit;
    }
}
