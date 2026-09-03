/**
 * COMP202 - Data Structures and Algorithms
 * Assignment 2 - Bonus: Hash-Based Cryptography
 *
 * In this bonus section, you will implement a simplified version of HMAC
 * (Hash-based Message Authentication Code). HMAC is widely used in real-world
 * systems to verify message integrity and authenticity -- for example, in
 * HTTPS, API authentication tokens, and digital signatures.
 *
 * The idea is simple: given a message and a secret key, produce a numeric
 * "authentication code" using a hash function. Anyone with the secret key
 * can recompute the code and verify the message has not been tampered with.
 *
 * You will reuse the polynomial rolling hash concept from Part 2, but apply
 * it here for cryptographic (authentication) purposes rather than table indexing.
 *
 * CONSTRAINTS:
 * - You may NOT use any classes from java.util.
 * - You may use Java arrays.
 * - You may ONLY modify the sections marked with // TODO.
 * - Do NOT change method signatures, class name, or provided code.
 *
 * Total: 20 bonus points
 */
public class HashCrypto {

    // ========================== STUDENT INFO ==========================
    // Fill in your information below. This is MANDATORY and will be used
    // for grading identification. Do NOT leave these empty.
    public static final String STUDENT_NAME = "";       // TODO: Enter your full name (e.g., "Ahmet Yilmaz")
    public static final String STUDENT_ID = "";         // TODO: Enter your student ID number (e.g., "0012345")
    public static final String STUDENT_KU_EMAIL = "";   // TODO: Enter your KU email (e.g., "ayilmaz20@ku.edu.tr")
    // ==================================================================

    /**
     * A prime number used as the modulus in HMAC computation.
     * DO NOT MODIFY.
     */
    private static final int PRIME_MODULUS = 10007;

    /**
     * Computes a polynomial rolling hash of the given message with the given modulus.
     * [3 points]
     *
     * This is similar to the hash function in Part 2, but takes an arbitrary
     * modulus parameter instead of using the table capacity.
     *
     * Formula:
     *   h(s) = (s[0] * 31^(n-1) + s[1] * 31^(n-2) + ... + s[n-1]) % modulus
     *
     * The result must be non-negative:
     *   result = ((hash % modulus) + modulus) % modulus
     *
     * Use long arithmetic to avoid overflow.
     *
     * @param message the string to hash
     * @param modulus the modulus to use
     * @return the hash value (non-negative, in range [0, modulus-1])
     */
    public static int hashMessage(String message, int modulus) {
        // TODO
        return 0;
    }

    /**
     * Generates a simplified HMAC (Hash-based Message Authentication Code).
     * [7 points]
     *
     * The simplified HMAC is computed in two steps:
     *   1. innerHash = hashMessage(secretKey + message, PRIME_MODULUS)
     *   2. outerHash = hashMessage(secretKey + innerHash, PRIME_MODULUS)
     *
     * Where "+" denotes string concatenation. In step 2, innerHash (an integer)
     * is converted to a string before concatenation (use String.valueOf() or
     * Integer.toString()).
     *
     * Pseudocode:
     *   innerInput = secretKey + message          // string concatenation
     *   innerHash  = hashMessage(innerInput, PRIME_MODULUS)
     *   outerInput = secretKey + String.valueOf(innerHash)
     *   outerHash  = hashMessage(outerInput, PRIME_MODULUS)
     *   return outerHash
     *
     * @param message the message to authenticate
     * @param secretKey the secret key known only to sender and receiver
     * @return the HMAC code
     */
    public static int generateHMAC(String message, String secretKey) {
        // TODO
        return 0;
    }

    /**
     * Verifies whether a given HMAC code matches the expected code for a message.
     * [3 points]
     *
     * Recompute the HMAC for the given message and secret key, then compare
     * it with the expected code.
     *
     * @param message the message to verify
     * @param secretKey the secret key
     * @param expectedCode the HMAC code to verify against
     * @return true if the recomputed HMAC matches expectedCode, false otherwise
     */
    public static boolean verifyHMAC(String message, String secretKey, int expectedCode) {
        // TODO
        return false;
    }

    /**
     * Detects tampered messages by verifying their HMAC codes.
     * [7 points]
     *
     * Given parallel arrays of messages and their supposed HMAC codes, return
     * an array containing only the messages whose codes do NOT match (i.e.,
     * the messages that have been tampered with). Preserve the original order.
     *
     * Pseudocode:
     *   1. First pass: count the number of tampered messages
     *      (where verifyHMAC returns false).
     *   2. Create a String array of that count.
     *   3. Second pass: fill in the tampered messages, preserving order.
     *   4. Return the array.
     *
     * @param messages array of messages
     * @param codes array of HMAC codes (codes[i] corresponds to messages[i])
     * @param secretKey the secret key used to generate the original codes
     * @return array of messages that failed verification (tampered), in original order
     */
    public static String[] detectTampering(String[] messages, int[] codes, String secretKey) {
        // TODO
        return new String[0];
    }
}
