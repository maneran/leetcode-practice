package com.leetcode.neetcode.arrayshashing;

import java.util.Arrays;
import java.util.HashSet;

/**
 * NeetCode 150 - #1 - Arrays &amp; Hashing.
 * LeetCode 217: Contains Duplicate (Easy).
 *
 * <p>Given an integer array {@code nums}, return {@code true} if any value
 * appears <b>at least twice</b> in the array. Return {@code false} if every
 * element is distinct.
 *
 * <pre>
 * Input: [1, 2, 3, 1]                    -> true
 * Input: [1, 2, 3, 4]                    -> false
 * Input: [1, 1, 1, 3, 3, 4, 3, 2, 4, 2]  -> true
 * </pre>
 *
 * <p><b>Constraints</b>
 * <ul>
 *   <li>{@code 0 <= nums.length <= 10^5} (LeetCode says 1, we also test 0)</li>
 *   <li>{@code -10^9 <= nums[i] <= 10^9} (we also test the int extremes)</li>
 *   <li>Do not change the caller's array.</li>
 * </ul>
 */
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> deDupSet = new HashSet<>();
        for(int x: nums) {
            if(!deDupSet.add(x)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicateWithSorting(int[] nums) {
        Arrays.sort(nums);
        if(nums.length < 2) {
            return false;
        }

        for(int i = 0; i < nums.length-1; i++) {
            if(nums[i] == nums[i+1]) {
                return true;
            }
        }
        return false;
    }
}
