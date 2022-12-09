package chucknorris;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        choice();
    }

    static String binaryRep(char c) {
        return String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
    }

    static void binaryFormattedResults(String s) {

        System.out.println("The result:");
        for (char c: s.toCharArray()){
            String f = "%7s".formatted(binaryRep(c)).replace(' ', '0');
            String t = "%c = %7s".formatted(c, f);
            System.out.println(t);
        }
    }

    // Encode Message in Chuck norris technique
    static void encodedResults(String s) {

        String binifiedString = binaryStringer(s);
        String encoded_msg = chuckNorrisTech(binifiedString);
        System.out.println("Encoded string:");
        System.out.println(encoded_msg.trim());
    }

    // Creates a single binary string for all the characters
    static String binaryStringer(String s) {
        StringBuilder binString = new StringBuilder();

        for (char c : s.toCharArray()) {
            String f = binaryRep(c);
//            System.out.println(f);
//            System.out.println(chuckNorrisTech(f));
            binString.append(f);
        }

        return binString.toString();
    }

    // Encode single characters into chuck norris technique
    static String chuckNorrisTech(String bin) {

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < bin.length() ; i++) {

            // Get the 0 or 1 character from the binary string and set its occurrence to 1
            char currentChar = bin.charAt(i);
            int noOfOccurrence = 1;

            // iterate till next character is not the same and record the no. of occurrence
            while (true) {

                if (i == bin.length() - 1) {
                    break;
                }

                char a = bin.charAt(i);
                char b = bin.charAt(i + 1);

                if (a == b) {
                    noOfOccurrence++;
                    i++;
                } else {
                    break;
                }
            }
            // Print out the character and its occurrence
//            System.out.printf("%c occurred %d times%n", currentChar, noOfOccurrence);

            // Append the encoded bits to the string code
            code.append(norrisStringBuilder(currentChar, noOfOccurrence)).append(' ');
        }

        return code.toString();
    }

    // Helper function for chuckNorrisTech()
    private static String norrisStringBuilder(char currentChar, int noOfOccurrence) {

        StringBuilder x = new StringBuilder();

        if (currentChar == '1') {
            x.append("0 ");
        } else x.append("00 ");

        x.append("0".repeat(Math.max(0, noOfOccurrence)));

//        System.out.println(x);
        return x.toString();
    }

    static void decodedResults(String s) {

        String concatedBinString = getConcatedBinString(s);
        System.out.println("Decoded string:");

        String decodedResult = binaryAsciiConverter(concatedBinString.toString());
        System.out.println(decodedResult);

    }

    private static String getConcatedBinString(String s) {
        String[] encodedList = s.split(" ");
        StringBuilder concatedBinString = new StringBuilder();

        int i = 0;
        while (i < encodedList.length - 1) {
            String bit = encodedList[i];
            String noOfOccurrenceString = encodedList[i + 1];
            int noOfTimes = countOccurrence2(noOfOccurrenceString);
//            System.out.printf("%s has occurred %d times%n", bit, noOfTimes);
            concatedBinString.append(decoder(bit, noOfTimes));

            i += 2;
        }

        return concatedBinString.toString();
    }

    private static int countOccurrence2(String s) {
        return s.length();
    }

    private static String decoder(String s, int noOfTimes) {

        String currentBit;

        if (Objects.equals(s, "0")) {
            currentBit = "1";
        } else currentBit = "0";

        return currentBit.repeat(noOfTimes);
    }

    private static String binaryAsciiConverter(String s) {

        StringBuilder concated = new StringBuilder();

        int i = 0;
        while (i < s.length()) {
            String bin = s.substring(i, i + 7);
            int x = Integer.parseInt(bin, 2);
            char c = (char) x;
//            System.out.println(c);
            concated.append(c);
            i += 7;
        }

        return concated.toString();
    }

    private static void choice() {
        System.out.println();
        Scanner in = new Scanner(System.in);

        int exit_flag = 0;
        while (exit_flag != 1) {
            System.out.println("Please input operation (encode/decode/exit):");
            String option = in.nextLine();
            switch (option) {
                case "encode" -> encode();
                case "decode" -> decode();
                case "exit" -> {
                    System.out.println("Bye!");
                    exit_flag = 1;
                }
                default -> System.out.printf("There is no '%s' operation%n", option);
            }
            System.out.println();
        }
    }

    private static void encode() {
        Scanner in = new Scanner(System.in);
        System.out.println("Input string:");
        in.useDelimiter("\n");
        String inputString = in.nextLine();
        encodedResults(inputString);
    }
    private static void decode() {
        Scanner in = new Scanner(System.in);
        System.out.println("Input encoded string:");
        in.useDelimiter("\n");
        String inputString = in.nextLine();
        if (notValid(inputString)) return;
        decodedResults(inputString);
    }

    private static boolean notValid(String inputString) {
        String[] t = inputString.split(" ");

        for (char e : inputString.toCharArray()) {
            if (e != '0' && e != ' ') {
                System.out.println("Encoded string is not valid.");
                return true;
            }
        }

        if (getConcatedBinString(inputString).length() % 7 != 0) {
            System.out.println("Encoded string is not valid.");
            return true;
        }

        if (t.length % 2 != 0) {
            System.out.println("Encoded string is not valid.");
            return true;
        }

        for (int i = 0; i < t.length; i += 2) {
            if (!Objects.equals(t[i], "0") && !Objects.equals(t[i], "00")) {
                System.out.println("Encoded string is not valid.");
                return true;
            }
        }

        return false;
    }

}