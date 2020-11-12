// Project 09:  Temperature class
// Description: Demonstrates class creation and use
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-11-03

import java.util.Scanner;

public class JustinHenleyProject09 {
    // Constant to count the number of Temperature items in the arrays
    final static int NUM_OF_TEMPS = 4;

    // Tests the Temperature class
    public static void main(String[] args) {
        // Create a new Scanner instance
        Scanner input = new Scanner(System.in);

        // Create an array of four Temperature objects
        Temperature[] temperatureArray = new Temperature[NUM_OF_TEMPS];
        // Give each Temperature object the value specified in the project description
        temperatureArray[0] = new Temperature();
        temperatureArray[1] = new Temperature('C');
        temperatureArray[2] = new Temperature(0.0);
        temperatureArray[3] = new Temperature(98.6, 'F');

        // Create an array of ordinal strings for easy counting within loop
        String[] ordinalStrings = new String[4];
        ordinalStrings[0] = "first";
        ordinalStrings[1] = "second";
        ordinalStrings[2] = "third";
        ordinalStrings[3] = "fourth";

        // Loop to output each object and prompt for new temperature values
        for (int i = 0; i < NUM_OF_TEMPS; i++) {
            // Output initial values
            System.out.printf("\nThe %s Temperature is %.2f C.\n", ordinalStrings[i], temperatureArray[i].getDegreeInCelsius());
            System.out.printf("The %s Temperature is %.2f F.\n", ordinalStrings[i], temperatureArray[i].getDegreeInFahrenheit());

            // Prompt for new values
            System.out.printf("Enter the degree (a number) and the scale (F or C) of the %s Temperature.\n", ordinalStrings[i]);
            System.out.print("Degree: ");
            double userDegree = input.nextDouble();
            System.out.print("Scale: ");
            char userScale = input.next().charAt(0);

            // Push new values to object and display
            temperatureArray[i].setDegree(userDegree, userScale);
            System.out.printf("The %s Temperature is now %.2f C.\n", ordinalStrings[i], temperatureArray[i].getDegreeInCelsius());
            System.out.printf("The %s Temperature is now %.2f F.\n", ordinalStrings[i], temperatureArray[i].getDegreeInFahrenheit());
        }

        // Output new temperature values in Celsius
        System.out.print("\nIn order of creation the Temperatures in Celsius are: ");
        System.out.printf("%.2f, ", temperatureArray[0].getDegreeInCelsius());
        System.out.printf("%.2f, ", temperatureArray[1].getDegreeInCelsius());
        System.out.printf("%.2f, ", temperatureArray[2].getDegreeInCelsius());
        System.out.printf("%.2f\n", temperatureArray[3].getDegreeInCelsius());

        // Output new temperature values in Fahrenheit
        System.out.print("\nIn order of creation the Temperatures in Fahrenheit are: ");
        System.out.printf("%.2f, ", temperatureArray[0].getDegreeInFahrenheit());
        System.out.printf("%.2f, ", temperatureArray[1].getDegreeInFahrenheit());
        System.out.printf("%.2f, ", temperatureArray[2].getDegreeInFahrenheit());
        System.out.printf("%.2f\n\n", temperatureArray[3].getDegreeInFahrenheit());


        // Loops to output comparisons of the Temperature objects using their new values
        // Outer loop for the calling object in the comparison
        for (int i = 0; i < NUM_OF_TEMPS - 1; i++) {
            // Inner loop for the objects being compared to the calling object
            for (int j = i + 1; j < NUM_OF_TEMPS; j++) {
                // Formatted output for each value
                System.out.printf("The %s Temperature is%s equal to the %s.\n", ordinalStrings[i], temperatureArray[i].equals(temperatureArray[j]) ? "" : " not", ordinalStrings[j]);
                System.out.printf("The %s Temperature is%s less than the %s.\n", ordinalStrings[i], temperatureArray[i].isLessThan(temperatureArray[j]) ? "" : " not", ordinalStrings[j]);
                System.out.printf("The %s Temperature is%s greater than the %s.\n\n", ordinalStrings[i], temperatureArray[i].isGreaterThan(temperatureArray[j]) ? "" : " not", ordinalStrings[j]);
            }
        }
    }
}

