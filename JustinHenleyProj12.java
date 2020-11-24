// Project 12:  Exception Handling
// Description: Demonstrates exception handling with the Account classes from project 11
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-11-24

// Note:  I initially wrote CheckingAccount and SavingAccount with 2-argument constructors.
//          However, no constructors for these classes are mentioned in the UML diagram.
//          I followed the diagram, and set default values of 0 for all fields and used mutators to initialize the accounts.

import java.text.DecimalFormat;
import java.util.Random;

public class JustinHenleyProj12 {
    public static void main(String[] args) {
        // Declare an array of Account, named accounts, with length 4
        Account[] accounts = new Account[4];

        // Initialize every element in accounts array.
        // The first two elements of the array should be instances of CheckingAccount
        // and second two elements should be instances of SavingsAccount.
        // The accounts should have different balances.
        accounts[0] = new CheckingAccount();
        accounts[1] = new CheckingAccount();
        accounts[2] = new SavingAccount();
        accounts[3] = new SavingAccount();

        // Create a new random number generator for test amounts
        Random rand = new Random();
        rand.nextDouble();

        // Randomly generate account balances and interest rates, and sequentially generate account ids
        for (int i = 0; i < accounts.length; i++) {
            accounts[i].setId(i + 100);
            accounts[i].setBalance(rand.nextInt(1000));

            // If the account is a SavingAccount, generate a reasonably small interest rate
            if (accounts[i] instanceof SavingAccount) {
                ((SavingAccount)accounts[i]).setInterestRate(rand.nextDouble() / 10);
            }
        }

        // Looping over the elements of the accounts array, call the deposit and withdraw methods
        // with several amounts for each account and print out the account information to see
        // the result. Ideally, this will trigger the special cases described about for the
        // withdraw methods.
        for (int i = 0; i < accounts.length; i++) {
            // Print header for each account tested
            System.out.println("\n------------------------------------------");
            System.out.println("Testing " + accounts[i].toString());
            System.out.println("------------------------------------------");

            // For each account, run multiple tests
            for (int j = 0; j < 4; j++) {
                // Generate a test amount, with a chance for negative values that should be rejected by the objects
                double amount = rand.nextDouble() + rand.nextInt(1000) - 500;

                // Attempt to withdraw amount, and display result
                System.out.printf("** Withdrawing $%.2f from account %d...\n", amount, accounts[i].getId());
                try {
                    accounts[i].withdraw(amount);
                }
                catch (IllegalAmountException | InsufficientFundsException ex) {
                    System.out.println(ex.getMessage());
                }
                // Display account information after withdrawal
                System.out.println(accounts[i].toString());

                // Attempt to deposit amount, and display result
                System.out.printf("** Depositing $%.2f to account %d...\n", amount, accounts[i].getId());
                try {
                    accounts[i].deposit(amount);
                }
                catch (IllegalAmountException ex) {
                    System.out.println(ex.getMessage());
                }
                // Display account information after withdrawal
                System.out.println(accounts[i].toString());

                // If SavingAccount, add interest and display result
                if (accounts[i] instanceof SavingAccount) {
                    System.out.printf("Adding interest to account %d...\n", accounts[i].getId());
                    ((SavingAccount) accounts[i]).addInterest();
                    System.out.println(((SavingAccount)accounts[i]).toString());
                }

            }

        }
    }
}

abstract class Account {
    // Data fields
    private int id = 0;
    private double balance = 0;

    // Constructors
    Account() {}

    Account(int id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    // Methods

    // Sets the balance of the Account
    // Receives:    A double representing the new balance of the Account
    // Returns:     Nothing
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Gets the balance of the Account
    // Receives:    Nothing
    // Returns:     A double representing the current balance of the account
    public double getBalance() {
        return this.balance;
    }

    // Sets the ID of the Account
    // Receives:    An integer representing the account number
    // Returns:     Nothing
    public void setId(int id) {
        this.id = id;
    }

    // Retrieves the ID number of the Account
    // Receives:    Nothing
    // Returns:     An integer representing the ID number of the Account
    public int getId() {
        return this.id;
    }

    // Attempts to deposit the specified amount into the Account, if the amount is positive
    // Receives:    A double value representing the deposit amount
    // Returns:     Nothing
    // Postcondition:  The Account balance has been increased by the deposit amount if positive
    // Throws:      IllegalAmountException if amount is less than 0
    public void deposit(double amount) throws IllegalAmountException {
        // Check to make sure deposit amount is a positive number
        if (amount < 0) {
            throw new IllegalAmountException("Deposit amount cannot be a negative value", amount);
        }
        // Add amount to balance
        this.balance += amount;
    }

    // Attempts to withdraw the specified amount from the Account
    // Receives:    A double value representing the withdrawal amount
    // Returns:     Nothing
    abstract void withdraw(double amount) throws IllegalAmountException, InsufficientFundsException;

    // Prints out the account information
    // Receives:    Nothing
    // Returns:     A string detailing the account id and balance
    public String toString() {
        return "Account #" + this.id + ", Balance: $" + this.balance;
    }
}

class CheckingAccount extends Account {
    // Constructors
    // The UML diagram did not call for explicit constructors for this class

