// Project 03:  Score to Letter Grade converter
// Author:  Justin Henley, jahenley@mail.fhsu.edu
// Date:    2020-09-04

import java.util.Scanner;

public class JustinHenleyProject03 {
    public static void main(String[] args) {
        // Create a scanner instance
        Scanner input = new Scanner(System.in);

        // Variables used within the program
        double score;
        String letterGrade;

        // Prompt user for score
        System.out.print("Enter your score (0 -- 100): ");
        score = input.nextDouble();

        /* IF AND IF_ELSE VERSION
        // Checks for scores outside of valid range
        if (score > 100 || score < 0) {
            System.out.print("INVALID SCORE ENTERED");
            return;
        }
        // If-else for deciding letter grade
        if (score >= 90) {
            letterGrade = "A";
        }
        else if (score >= 80) {
            letterGrade = "B";
        }
        else if (score >= 70) {
            letterGrade = "C";
        }
        else if (score >= 60) {
            letterGrade = "D";
        }
        else {
            letterGrade = "F";
        }

        END IF AND IF_ELSE VERSION */

        // Switch version

        // Convert score to only the tens place for easy switching
        int tensScore = (int) score / 10;

        // Switch based on converted score
        switch (tensScore) {

            case 9:
                letterGrade = "A";
                break;
            case 8:
                letterGrade = "B";
                break;
            case 7:
                letterGrade = "C";
                break;
            case 6:
                letterGrade = "D";
                break;
            case 5:
            case 4:
            case 3:  // Fall through for all "F" values
            case 2:  // Saves the default for non-valid scores
            case 1:  // Scores between 0 and 9 are handled specially in the default case
                letterGrade = "F";
                break;
            default:
                // Special case for score of 100
                // Couldn't handle with "case 10:" because it would also recognize up to score = 109
                if (score == 100) {
                    letterGrade = "A";
                }
                // Special handling for (0 <= score < 10)
                // The integer division trick for the switch condition causes values from -1 to -9 to evaluate to 0
                else if (tensScore == 0 && score >= 0) {
                    letterGrade = "F";
                }
                // Otherwise, the score is invalid
                else {
                    System.out.print("INVALID SCORE ENTERED");
                    return;
                }
        }

        // Output letter grade
        System.out.print("Your grade is: " + letterGrade);

    }
}


/*
SAMPLE OUTPUT

RUN 1:
    Enter your score (0 -- 100): 93
    Your grade is: A

RUN 2:
    Enter your score (0 -- 100): 83
    Your grade is: B

RUN 3:
    Enter your score (0 -- 100): 0
    Your grade is: F

RUN 4:
    Enter your score (0 -- 100): 5
    Your grade is: F

RUN 5:
    Enter your score (0 -- 100): 101
    INVALID SCORE ENTERED

RUN 6:
    Enter your score (0 -- 100): -1
    INVALID SCORE ENTERED

END SAMPLE OUTPUT
 */