class Temperature {
    // DEFAULT VALUES
    final static double DEFAULT_DEGREE = 32;
    final static char DEFAULT_SCALE = 'F';

    // DATA FIELDS
    private double degree;
    private char scale;

    // CONSTRUCTORS
    // Calls the two-arg constructor with default values for degree and scale
    public Temperature() {
        this(DEFAULT_DEGREE, DEFAULT_SCALE);
    }

    // Calls the two-arg constructor with a default value for scale
    public Temperature(double degree) {
        this(degree, DEFAULT_SCALE);
    }

    // Calls the two-arg constructor with a default value for degree
    public Temperature(char scale) {
        this(DEFAULT_DEGREE, scale);
    }

    // All other constructors call this constructor
    // Consolidates error checking within the setDegree function for simplicity
    public Temperature(double degree, char scale) {
        // Error checking for input is in the setters, so they are called here
        setDegree(degree, scale);
    }

    // METHODS
    // Returns temperature in Celsius
    // Precondition:    degree and scale have been given values
    // Postcondition:   returns degrees in Celsius
    public double getDegreeInCelsius() {
        // If scale is C, just return the degrees
        if (scale == 'C')
            return this.degree;
        else  // Convert F to C if necessary
            return (this.degree - 32) * 5 / 9;
    }

    // Returns temperature in Fahrenheit
    // Precondition:    degree and scale have been given values
    // Postcondition:   returns degrees in Fahrenheit
    public double getDegreeInFahrenheit() {
        // If scale is in F, just return the degrees
        if (scale == 'F')
            return this.degree;
        else  // Convert C to F if necessary
            return 1.8 * this.degree + 32;
    }

    // Sets the degree of the Temperature object
    // Precondition:    Degree has been given value
    // Postcondition:   The degree data field has been set
    public void setDegree(double degree) {
        setDegree(degree, this.scale);
    }

    // Sets the scale of the Temperature object
    // Precondition:    Scale has been given value
    // Postcondition:   The scale data field has been set
    public void setDegree(char scale) {
        setDegree(this.degree, scale);
    }

    // Sets the degree and scale of the Temperature object
    // Other overloads of setDegree call this version to consolidate error checking here
    // Precondition:    Degree and scale have been given values
    // Postcondition:   The degree and scale data fields have been set
    public void setDegree(double degree, char scale) {
        // Set degree
        this.degree = degree;

        // Ensure scale is uppercase
        scale = Character.toUpperCase(scale);
        // Check for valid scale
        if (isValidScale(scale))
            // Set scale if valid,
            this.scale = scale;
        else
            // Default to F for invalid scales
            this.scale = DEFAULT_SCALE;
    }

    // Precondition: temp2 is a reference to a valid Temperature object that has values
    // Postcondition:   returns true if this temperature is equal to the parameter temperature
    public boolean equals(Temperature temp2) {
        if (this.getDegreeInCelsius() == temp2.getDegreeInCelsius())
            return true;  // The two temperatures are equal
        else
            return false; // The two temperatures are not equal
    }

    // Precondition: temp2 is a reference to a valid Temperature object that has values
    // Postcondition:   returns true if this temperature is less than the parameter temperature
    public boolean isLessThan(Temperature temp2) {
        if (this.getDegreeInCelsius() < temp2.getDegreeInCelsius())
            return true;  // this is less than temp2
        else
            return false; // this is not less than temp2
    }

    // Precondition: temp2 is a reference to a valid Temperature object that has values
    // Postcondition:   returns true if this temperature is greater than the parameter temperature
    public boolean isGreaterThan(Temperature temp2) {
        if (this.getDegreeInCelsius() > temp2.getDegreeInCelsius())
            return true;  // this is greater than temp2
        else
            return false; // this is less than temp2
    }

    // Precondition: Scale has been given a value.
    // Postcondition:   Returns true if scale is F,f,C, or c.
    //                  Returns false otherwise.
    private static boolean isValidScale(char scale) {
        // Return true if scale is valid upper- or lower-case
        if (scale == 'C' || scale == 'F')
            return true;
        // Return false if invalid
        else
            return false;
    }
}


