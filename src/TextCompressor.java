/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Josh Little
 */
public class TextCompressor {


    private static int bitLength;
    private static final int EOF = 0x100;
    private static int totalCodes = 0;
    private static int maxCodes;

    private static void compress() {

        // Read in the file & determine it's length
        String s = BinaryStdIn.readString();
        int n = s.length();

        // Depending on the length of the file, determine length and # of codes used.
        bitLength = n < 20000 ? 9 : 12;
        BinaryStdOut.write(16 - bitLength, 4);
        maxCodes = (int)Math.pow(2, bitLength) - EOF - 1;

        // Create the TST that will be used to lookup codes and keep track of new ones
        TST codeDictionary = new TST();

        // Construct TST with first 256 ascii values
        for (int i = 0; i < EOF; i++) {
            codeDictionary.insert(String.valueOf((char)i), i);
        }

        // Insert EOF character
        codeDictionary.insert("EOF", EOF);

        // Iterate through the given string and use LZW to compress it.
        String currentString;
        int currentCode = 0;
        for (int i = 0; i < n; i++) {
            // Find the current longest code
            currentString = codeDictionary.getLongestPrefix(s, i);
            currentCode = codeDictionary.lookup(currentString);

            // Write out the current longest code
            BinaryStdOut.write(currentCode, bitLength);

            // Add the next code to dictionary
            if (i + currentString.length() < s.length() && totalCodes < maxCodes) {
                codeDictionary.insert(currentString + s.charAt(i + currentString.length()), EOF + ++totalCodes);
            }
            if (i + currentString.length() < s.length()) {
                i += currentString.length() - 1;
            } else
                break;
        }
        BinaryStdOut.write(EOF, bitLength);

        BinaryStdOut.close();
    }

    private static void expand() {

        // TODO: Complete the expand() method

        // If the next code is one greater than the current number of codes, that means it's the
        // edge case and that the next code is the current string/prefix + its first letter.

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
