public class TestingArea {

    private static boolean lowercase = true;

    public static void main(String[] args) {
        int chunkSize = 5;

        String test = "ABRAcadabra";
        String convertedTest = "";

        Character c = 'a';
        System.out.println((int)c);

        // Uppercase = 65 to 90
        // Lowercase = 97 to 122

        boolean previouslyLower = true;

        // Assume lowercase and swap when the value 27 is seen.
        // Required bits to represent a letter: 5 || Total numbers: 32
        String currentChar = "";

        // Compressor
        for (int i = 0; i < test.length(); i++) {
            previouslyLower = lowercase;
            currentChar = convertChar(test.charAt(i));
            if (lowercase != previouslyLower)
                convertedTest += "11011";
            convertedTest += currentChar;
        }

        System.out.println(convertedTest + "   |   " + convertedTest.length() + " bits!");

        // Expander
        String currentChunk;
        int currentLetter = 0;
        boolean testBoolean = true;
        String newString = "";
        for (int i = 0; i < convertedTest.length() / chunkSize;  i++) {
            currentChunk = "";
            for (int j = 0; j < chunkSize; j++) {
                currentChunk += convertedTest.charAt(chunkSize * i + j);
            }
            System.out.println(currentChunk);
            currentLetter = Integer.parseInt(currentChunk, 2);
            System.out.println(currentLetter);
            if (currentLetter == 27)
                testBoolean = !testBoolean;
            else
                newString += convertBinary(currentLetter, testBoolean);
        }

        System.out.println(newString);
    }

    public static char convertBinary(int i, boolean b) {
        if (!b)
            return (char)(i + 65);
        return (char)(i + 97);
    }

    public static int binToChar(char c) {
        if (c >= 65 && c <= 90) {
            setCase(false);
            return c - 65;
        }
        // If lowercase
        else if (c >= 97 && c <= 122) {
            setCase(true);
            return c - 97;
        }
        else
            return 0;
    }

    public static int convertInt(char c) {
        if (c >= 65 && c <= 90) {
            setCase(false);
            return c - 65;
        }
        // If lowercase
        else if (c >= 97 && c <= 122) {
            setCase(true);
            return c - 97;
        }
        else
            return 0;
    }

    public static String convertChar(char c) {
        String value = "";
        String finalReturn = "";
        // If uppercase
        value += Integer.toBinaryString(convertInt(c));
        if (value.length() < 5) {
            for (int i = 0; i < 5- value.length(); i++)
                finalReturn += "0";
        }
        return finalReturn + value;
    }

    public static void setCase(boolean bool) {
            lowercase = bool;
    }
}