/*
TEST RUN #1

The first Temperature is 0.00 C.
The first Temperature is 32.00 F.
Enter the degree (a number) and the scale (F or C) of the first Temperature.
Degree: 77
Scale: c
The first Temperature is now 77.00 C.
The first Temperature is now 170.60 F.

The second Temperature is 32.00 C.
The second Temperature is 89.60 F.
Enter the degree (a number) and the scale (F or C) of the second Temperature.
Degree: 77
Scale: f
The second Temperature is now 25.00 C.
The second Temperature is now 77.00 F.

The third Temperature is -17.78 C.
The third Temperature is 0.00 F.
Enter the degree (a number) and the scale (F or C) of the third Temperature.
Degree: 25
Scale: c
The third Temperature is now 25.00 C.
The third Temperature is now 77.00 F.

The fourth Temperature is 37.00 C.
The fourth Temperature is 98.60 F.
Enter the degree (a number) and the scale (F or C) of the fourth Temperature.
Degree: 100
Scale: c
The fourth Temperature is now 100.00 C.
The fourth Temperature is now 212.00 F.

In order of creation the Temperatures in Celsius are: 77.00, 25.00, 25.00, 100.00

In order of creation the Temperatures in Fahrenheit are: 170.60, 77.00, 77.00, 212.00

The first Temperature is not equal to the second.
The first Temperature is not less than the second.
The first Temperature is greater than the second.

The first Temperature is not equal to the third.
The first Temperature is not less than the third.
The first Temperature is greater than the third.

The first Temperature is not equal to the fourth.
The first Temperature is less than the fourth.
The first Temperature is not greater than the fourth.

The second Temperature is equal to the third.
The second Temperature is not less than the third.
The second Temperature is not greater than the third.

The second Temperature is not equal to the fourth.
The second Temperature is less than the fourth.
The second Temperature is not greater than the fourth.

The third Temperature is not equal to the fourth.
The third Temperature is less than the fourth.
The third Temperature is not greater than the fourth.
 */

/*
TEST RUN #2

The first Temperature is 0.00 C.
The first Temperature is 32.00 F.
Enter the degree (a number) and the scale (F or C) of the first Temperature.
Degree: 35
Scale: c
The first Temperature is now 35.00 C.
The first Temperature is now 95.00 F.

The second Temperature is 32.00 C.
The second Temperature is 89.60 F.
Enter the degree (a number) and the scale (F or C) of the second Temperature.
Degree: 12
Scale: f
The second Temperature is now -11.11 C.
The second Temperature is now 12.00 F.

The third Temperature is -17.78 C.
The third Temperature is 0.00 F.
Enter the degree (a number) and the scale (F or C) of the third Temperature.
Degree: -40
Scale: c
The third Temperature is now -40.00 C.
The third Temperature is now -40.00 F.

The fourth Temperature is 37.00 C.
The fourth Temperature is 98.60 F.
Enter the degree (a number) and the scale (F or C) of the fourth Temperature.
Degree: 212
Scale: f
The fourth Temperature is now 100.00 C.
The fourth Temperature is now 212.00 F.

In order of creation the Temperatures in Celsius are: 35.00, -11.11, -40.00, 100.00

In order of creation the Temperatures in Fahrenheit are: 95.00, 12.00, -40.00, 212.00

The first Temperature is not equal to the second.
The first Temperature is not less than the second.
The first Temperature is greater than the second.

The first Temperature is not equal to the third.
The first Temperature is not less than the third.
The first Temperature is greater than the third.

The first Temperature is not equal to the fourth.
The first Temperature is less than the fourth.
The first Temperature is not greater than the fourth.

The second Temperature is not equal to the third.
The second Temperature is not less than the third.
The second Temperature is greater than the third.

The second Temperature is not equal to the fourth.
The second Temperature is less than the fourth.
The second Temperature is not greater than the fourth.

The third Temperature is not equal to the fourth.
The third Temperature is less than the fourth.
The third Temperature is not greater than the fourth.
 */