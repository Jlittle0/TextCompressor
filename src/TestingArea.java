import java.util.ArrayList;

public class TestingArea {

    private static int bitLength;
    private static final int EOF = 0x100;
    private static int totalCodes = 0;
    private static int maxCodes;

    private static boolean lowercase = true;

    public static void main(String[] args) {
        String s = compress();
    }

    private static String compress() {

        // Read in the file & determine it's length
        String s = "ABRACADABRA";
        int n = s.length();

        // Depending on the length of the file, determine length and # of codes used.
        bitLength = n < 20000 ? 9 : 12;
        BinaryStdOut.write(16 - bitLength, 4);

        // Create the TST that will be used to lookup codes and keep track of new ones
        TST codeDictionary = new TST();

        // Construct TST with first 256 ascii values
        for (int i = 0; i < EOF; i++) {
            codeDictionary.insert(String.valueOf((char)i), i);
        }

        // Insert EOF character
        codeDictionary.insert("EOF", EOF);

        // Iterate through the given string and use LZW to compress it.
        String currentString, finalCode = "";
        int currentCode = 0;
        for (int i = 0; i < n; i++) {
            System.out.println(s + " | " + i);
            // Find the current longest code
            currentString = codeDictionary.getLongestPrefix(s, i);
            System.out.println(currentString);
            currentCode = codeDictionary.lookup(currentString);

            // Write out the current longest code
            System.out.println(currentString);
            finalCode += Integer.toString(currentCode, 16) + " ";


            // Add the next code to dictionary
            if (i + currentString.length() < s.length()) {
                System.out.println(currentString + " | " + Integer.toString(currentCode, 16) + " | " + currentString + s.charAt(i + currentString.length()));
                codeDictionary.insert(currentString + s.charAt(i + currentString.length()), EOF + ++totalCodes);
                i += currentString.length() - 1;
            }
            else
                break;
        }
        finalCode += Integer.toString(EOF, 16);
        System.out.println(finalCode);
        System.out.println(totalCodes);
        return finalCode;
    }

}
