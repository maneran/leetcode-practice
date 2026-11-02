package com.leetcode.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BetweenTwoEqualCharactersTest {

    private final BetweenTwoEqualCharacters solver = new BetweenTwoEqualCharacters();

    @Nested
    @DisplayName("LeetCode examples")
    class Examples {

        @Test
        void twoEqualNeighbours() {
            assertEquals(0, solver.betweenTwoEqualCharacters("aa"));
        }

        @Test
        void twoLettersBetween() {
            assertEquals(2, solver.betweenTwoEqualCharacters("abca"));
        }

        @Test
        void noPair() {
            assertEquals(-1, solver.betweenTwoEqualCharacters("cbzxy"));
        }
    }

    @Nested
    @DisplayName("tiny inputs")
    class Tiny {

        @Test
        void nullString() {
            assertEquals(-1, solver.betweenTwoEqualCharacters(null));
        }

        @Test
        void emptyString() {
            assertEquals(-1, solver.betweenTwoEqualCharacters(""));
        }

        @Test
        void oneCharacter() {
            assertEquals(-1, solver.betweenTwoEqualCharacters("a"));
        }

        @Test
        void twoDifferentCharacters() {
            assertEquals(-1, solver.betweenTwoEqualCharacters("ab"));
        }
    }

    @Nested
    @DisplayName("which pair wins")
    class WhichPair {

        @Test
        void threeEqualUseTheOutsidePair() {
            // a at 0, 1, 2 -> outside pair 0..2 has 1 char between
            assertEquals(1, solver.betweenTwoEqualCharacters("aaa"));
        }

        @Test
        void manyRepeatsKeepTheFirstIndex() {
            // a at 0, 2, 4, 6 -> pair 0..6 -> 5 chars between
            assertEquals(5, solver.betweenTwoEqualCharacters("abacada"));
        }

        @Test
        void bestPairIsNotTheFirstCharacter() {
            // z appears once; a at 1 and 7 -> 5 chars between
            assertEquals(5, solver.betweenTwoEqualCharacters("zabcdefaq"));
        }

        @Test
        void bestPairStartsLateInTheString() {
            // a: 0..2 -> 1, b: 3..9 -> 5
            assertEquals(5, solver.betweenTwoEqualCharacters("axabcdefgb"));
        }

        @Test
        void nestedPairsOuterWins() {
            // a: 0..5 -> 4, b: 1..4 -> 2
            assertEquals(4, solver.betweenTwoEqualCharacters("abxyba"));
        }

        @Test
        void crossingPairs() {
            // a: 0..3 -> 2, b: 1..5 -> 3
            assertEquals(3, solver.betweenTwoEqualCharacters("abcaxb"));
        }

        @Test
        void firstAndLastLettersOfTheAlphabet() {
            assertEquals(1, solver.betweenTwoEqualCharacters("aza"));
            assertEquals(1, solver.betweenTwoEqualCharacters("zaz"));
        }
    }

    @Nested
    @DisplayName("beyond LeetCode: any char")
    class AnyChar {

        @Test
        void upperAndLowerCaseAreDifferent() {
            assertEquals(-1, solver.betweenTwoEqualCharacters("aA"));
            assertEquals(1, solver.betweenTwoEqualCharacters("AbA"));
        }

        @Test
        void digitsAndSpaces() {
            // '1' at 0 and 5 -> 4 chars between (the spaces give only 2)
            assertEquals(4, solver.betweenTwoEqualCharacters("1 ab 1"));
        }

        @Test
        void hebrewLetters() {
            // first letter at 0 and 5 -> 4 chars between
            assertEquals(4, solver.betweenTwoEqualCharacters("שלום ש"));
        }
    }

    @Nested
    @DisplayName("large input - 10^5 characters")
    class Large {

        private static final int N = 100_000;
        private static final Duration LIMIT = Duration.ofMillis(200);

        @Test
        void longestPairIsTheWholeString() {
            String s = "a" + "b".repeat(N - 2) + "a";
            assertTimeoutPreemptively(LIMIT,
                    () -> assertEquals(N - 2, solver.betweenTwoEqualCharacters(s)));
        }

        @Test
        void twoLettersRepeatedAllTheWay() {
            // a: 0..N-2 -> N-3, b: 1..N-1 -> N-3
            String s = "ab".repeat(N / 2);
            assertTimeoutPreemptively(LIMIT,
                    () -> assertEquals(N - 3, solver.betweenTwoEqualCharacters(s)));
        }

        @Test
        void fiftyThousandDifferentCharsNoPair() {
            StringBuilder sb = new StringBuilder();
            for (char c = '\u0100'; sb.length() < 50_000; c++) {
                sb.append(c);
            }
            String s = sb.toString();
            assertTimeoutPreemptively(LIMIT,
                    () -> assertEquals(-1, solver.betweenTwoEqualCharacters(s)));
        }
    }
}
