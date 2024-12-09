public class TestingArea {

    private static int bitLength;
    private static final int EOF = 0x80;
    private static int additonalCodes = 0;

    private static boolean lowercase = true;

    public static void main(String[] args) {
        compress();
    }

    private static void compress() {

        // Read in the file & determine it's length
        String s = "ABRACADABRA";
        int n = s.length();

        // Depending on the length of the file, determine length and # of codes used.
        bitLength = n < 10000 ? 8 : 12;
        BinaryStdOut.write(16 - bitLength, 4);

        // Create the TST that will be used to lookup codes and keep track of new ones
        TST codeDictionary = new TST();

        // Construct TST with first 128 ascii values
        for (int i = 0; i < 128; i++) {
            codeDictionary.insert(String.valueOf((char)i), i);
        }

        // Insert EOF character
        codeDictionary.insert("EOF", 0x80);

        // Iterate through the given string and use LZW to compress it.
        String currentString;
        int currentCode = 0;
        for (int i = 0; i < n; i++) {
            System.out.println(s + " | " + i);
            // Find the current longest code
            currentString = codeDictionary.getLongestPrefix(s, i);
            currentCode = codeDictionary.lookup(currentString);

            // Write out the current longest code
            System.out.println(currentString);
            System.out.println(currentString + " | " + Integer.toString(currentCode, 16) + " | " + currentString + s.charAt(i + currentString.length()));

            // Add the next code to dictionary
            codeDictionary.insert(currentString + s.charAt(i + currentString.length()), EOF + ++additonalCodes);
            i += currentString.length() -1;
        }

        BinaryStdOut.close();
    }
}
