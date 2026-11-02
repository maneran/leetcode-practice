package com.leetcode.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * Real interview question (2026-10-22).
 * Same as LeetCode 1624: Largest Substring Between Two Equal Characters (Easy).
 *
 * <p>Given a string {@code s}, return the length of the <b>longest</b>
 * substring between two equal characters, <b>not</b> counting those two
 * characters. If no character appears twice, return {@code -1}.
 *
 * <pre>
 * "aa"     -> 0    (nothing between the two 'a')
 * "abca"   -> 2    ("bc")
 * "cbzxy"  -> -1   (no character appears twice)
 * </pre>
 *
 * <p><b>Contract</b>
 * <ul>
 *   <li>{@code null} or empty string returns {@code -1}.</li>
 *   <li>LeetCode allows only lowercase letters and length up to 300.
 *       The interviewer did not say that, so we also test any {@code char}
 *       (upper case, digits, spaces, Hebrew) and length up to 10^5.</li>
 *   <li>Upper case and lower case are different characters.</li>
 * </ul>
 */
public class BetweenTwoEqualCharacters {

    public Integer betweenTwoEqualCharacters(String s) {
        if (s == null) {
            return -1;
        }
        // char -> index of its FIRST appearance
        Map<Character, Integer> firstIndex = new HashMap<>();
        int best = -1;
        for (int i = 0; i < s.length(); i++) {
            Integer first = firstIndex.putIfAbsent(s.charAt(i), i);
            if (first != null) {
                best = Math.max(best, i - first - 1);
            }
        }
        return best;
    }
}
