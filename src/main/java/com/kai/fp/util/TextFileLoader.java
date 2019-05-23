package com.kai.fp.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple exercise used to practice using javadoc as well as writing a class for use by myself.
 * Condenses .txt file loading throughout my (Kai Tinkess) programs into a single class.
 * Uses InputStreamReader for the most part.
 * I understand most of these functions already exist in java, I would just like to practice them for myself.
 * Currently using .getResourceAsStream(), so please put the text file in the same directory
 *  as this file, or in a lower one.
 *
 * @author Kai Tinkess
 * @version 0.1.2, 08/24/18
 * @since 1.7
 */



public class TextFileLoader {


    /**
     * Public methods.
     * For more detail on each method, read the documentation on the private methods
     *
     * In the return statement, I am giving a reader (in this case an input stream reader) with a file attached to it
     *  (in this case the .txt file converted into an InputStream) to the internal method
     *
     * @param fileName the name of the resource used
     * @return various lists/strings/ints.
     * @throws IOException in the case of the file not being able to be loaded
     * @throws NoTextFileException if the passed in string does not end in .txt or is null
     * @throws FileCouldNotBeLocatedException if the getResourceAsStream call returns null
     */

    //TODO: Clean up try/catch blocks

    public static List<String> readLines(String fileName) throws IOException {
        if (checkIfTextFile(fileName)) {
            return internalReadLines((loadInputStreamReaderForFile(fileName)));
        }
        return null;
    }

    public static List<String> readCharacters(String fileName) throws IOException {
        if (checkIfTextFile(fileName)) {
            return internalReadCharacters((loadInputStreamReaderForFile(fileName)));
        }
        return null;
    }

    public static List<List<String>> get2DStringArray(String fileName) throws IOException {
        if (checkIfTextFile(fileName)) {
            return internal2DStringArray((loadInputStreamReaderForFile(fileName)));
        }
        return null;
    }

    public static String getFullString(String fileName) throws IOException {
        if (checkIfTextFile(fileName)) {
            return internalGetFullString((loadInputStreamReaderForFile(fileName)));
        }
        return null;
    }

    public static String getFullString(String fileName, String lineSeparator) throws IOException {
        if (checkIfTextFile(fileName)) {
            return internalGetFullString((loadInputStreamReaderForFile(fileName)), lineSeparator);
        }
        return null;
    }

    /**
     * Two methods that return the length of the file in either characters or lines
     *
     * @param fileName the name of the file
     * @return int the length in either lines or characters
     */

    public static int lineLength(String fileName) throws IOException {
        return readLines(fileName).size();
    }

    public static int charLength(String fileName) throws IOException {
        return readCharacters(fileName).size();
    }

    /**
     * Method that checks if the passed in string ends in null and ends in .txt
     *
     * @param s the string passed in by the user
     * @return Boolean returns true if the String ends in .txt and is not equal to null
     * @throws IOException if it doesn't end in .txt or is null
     */

    private static boolean checkIfTextFile(String s) throws IOException {
        if (s.equals(null)) { throw new IOException(); }
        if (!s.substring(s.length()-4).equals(".txt")) { throw new IOException(); }
        return true;
    }

    /**
     * Returns an ArrayList of the lines in the text file.
     *
     * @param toRead the reader used to read through the file. allows for different subclasses
     * @return List<String> a list of all lines in the file
     * @throws IOException if the file cannot be read due to an error
     */

    private static List<String> internalReadLines(Reader toRead) throws IOException {
        try (BufferedReader reader = new BufferedReader(toRead)){
            List<String> result = new ArrayList<>();
            while(true) {
                String line = reader.readLine();
                if (line == null)
                    break;
                result.add(line);
            }
            return result;
        }
    }

    /**
     * Returns an ArrayList of Strings of characters in the text file
     *
     * @param toRead the reader used to read through the file. normally will be inputStreamReader
     * @return List<String> a list of all characters throughout the file
     * @throws IOException if the file cannot be read
     */

    private static List<String> internalReadCharacters(Reader toRead) throws IOException {
        List<String> chars = new ArrayList<>();
        for (String t: internalReadLines(toRead)) {
            for (String l: t.split("")) {
                chars.add(l);
            }
        }
        return chars;
    }

    /**
     * Returns a 2D ArrayList of every character, organized by lines
     *
     * For example, if hello.txt contained:
     * "Hello
     *  World"
     * Printing the result of this method with hello.txt as the argument would produce:
     * [H, e, l, l, o]
     * [W, o, r, l, d]
     *
     * @param toRead the reader used
     * @return List<ArrayList<String>> a 2d arrayList of every character in the file
     * @throws IOException if the file cannot be read
     */

    private static List<List<String>> internal2DStringArray(Reader toRead) throws IOException {
        List<List<String>> stringArray = new ArrayList<List<String>>();
        for (String t: internalReadLines(toRead)) {
            List<String> currentLine = new ArrayList<String>();
            for (String s: t.split("")) {
                currentLine.add(s);
            }
            stringArray.add(currentLine);
        }
        return stringArray;
    }

    /**
     * Returns the full text file as a single string
     *
     * @param toRead the reader used
     * @return String the entire file as a string
     * @throws IOException if there is an error with reading the file
     */

    private static String internalGetFullString(Reader toRead) throws IOException {
        return internalGetFullString(toRead, "");
    }

    /**
     * Returns the full text as a single string with a string inserted between every line
     *
     * @param toRead the reader used
     * @param lineSeparator a string that will be inserted between every line in the full string (such as a \n or space)
     * @return String the entire file as a string
     * @throws IOException if there is an error with reading the file
     */

    private static String internalGetFullString(Reader toRead, String lineSeparator) throws IOException {
        String fullString = "";
        for (String t: internalReadLines(toRead)) { fullString += (t + lineSeparator); }
        return fullString;
    }

    /**
     * Loads the file as an input stream object (for use inside this class)
     *
     * @param fileName the name of the file
     * @return InputStream the InputStream of the file (if it's in the class path)
     * @throws IOException if getResourceAsStream() call return null
     */

    private static InputStreamReader loadInputStreamReaderForFile(String fileName) throws IOException {
        try {
            return new InputStreamReader(TextFileLoader.class.getResourceAsStream(fileName));
        } catch (NullPointerException e) { throw new IOException(); }
    }


}