    // Methods

    // Attempts to withdraw the specified amount from the CheckingAccount
    // Receives:    A double value representing the withdrawal amount
    // Returns:     Nothing
    // Postcondition:   If amount is non-negative and <= the current balance, the balance has been reduced by amount
    //                  Else, balance remains unchanged.
    @Override
    void withdraw(double amount) throws IllegalAmountException, InsufficientFundsException {
        // Only act if withdrawal amount is non-negative
        if (amount < 0) {
            throw new IllegalAmountException("Withdrawal amount cannot be negative", amount);
        }
        if (amount > this.getBalance()) {
            throw new InsufficientFundsException("Withdrawal amount exceeds account balance", amount);
        }
        // Only act if the withdrawal amount is less than or equal to the balance
        this.setBalance(this.getBalance() - amount);
    }

    // Prints out the CheckingAccount information
    // Receives:    Nothing
    // Returns:     A string detailing the account id and balance
    @Override
    public String toString() {
        // Round the balance to 2 decimal places
        String roundedBalance = new DecimalFormat("#.00").format(getBalance());
        return "Checking Account #" + getId() + ", Balance: $" + roundedBalance;
    }
}

class SavingAccount extends Account {

    // Data fields
    // Default to 0, no other reasonable default
    private double interestRate = 0;

    // Constructors
    // The UML diagram did not call for explicit constructors for this class

    // Methods

    // Sets the interest rate of the SavingAccount
    // Receives:    A double value representing the interest rate
    // Returns:     Nothing
    // Postcondition:   The interestRate has been set to the specified rate
    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    // Retrieves the interest rate of the SavingAccount
    // Receives:    Nothing
    // Returns:     A double value representing the current interest rate
    public double getInterestRate() {
        return this.interestRate;
    }

    // Attempts to withdraw the specified amount from the SavingAccount
    // Receives:    A double value representing the withdrawal amount
    // Returns:     Nothing
    // Postcondition:   If amount is non-negative and <= the current balance, the balance has been reduced by amount
    //                  Else, balance remains unchanged.
    @Override
    void withdraw(double amount) throws IllegalAmountException, InsufficientFundsException {
        // Only act if withdrawal amount is non-negative
        if (amount < 0) {
            throw new IllegalAmountException("Withdrawal amount cannot be negative", amount);
        }
        if (amount > this.getBalance() - 500) {
            throw new InsufficientFundsException("Withdrawal amount exceeds balance eligible for withdrawal", amount);
        }
        // Only act if the withdrawal amount is less than or equal to the balance
        this.setBalance(this.getBalance() - amount);
    }

    // Adds the interest at the set interest rate to the account balance
    // Receives:    Nothing
    // Returns:     Nothing
    // Postcondition:   The balance has increased by balance*interestRate
    public void addInterest() {
        this.setBalance(this.getBalance() * (1 + interestRate));
    }

    // Prints out the account information
    // Receives:    Nothing
    // Returns:     A string detailing the account id, balance, and interest rate
    @Override
    public String toString() {
        // Format the balance and interest rates to display with an appropriate number of decimal places
        String roundedBalance = new DecimalFormat("#.00").format(getBalance());
        String roundedInterest = new DecimalFormat("0.000").format(getInterestRate());

        // Build the return string
        return "Savings Account #" + getId() + ", Interest Rate: " + roundedInterest + "%, Balance: $" + roundedBalance;
    }
}

// Provides an exception class for illegal amounts entered in banking operations
class IllegalAmountException extends Exception {
    // Store the illegal value for inspection if desired
    public double amount = 0;

