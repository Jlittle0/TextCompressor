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

    private static void compress() {

        // Current idea: For all alphabetical letters, store them in 5-6 bits for a quick compression
        // and then add an escape character whenever you're switching between lowercase
        // characters and anything else. 32 is a space which could be 00000.

        // Possibly do a binary tree that can be compressed into a string of characters/bits
        // to then put in the header and use to find things.

        // For frequently appearing words, set a code for them and then send a hashmap that
        // holds the word as the key and then the value of its ranking based on frequency
        // making the word that appears most have a value of 1.

        // Potentially take a chunk of the test to find out what words are the most common
        // and assume that the chunk you took is representative of the whole. Could also take
        // a couple chunks from throughout the text to make it more accurate instead of the
        // whole thing. Most likely just going to consider words for the most appearances but
        // maybe phrases are just as likely.

        // Maybe first find out the average length of each word, if the length is greater than 2
        // then have 10 bit codes and if each word is only 2 letters long on average then
        // have 8 bit or smaller codes to represent different words.

        // Keep spaces in the final txt file to separate words so that you know what's what
        // and then you can have different # of bits for different hashed words. The would be
        // 26 * 26 * t + 26 * h + e etc. using a hash function. Which would be over 50000
        // but less than 2 to the 16th and could be represented in 2 bytes instead of 3. For
        // words that are only two letters, then you could store them in 1 byte. This hash map
        // doesn't seem to be that good sadly :(

        String s = BinaryStdIn.readString();
        int n = s.length();

       // First test case = ABRACADABRABRABRA
        // Most Common Sequence: ABRA = 00
        // New Text = 00CAD00BR00


        // Test segment



        // Size is currently 10 but it's most likely going to be a percentage of the words in
        // the text in the future.
        Object[] map = new Object[10];
        map = s.split(" ");

        BinaryStdOut.close();
    }

    private static void expand() {

        // TODO: Complete the expand() method

        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
