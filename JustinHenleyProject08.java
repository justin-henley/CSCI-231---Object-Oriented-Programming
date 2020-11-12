// Project 08:  Nine heads and tails
// Description: Demonstrates multidimensional arrays
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-10-23

import java.util.Scanner;

public class JustinHenleyProject08 {
    public static final int MATRIX_ROWS = 3;
    public static final int MATRIX_COLUMNS = 3;

    public static void main(String[] args) {
        // Create a new scanner object
        Scanner input = new Scanner(System.in);

        // Prompt user for a value from 0 to 511 inclusive
        int userNumber;
        // Do-while loop checks user input for valid range
        do {
            System.out.print("Enter a number between 0 and 511: ");
            userNumber = input.nextInt();
        } while (userNumber < 0 || userNumber > 511);

        // Convert userNumber to a binary String
        String userBinary = toBinary(userNumber);

        // Fill the matrix
        char[][] userMatrix = fillMatrix(userBinary);

        // Print the matrix with H for heads(0) and T for tails (1)
        printMatrix(userMatrix);
    }

    // Converts a decimal number to a binary string with enough padding to fill the matrix
    // Precondition: decimal is a non-negative decimal integer less than or equal to 2^(MATRIX_ROWS * MATRIX_COLUMNS)
    // Postcondition: returns a 0-padded binary string of length (MATRIX_ROWS * MATRIX_COLUMNS)
    public static String toBinary(int decimal) {
        // Create a string to hold the binary value
        // toBinaryString returns a non-padded binary value
        String binary = Integer.toBinaryString(decimal);

        // Check if the resulting binary string is long enough to fill the matrix
        if (binary.length() < MATRIX_COLUMNS * MATRIX_ROWS) {
            // Generate padding and append the unpadded binary, creating the padded binary string
            binary = "0".repeat((MATRIX_ROWS * MATRIX_COLUMNS) - binary.length()) + binary;
        }

        // Return the string represented by the StringBuilder
        return binary;
    }

    // Fills the matrix defined by MATRIX_ROWS x MATRIX_COLUMNS using a binary string
    // 0 in the binary string is represented as H for heads
    // 1 in the binary string is represented as T for tails
    // Most significant bit corresponds to matrix[0][0]
    // Precondition: binaryString is a binary number of length (MATRIX_ROWS * MATRIX_COLUMNS)
    // Postcondition: The returned char matrix has been filled with H's and T's to represent the specified permutation
    public static char[][] fillMatrix(String binaryString) {
        // Create a 3x3 char matrix to hold the coin combination
        char[][] matrix = new char[MATRIX_ROWS][MATRIX_COLUMNS];

        // Fill the matrix using the binary string
        // 0 is Heads, 1 is Tails

        // Iterate over the rows of the matrix
        for (int i = 0; i < MATRIX_ROWS; i++) {
            // Iterate over the columns for each row
            for (int j = 0; j < MATRIX_COLUMNS; j++) {
                // If the binary bit for the current position is 0, place H in the matrix
                if (binaryString.charAt(i * MATRIX_ROWS + j) == '0') {
                    matrix[i][j] = 'H';
                }
                // If the current binary bit is not 0, place T in the matrix
                else {
                    matrix[i][j] = 'T';
                }
            }
        }

        return matrix;
    }

    // Prints the matrix with MATRIX_ROWS rows and MATRIX_COLUMNS columns
    // Precondition: matrix has MATRIX_ROWS rows and MATRIX_COLUMNS columns and has been given values
    // Postcondition: The matrix is printed to standard output with MATRIX_ROWS rows and MATRIX_COLUMNS columns
    public static void printMatrix(char[][] matrix) {
        // Begin matrix on a new line
        System.out.println();

        // Iterate over the rows in the matrix
        for (int i = 0; i < MATRIX_ROWS; i++) {
            // Print each character in the row
            for (int j = 0; j < MATRIX_COLUMNS; j++) {
                System.out.print(matrix[i][j] + " ");
            }

            // Print a new line before the next row begins
            System.out.println();
        }
    }
}

/*
SAMPLE RUNS:

Enter a number between 0 and 511: -1
Enter a number between 0 and 511: 512
Enter a number between 0 and 511: 0

H H H
H H H
H H H

Enter a number between 0 and 511: 511

T T T
T T T
T T T

Enter a number between 0 and 511: 128

H T H
H H H
H H H

Enter a number between 0 and 511: 127

H H T
T T T
T T T

 */