    IllegalAmountException() {
        super();
    }
    IllegalAmountException(String s, double amount) {
        super(s);
        this.amount = amount;
    }
}

// Provides an exception class for withdrawal amounts that exceed balance eligible for withdrawal
class InsufficientFundsException extends Exception {
    // Store the amount requested
    public double amount = 0;

    InsufficientFundsException() {
        super();
    }
    InsufficientFundsException(String s, double amount) {
        super(s);
        this.amount = amount;
    }
}

/*
SAMPLE RUN 1

------------------------------------------
Testing Checking Account #100, Balance: $971.00
------------------------------------------
** Withdrawing $-248.70 from account 100...
Withdrawal amount cannot be negative
Checking Account #100, Balance: $971.00
** Depositing $-248.70 to account 100...
Deposit amount cannot be a negative value
Checking Account #100, Balance: $971.00
** Withdrawing $-347.10 from account 100...
Withdrawal amount cannot be negative
Checking Account #100, Balance: $971.00
** Depositing $-347.10 to account 100...
Deposit amount cannot be a negative value
Checking Account #100, Balance: $971.00
** Withdrawing $-181.30 from account 100...
Withdrawal amount cannot be negative
Checking Account #100, Balance: $971.00
** Depositing $-181.30 to account 100...
Deposit amount cannot be a negative value
Checking Account #100, Balance: $971.00
** Withdrawing $-309.53 from account 100...
Withdrawal amount cannot be negative
Checking Account #100, Balance: $971.00
** Depositing $-309.53 to account 100...
Deposit amount cannot be a negative value
Checking Account #100, Balance: $971.00

------------------------------------------
Testing Checking Account #101, Balance: $803.00
------------------------------------------
** Withdrawing $40.03 from account 101...
Checking Account #101, Balance: $762.97
** Depositing $40.03 to account 101...
Checking Account #101, Balance: $803.00
** Withdrawing $232.91 from account 101...
Checking Account #101, Balance: $570.09
** Depositing $232.91 to account 101...
Checking Account #101, Balance: $803.00
** Withdrawing $-155.86 from account 101...
Withdrawal amount cannot be negative
Checking Account #101, Balance: $803.00
** Depositing $-155.86 to account 101...
Deposit amount cannot be a negative value
Checking Account #101, Balance: $803.00
** Withdrawing $135.71 from account 101...
Checking Account #101, Balance: $667.29
** Depositing $135.71 to account 101...
Checking Account #101, Balance: $803.00

------------------------------------------
Testing Savings Account #102, Interest Rate: 0.093%, Balance: $851.00
------------------------------------------
** Withdrawing $-222.53 from account 102...
Withdrawal amount cannot be negative
Savings Account #102, Interest Rate: 0.093%, Balance: $851.00
** Depositing $-222.53 to account 102...
Deposit amount cannot be a negative value
Savings Account #102, Interest Rate: 0.093%, Balance: $851.00
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $929.92
** Withdrawing $145.52 from account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $784.40
** Depositing $145.52 to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $929.92
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1016.15
** Withdrawing $389.48 from account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $626.67
** Depositing $389.48 to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1016.15
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1110.38
** Withdrawing $3.72 from account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1106.66
** Depositing $3.72 to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1110.38
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.093%, Balance: $1213.35

------------------------------------------
Testing Savings Account #103, Interest Rate: 0.043%, Balance: $831.00
------------------------------------------
** Withdrawing $157.24 from account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $673.76
** Depositing $157.24 to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $831.00
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $866.67
** Withdrawing $11.65 from account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $855.02
** Depositing $11.65 to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $866.67
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $903.87
** Withdrawing $-370.81 from account 103...
Withdrawal amount cannot be negative
Savings Account #103, Interest Rate: 0.043%, Balance: $903.87
** Depositing $-370.81 to account 103...
Deposit amount cannot be a negative value
Savings Account #103, Interest Rate: 0.043%, Balance: $903.87
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $942.66
** Withdrawing $170.37 from account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $772.29
** Depositing $170.37 to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $942.66
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.043%, Balance: $983.12

END SAMPLE RUN 1
 */

/*
SAMPLE RUN 2

------------------------------------------
Testing Checking Account #100, Balance: $888.00
------------------------------------------
** Withdrawing $399.52 from account 100...
Checking Account #100, Balance: $488.48
** Depositing $399.52 to account 100...
Checking Account #100, Balance: $888.00
** Withdrawing $185.58 from account 100...
Checking Account #100, Balance: $702.42
** Depositing $185.58 to account 100...
Checking Account #100, Balance: $888.00
** Withdrawing $414.09 from account 100...
Checking Account #100, Balance: $473.91
** Depositing $414.09 to account 100...
Checking Account #100, Balance: $888.00
** Withdrawing $-54.13 from account 100...
Withdrawal amount cannot be negative
Checking Account #100, Balance: $888.00
** Depositing $-54.13 to account 100...
Deposit amount cannot be a negative value
Checking Account #100, Balance: $888.00

------------------------------------------
Testing Checking Account #101, Balance: $51.00
------------------------------------------
** Withdrawing $131.78 from account 101...
Withdrawal amount exceeds account balance
Checking Account #101, Balance: $51.00
** Depositing $131.78 to account 101...
Checking Account #101, Balance: $182.78
** Withdrawing $172.99 from account 101...
Checking Account #101, Balance: $9.79
** Depositing $172.99 to account 101...
Checking Account #101, Balance: $182.78
** Withdrawing $-246.34 from account 101...
Withdrawal amount cannot be negative
Checking Account #101, Balance: $182.78
** Depositing $-246.34 to account 101...
Deposit amount cannot be a negative value
Checking Account #101, Balance: $182.78
** Withdrawing $446.32 from account 101...
Withdrawal amount exceeds account balance
Checking Account #101, Balance: $182.78
** Depositing $446.32 to account 101...
Checking Account #101, Balance: $629.09

------------------------------------------
Testing Savings Account #102, Interest Rate: 0.056%, Balance: $273.00
------------------------------------------
** Withdrawing $42.92 from account 102...
Withdrawal amount exceeds balance eligible for withdrawal
Savings Account #102, Interest Rate: 0.056%, Balance: $273.00
** Depositing $42.92 to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $315.92
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $333.49
** Withdrawing $226.51 from account 102...
Withdrawal amount exceeds balance eligible for withdrawal
Savings Account #102, Interest Rate: 0.056%, Balance: $333.49
** Depositing $226.51 to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $560.01
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $591.15
** Withdrawing $-47.42 from account 102...
Withdrawal amount cannot be negative
Savings Account #102, Interest Rate: 0.056%, Balance: $591.15
** Depositing $-47.42 to account 102...
Deposit amount cannot be a negative value
Savings Account #102, Interest Rate: 0.056%, Balance: $591.15
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $624.02
** Withdrawing $-492.09 from account 102...
Withdrawal amount cannot be negative
Savings Account #102, Interest Rate: 0.056%, Balance: $624.02
** Depositing $-492.09 to account 102...
Deposit amount cannot be a negative value
Savings Account #102, Interest Rate: 0.056%, Balance: $624.02
Adding interest to account 102...
Savings Account #102, Interest Rate: 0.056%, Balance: $658.73

------------------------------------------
Testing Savings Account #103, Interest Rate: 0.082%, Balance: $412.00
------------------------------------------
** Withdrawing $-64.03 from account 103...
Withdrawal amount cannot be negative
Savings Account #103, Interest Rate: 0.082%, Balance: $412.00
** Depositing $-64.03 to account 103...
Deposit amount cannot be a negative value
Savings Account #103, Interest Rate: 0.082%, Balance: $412.00
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $445.72
** Withdrawing $479.42 from account 103...
Withdrawal amount exceeds balance eligible for withdrawal
Savings Account #103, Interest Rate: 0.082%, Balance: $445.72
** Depositing $479.42 to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $925.14
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $1000.86
** Withdrawing $-2.71 from account 103...
Withdrawal amount cannot be negative
Savings Account #103, Interest Rate: 0.082%, Balance: $1000.86
** Depositing $-2.71 to account 103...
Deposit amount cannot be a negative value
Savings Account #103, Interest Rate: 0.082%, Balance: $1000.86
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $1082.77
** Withdrawing $41.14 from account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $1041.64
** Depositing $41.14 to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $1082.77
Adding interest to account 103...
Savings Account #103, Interest Rate: 0.082%, Balance: $1171.39

END SAMPLE RUN 2
 */
