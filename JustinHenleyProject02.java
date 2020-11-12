// Project 02:  Change converter. Takes an amount of change between 1 and 99, and outputs the number of each coin needed
//              to most efficiently make that change.
// Author:  Justin Henley, jahenley@mail.fhsu.edu
// Date:    2020-08-25

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class JustinHenleyProject02 {
    public static void main(String[] args) {
        // Declare a new Scanner object for input
        Scanner input = new Scanner(System.in);

        // Variables used within the program
        int change, quarters, dimes, nickels, pennies;

        // Prompt user for an amount of change
        // System.out.println("Enter an amount of change between 1 and 99 cents");
        // change = input.nextInt();

        // Randomly generate an amount of change between 1 and 99 cents
        change = ThreadLocalRandom.current().nextInt(1, 100);

        // Begin to print output before modifying the change variable
        System.out.println("You have " + change + " cents. That is:");

        // Process the change into different categories
        quarters = change /  25;
        dimes = (change %= 25) / 10;
        nickels = (change %= 10) / 5;
        pennies = (change %= 5);

        // Finish printing the output number of each coin
        System.out.println(quarters + " quarters");
        System.out.println(dimes + " dimes");
        System.out.println(nickels + " nickels");
        System.out.println(pennies + " pennies");

    }
}
