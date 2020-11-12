// Project 04:  Char to telephone digit converter
// Author:  Justin Henley, jahenley@mail.fhsu.edu
// Date:    2020-09-16

import java.util.Scanner;

public class JustinHenleyProject04 {
    public static void main(String[] args) {
        // Create a new Scanner object for input
        Scanner input = new Scanner(System.in);

        // Prompt user for input
        System.out.print("Enter a letter: ");

        // Take input as an uppercase string, keeping the string and also storing the first character as a char
        String letterString = input.nextLine().toUpperCase();
        char letterChar = letterString.charAt(0);

        // Guard clause against illegal input
        if ( !(Character.isLetter(letterChar)) || letterString.length() != 1) {
            System.out.print("Error: Not a letter");
            return;
        }

        // Convert to digit
        int digit;  // Holds the converted telephone digit

        // Checking for which grouping letter belongs to
        // Requires that the letter has been converted to uppercase
        // Requires that letterString.length() == 1
        if (("ABC").contains(letterString))
            digit = 2;
        else if (("DEF").contains(letterString))
            digit = 3;
        else if (("GHI").contains(letterString))
            digit = 4;
        else if (("JKL").contains(letterString))
            digit = 5;
        else if (("MNO").contains(letterString))
            digit = 6;
        else if (("PQRS").contains(letterString))
            digit = 7;
        else if (("TUV").contains(letterString))
            digit = 8;
        else if (("WXYZ").contains(letterString))
            digit = 9;
        else { // Cheeky error checking to make sure my alphabet groupings did not miss any letters
            System.out.println("Error: Unreachable code reached (letter isLetter() but is not in alphabet)");
            digit = -1;
        }

        // Output the converted digit
        System.out.print(digit);

    }
}
/*
SAMPLE OUTPUT

Enter a letter: b
2

Enter a letter: Z
9

Enter a letter: $
Error: Not a letter

Enter a letter: business letter
Error: Not a letter

 */