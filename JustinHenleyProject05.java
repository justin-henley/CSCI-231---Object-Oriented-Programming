// Project 05: Mortgage Calculator
// Author:  Justin Henley, jahenley@mail.fhsu.edu
// Date:    2020-09-21

// Note: I accomplished all three extra credit problems.  Also, I could not figure out how you formatted your table so nicely!
//       Sample run is at the bottom.

import java.util.Scanner;

public class JustinHenleyProject05 {
    public static void main(String[] args) {
        // Repeat control character
        char repeatCalculations;

        // Outer do-while loop to allow repeated calculations
        do {
            // Create a new Scanner object for input
            Scanner input = new Scanner(System.in);

            // Prompt user for principal, interest rate, and term (in years)
            System.out.print("Enter the principle to borrow: $");
            double principle = input.nextDouble();
            System.out.print("Enter the interestRate in decimal: ");
            double interestRate = input.nextDouble();
            System.out.print("Enter the term (in years): ");
            int termYears = input.nextInt();

            // Set up variables for the mortgage formulas
            int n = termYears * 12;
            double r = interestRate / 12;
            double totalInterest, balance = principle, monthlyInterest, monthlyPrincipal;

            // Process monthly payment
            double monthlyPayment = (principle * r * Math.pow((1 + r), n)) / (Math.pow((1 + r), n) - 1);

            // Print out the table
            // Table header
            System.out.println("\nMonth   Payment     Principal   Interest   Total Interest  Balance");

            /* THE WHILE-LOOP VERSION
            // while-loop version
            int month = 1;

            while (month <= n) { // n is the term in months, defined above
                // Calculate the amount of the monthly payment going towards interest and paying down principle
                // Calculating the interest payment before updating the balance to figure the amount for this months payment
                monthlyInterest = balance * r;
                monthlyPrincipal = monthlyPayment - monthlyInterest;

                // Calculate balance and total interest each month
                balance = Math.pow((1 + r), month) * principle - ((Math.pow((1 + r), month) - 1) * monthlyPayment) / r;
                totalInterest = monthlyPayment * month - (principle - balance);

                // Output calculations for current month
                System.out.printf("%5d   $%.2f    $%.2f    $%.2f    $%.2f         $%.2f\n", month, monthlyPayment, monthlyPrincipal, monthlyInterest, totalInterest, balance);

                // Increment the month before looping
                month++;
            }
            END WHILE-LOOP VERSION */

            // for-loop version
            for (int month = 1; month <= n; month++) { // n is the term in months, defined above
                // Calculate the amount of the monthly payment going towards interest and paying down principle
                // Calculating the interest payment before updating the balance to figure the amount for this months payment
                monthlyInterest = balance * r;
                monthlyPrincipal = monthlyPayment - monthlyInterest;

                // Calculate balance and total interest each month
                balance = Math.pow((1 + r), month) * principle - ((Math.pow((1 + r), month) - 1) * monthlyPayment) / r;
                totalInterest = monthlyPayment * month - (principle - balance);

                // Output calculations for current month
                System.out.printf("%5d   $%.2f    $%.2f    $%.2f    $%.2f         $%.2f\n", month, monthlyPayment, monthlyPrincipal, monthlyInterest, totalInterest, balance);
            }

            // Prompt to calculate another mortgage
            System.out.print("\nEnter Y to calculate another mortgage, or N to quit: ");
            repeatCalculations = input.next().charAt(0);

        } while (repeatCalculations == 'Y' || repeatCalculations == 'y');
    }
}


/* SAMPLE RUN

Enter the principle to borrow: $50000
Enter the interestRate in decimal: 0.05
Enter the term (in years): 2

Month   Payment     Principal   Interest   Total Interest  Balance
    1   $2193.57    $1985.24    $208.33    $208.33         $48014.76
    2   $2193.57    $1993.51    $200.06    $408.39         $46021.26
    3   $2193.57    $2001.81    $191.76    $600.15         $44019.44
    4   $2193.57    $2010.16    $183.41    $783.56         $42009.29
    5   $2193.57    $2018.53    $175.04    $958.60         $39990.76
    6   $2193.57    $2026.94    $166.63    $1125.23         $37963.81
    7   $2193.57    $2035.39    $158.18    $1283.41         $35928.43
    8   $2193.57    $2043.87    $149.70    $1433.12         $33884.56
    9   $2193.57    $2052.38    $141.19    $1574.30         $31832.18
   10   $2193.57    $2060.94    $132.63    $1706.94         $29771.24
   11   $2193.57    $2069.52    $124.05    $1830.98         $27701.72
   12   $2193.57    $2078.15    $115.42    $1946.41         $25623.57
   13   $2193.57    $2086.80    $106.76    $2053.17         $23536.77
   14   $2193.57    $2095.50    $98.07    $2151.24         $21441.27
   15   $2193.57    $2104.23    $89.34    $2240.58         $19337.04
   16   $2193.57    $2113.00    $80.57    $2321.15         $17224.04
   17   $2193.57    $2121.80    $71.77    $2392.92         $15102.24
   18   $2193.57    $2130.64    $62.93    $2455.84         $12971.59
   19   $2193.57    $2139.52    $54.05    $2509.89         $10832.07
   20   $2193.57    $2148.44    $45.13    $2555.03         $8683.64
   21   $2193.57    $2157.39    $36.18    $2591.21         $6526.25
   22   $2193.57    $2166.38    $27.19    $2618.40         $4359.87
   23   $2193.57    $2175.40    $18.17    $2636.57         $2184.47
   24   $2193.57    $2184.47    $9.10    $2645.67         $-0.00

Enter Y to calculate another mortgage, or N to quit: y
Enter the principle to borrow: $100000
Enter the interestRate in decimal: .025
Enter the term (in years): 1

Month   Payment     Principal   Interest   Total Interest  Balance
    1   $8446.61    $8238.28    $208.33    $208.33         $91761.72
    2   $8446.61    $8255.44    $191.17    $399.50         $83506.28
    3   $8446.61    $8272.64    $173.97    $573.48         $75233.64
    4   $8446.61    $8289.87    $156.74    $730.21         $66943.77
    5   $8446.61    $8307.14    $139.47    $869.68         $58636.62
    6   $8446.61    $8324.45    $122.16    $991.84         $50312.17
    7   $8446.61    $8341.79    $104.82    $1096.65         $41970.38
    8   $8446.61    $8359.17    $87.44    $1184.09         $33611.20
    9   $8446.61    $8376.59    $70.02    $1254.12         $25234.62
   10   $8446.61    $8394.04    $52.57    $1306.69         $16840.58
   11   $8446.61    $8411.53    $35.08    $1341.77         $8429.05
   12   $8446.61    $8429.05    $17.56    $1359.33         $-0.00

Enter Y to calculate another mortgage, or N to quit: n

 */