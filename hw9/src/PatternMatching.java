import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Your implementations of various string searching algorithms.
 *
 * @author Sreehitha Kalagara
 * @version 1.0
 * @userid skalagara6
 * @GTID 903782097
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null!");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null!");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null!");
        } else {
            List<Integer> aList = new ArrayList<Integer>();
            int m = pattern.length();
            int n = text.length();
            int x = 0;
            int y = 0;
            if (m > n) {
                return aList;
            }
            int[] table = buildFailureTable(pattern, comparator);
            while (y <= n - m) {
                while (x < m && comparator.compare(pattern.charAt(x),
                        text.charAt(y + x)) == 0) {
                    x++;
                }
                if (x == 0) {
                    y++;
                } else {
                    if (x == m) {
                        aList.add(y);
                    }
                    int nextAlignment = table[x - 1];
                    y = y + x - nextAlignment;
                    x = nextAlignment;
                }
            }
            return aList;
        }
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex.
     * pattern:       a  b  a  b  a  c
     * failureTable: [0, 0, 1, 2, 3, 0]
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null!");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null!");
        } else {
            int[] table = new int[pattern.length()];
            if (pattern.length() != 0) {
                table[0] = 0;
            }
            int x = 0;
            int y = 1;
            while (y < pattern.length()) {
                if (comparator.compare(pattern.charAt(y), pattern.charAt(x)) == 0) {
                    table[y++] = ++x;
                } else {
                    if (x != 0) {
                        x = table[x - 1];
                    } else {
                        table[y++] = x;
                    }
                }
            }
            return table;
        }
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method. Do NOT implement the Galil Rule in this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null!");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null!");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null!");
        } else {
            List<Integer> matches = new ArrayList<>();
            int x = pattern.length();
            int y = text.length();
            if (x > y) {
                return matches;
            }
            Map<Character, Integer> lastOccurrence = buildLastTable(pattern);
            int i = 0;
            while (i <= y - x) {
                int j = pattern.length() - 1;
                while (j >= 0 && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j--;
                }
                if (j == -1) {
                    matches.add(i);
                    i++;
                } else {
                    Integer shift = lastOccurrence.get(text.charAt(i + j));
                    if (shift == null) {
                        shift = -1;
                    }
                    if (shift < j) {
                        i = i + (j - shift);
                    } else {
                        i++;
                    }
                }
            }
            return matches;
        }
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null!");
        }
        Map<Character, Integer> table = new HashMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            Character a = pattern.charAt(i);
            table.put(a, i);
        }
        return table;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     * Do NOT implement your own version of Math.pow().
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null!");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null!");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null!");
        } else {
            List<Integer> matches = new ArrayList<>();
            int n = pattern.length();
            int m = text.length();
            if (n > m) {
                return matches;
            }
            int patternHash = hash(pattern, n);
            int textHash = hash(text, n);
            int i = 0;
            while (i <= m - n) {
                if (patternHash == textHash) {
                    int j = 0;
                    while (j < n && comparator.compare(text.charAt(i + j),
                            pattern.charAt(j)) == 0) {
                        j++;
                    }
                    if (j == n) {
                        matches.add(i);
                    }
                }
                i++;
                if (i <= m - n) {
                    textHash = updateHash(textHash, n, text.charAt(i - 1), text.charAt(i + n - 1));
                }
            }
            return matches;
        }

    }
    /**
     * hash function used for Rabin-Karp
     * @param curr substring to generate hash for
     * @param length of the string you want to generate the hash for
     * @return hash of the substring
     */
    private static int hash(CharSequence curr, int length) {
        int hash = 0;
        for (int i = 0; i < length; i++) {
            hash = hash + curr.charAt(i) * power(BASE, length - i - 1);
        }
        return hash;
    }

    /**
     * Updates the hash
     * @param a hash generated
     * @param b length of pattern
     * @param c character to remove
     * @param d character to add
     * @return updated hash
     */
    private static int updateHash(int a, int b,
                                  char c, char d) {
        return (a - c * power(BASE, b - 1)) * BASE + d;
    }

    /**
     * calculate the result of a number raised to a power
     * @param x the base number
     * @param y the exponent
     * @return result of the base raised to the power
     */
    private static int power(int x, int y) {
        if (y == 0) {
            return 1;
        } else if (y == 1) {
            return x;
        }
        int z = power(x, y / 2);
        if (y % 2 == 0) {
            return z * z;
        } else {
            return z * power(x, y / 2 + 1);
        }
    }
    /**
     * This method is OPTIONAL and for extra credit only.
     *
     * The Galil Rule is an addition to Boyer Moore that optimizes how we shift the pattern
     * after a full match. Please read Extra Credit: Galil Rule section in the homework pdf for details.
     *
     * Make sure to implement the buildLastTable() method and buildFailureTable() method
     * before implementing this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMooreGalilRule(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null!");
        } else if (text == null) {
            throw new IllegalArgumentException("The text is null!");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator is null!");
        } else {
            int[] table = buildFailureTable(pattern, comparator);
            int x = pattern.length();
            int y = x - table[x - 1];
            Map<Character, Integer> last = buildLastTable(pattern);
            ArrayList<Integer> toReturn = new ArrayList<>();
            int i = 0;
            int z = 0;
            while (i <= text.length() - pattern.length()) {
                int j = pattern.length() - 1;
                while (j >= z && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j--;
                }
                if (j < z) {
                    toReturn.add(i);
                    z = x - y;
                    i += y;
                } else {
                    z = 0;
                    int shift = last.getOrDefault(text.charAt(i + j), -1);
                    if (shift < j) {
                        i = i + j - shift;
                    } else {
                        i++;
                    }
                }
            }
            return toReturn;
        }
    }
}

