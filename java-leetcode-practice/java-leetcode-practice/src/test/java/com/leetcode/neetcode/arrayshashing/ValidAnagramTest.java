package com.leetcode.neetcode.arrayshashing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidAnagramTest {

    private final ValidAnagram solver = new ValidAnagram();

    @Nested
    @DisplayName("LeetCode examples")
    class Examples {

        @Test
        void anagram() {
            assertTrue(solver.isAnagram("anagram", "nagaram"));
        }

        @Test
        void notAnagram() {
            assertFalse(solver.isAnagram("rat", "car"));
        }
    }

    @Nested
    @DisplayName("tiny inputs")
    class Tiny {

        @Test
        void bothEmpty() {
            assertTrue(solver.isAnagram("", ""));
        }

        @Test
        void oneEmpty() {
            assertFalse(solver.isAnagram("a", ""));
            assertFalse(solver.isAnagram("", "a"));
        }

        @Test
        void sameSingleLetter() {
            assertTrue(solver.isAnagram("a", "a"));
        }

        @Test
        void differentSingleLetter() {
            assertFalse(solver.isAnagram("a", "b"));
        }

        @Test
        void identicalStrings() {
            assertTrue(solver.isAnagram("listen", "listen"));
        }
    }

    @Nested
    @DisplayName("counts matter, not only which letters")
    class Counts {

        @Test
        void differentLengths() {
            assertFalse(solver.isAnagram("ab", "abb"));
            assertFalse(solver.isAnagram("abb", "ab"));
        }

        @Test
        void sameLettersDifferentCounts() {
            // same set of letters {a, b}, same length, different counts
            assertFalse(solver.isAnagram("aab", "abb"));
        }

        @Test
        void sameLengthOneLetterSwappedForAnother() {
            assertFalse(solver.isAnagram("aacc", "ccac"));
        }

        @Test
        void allSameLetter() {
            assertTrue(solver.isAnagram("zzzz", "zzzz"));
            assertFalse(solver.isAnagram("zzzz", "zzz"));
        }

        @Test
        void firstAndLastLettersOfTheAlphabet() {
            assertTrue(solver.isAnagram("az", "za"));
            assertFalse(solver.isAnagram("az", "ay"));
        }
    }

    @Nested
    @DisplayName("counts above 127")
    class BigCounts {

        @Test
        void equalCountsAbove127() {
            String s = "a".repeat(200) + "b".repeat(300);
            String t = "b".repeat(300) + "a".repeat(200);
            assertTrue(solver.isAnagram(s, t));
        }

        @Test
        void countsAbove127DifferByOne() {
            String s = "a".repeat(200) + "b".repeat(300);
            String t = "a".repeat(201) + "b".repeat(299);
            assertFalse(solver.isAnagram(s, t));
        }
    }

    @Nested
    @DisplayName("large input - 5 * 10^4 letters")
    class Large {

        private static final int N = 50_000;
        private static final Duration LIMIT = Duration.ofMillis(100);

        private String alphabetRepeated(int length, boolean reversed) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int letter = i % 26;
                sb.append((char) ('a' + (reversed ? 25 - letter : letter)));
            }
            return sb.toString();
        }

        @Test
        void largeAnagramIsFast() {
            String s = alphabetRepeated(N, false);
            String t = new StringBuilder(s).reverse().toString();
            assertTimeoutPreemptively(LIMIT, () -> assertTrue(solver.isAnagram(s, t)));
        }

        @Test
        void largeDifferenceInTheLastLetter() {
            String s = alphabetRepeated(N, false);
            char last = s.charAt(N - 1);
            char other = last == 'a' ? 'b' : 'a';
            String t = s.substring(0, N - 1) + other;
            assertTimeoutPreemptively(LIMIT, () -> assertFalse(solver.isAnagram(s, t)));
        }
    }
}
