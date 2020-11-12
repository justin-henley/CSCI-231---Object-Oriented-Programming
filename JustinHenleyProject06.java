// Project 06:  Overloaded Methods
// Description: Demonstrates overloaded methods to process strings
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-10-05

// Sample run is at bottom

import java.util.Scanner;

public class JustinHenleyProject06 {

    // main to test the overloaded methods
    public static void main(String[] args) {
        // Outer loop to allow user to repeat
        char repeat = 'N';

        do {
            // New scanner object
            Scanner input = new Scanner(System.in);

            // variables for user entry
            String userString;
            char userChar;

            // Prompt and save user input
            System.out.print("Enter a string: ");
            userString = input.nextLine();
            System.out.print("Enter a char: ");
            userChar = input.nextLine().charAt(0);

            // Process and display result of function calls
            System.out.print("\nThe result of calling function int countLetters(String s) is: ");
            System.out.println(countLetters(userString));
            System.out.print("The result of calling function int countLetters(String s, char c) is: ");
            System.out.println(countLetters(userString, userChar));

            // Prompt user to repeat main loop
            System.out.print("\nDo you want to do another test? Y/N: ");
            repeat = input.nextLine().charAt(0);
        } while (repeat == 'Y' || repeat == 'y');
    }


    /*  Counts the number of English letters in the given string
        Requires:   S is a valid string (including the empty string)
        Returns:    An integer count of the number of English letters in the string
     */
    public static int countLetters(String s) {
        // counter variable for number of English letters in string
        int count = 0;

        // Iterate over each character in string and increment count if char is a letter
        for (int i = 0; i < s.length(); i++){
            if (Character.isLetter(s.charAt(i)))
                count++;
        }

        // Return the count of letters
        return count;
    }


    /*  Counts the occurrences of a given English letter (not case sensitive) in the given string
        Requires:   s is a valid string (including the empty string)
                    c is an English letter
        Returns:    If c is not an English letter, returns -1
                    If c is an English letter, returns the number of occurrences of c in s (not case sensitive)
     */
    public static int countLetters(String s, char c) {
        // early return for non-letter input for char c
        if (!Character.isLetter(c))
            return -1;

        // counter variable for occurrences of c in s
        int count = 0;
        // Change c to uppercase for easy processing
        c = Character.toUpperCase(c);

        // Iterate over each character in string s, and increment count if char c is found (non-case sensitive)
        for (int i = 0; i < s.length(); i++)
            if (Character.toUpperCase(s.charAt(i)) == c)
                count++;

        // Return count of occurrences of c in s
        return count;
    }
}


/*
Sample Run:

    Enter a string: HELLO 123!@# WORLD
    Enter a char: 2

    The result of calling function int countLetters(String s) is: 10
    The result of calling function int countLetters(String s, char c) is: -1

    Do you want to do another test? Y/N: y
    Enter a string: !@#$%12345 States
    Enter a char: s

    The result of calling function int countLetters(String s) is: 6
    The result of calling function int countLetters(String s, char c) is: 2

    Do you want to do another test? Y/N: Y
    Enter a string: What wHat What
    Enter a char: H

    The result of calling function int countLetters(String s) is: 12
    The result of calling function int countLetters(String s, char c) is: 3

    Do you want to do another test? Y/N: n
 */