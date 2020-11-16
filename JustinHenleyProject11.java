// Project 11:  Account Class
// Description: Demonstrates class and derived class creation and use, polymorphism
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-11-13

// Note:  I initially wrote CheckingAccount and SavingAccount with 2-argument constructors.
//          However, no constructors for these classes are mentioned in the UML diagram.
//          I followed the diagram, and set default values of 0 for all fields and used mutators to initialize the accounts.

import java.text.DecimalFormat;
import java.util.Random;

public class JustinHenleyProject11 {
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
                accounts[i].withdraw(amount);
                System.out.println(accounts[i].toString());

                // Attempt to deposit amount, and display result
                System.out.printf("** Depositing $%.2f to account %d...\n", amount, accounts[i].getId());
                accounts[i].deposit(amount);
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
    // Postcondition:  The Account balance has been increased by the deposit amount if positive,
    //              or remains unchanged if the amount was negative
    public void deposit(double amount) {
        // Check to make sure deposit amount is a positive number
        if (amount > 0) {
            this.balance += amount;
        }
    }

    // Attempts to withdraw the specified amount from the Account
    // Receives:    A double value representing the withdrawal amount
    // Returns:     Nothing
    abstract void withdraw(double amount);

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
    void withdraw(double amount) {
        // Only act if withdrawal amount is positive (no need to waste time withdrawing 0)
        // Only act if the withdrawal amount is less than or equal to the balance
        if (amount > 0 && amount <= this.getBalance()) {
            this.setBalance(this.getBalance() - amount);
        }
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
    void withdraw(double amount) {
        // Only act if withdrawal amount is positive (no need to waste time withdrawing 0)
        // Only act if the withdrawal amount is less than or equal to the balance
        if (amount > 0 && amount <= this.getBalance()) {
            // Calculate new balance
            double newBalance = this.getBalance() - amount;
            // If new balance is at least $500, complete withdrawal by updating balance
            if (newBalance >= 500) {
                this.setBalance(newBalance);
            }
        }
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



/*

SAMPLE RUN #1

    ------------------------------------------
    Testing Checking Account: 100, Balance: $367.0
    ------------------------------------------
    ** Withdrawing $203.10 from account 100...
    Checking Account: 100, Balance: $163.9
    ** Depositing $203.10 to account 100...
    Checking Account: 100, Balance: $367.0
    ** Withdrawing $346.12 from account 100...
    Checking Account: 100, Balance: $20.88
    ** Depositing $346.12 to account 100...
    Checking Account: 100, Balance: $367.0
    ** Withdrawing $300.99 from account 100...
    Checking Account: 100, Balance: $66.01
    ** Depositing $300.99 to account 100...
    Checking Account: 100, Balance: $367.0
    ** Withdrawing $-308.20 from account 100...
    Checking Account: 100, Balance: $367.0
    ** Depositing $-308.20 to account 100...
    Checking Account: 100, Balance: $367.0

    ------------------------------------------
    Testing Checking Account: 101, Balance: $725.0
    ------------------------------------------
    ** Withdrawing $17.27 from account 101...
    Checking Account: 101, Balance: $707.73
    ** Depositing $17.27 to account 101...
    Checking Account: 101, Balance: $725.0
    ** Withdrawing $-385.54 from account 101...
    Checking Account: 101, Balance: $725.0
    ** Depositing $-385.54 to account 101...
    Checking Account: 101, Balance: $725.0
    ** Withdrawing $-48.94 from account 101...
    Checking Account: 101, Balance: $725.0
    ** Depositing $-48.94 to account 101...
    Checking Account: 101, Balance: $725.0
    ** Withdrawing $-124.84 from account 101...
    Checking Account: 101, Balance: $725.0
    ** Depositing $-124.84 to account 101...
    Checking Account: 101, Balance: $725.0

    ------------------------------------------
    Testing Account: 102, Interest Rate: 0.015%, Balance: $154.00
    ------------------------------------------
    ** Withdrawing $95.40 from account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $154.00
    ** Depositing $95.40 to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $249.40
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $253.09
    ** Withdrawing $-228.23 from account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $253.09
    ** Depositing $-228.23 to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $253.09
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $256.84
    ** Withdrawing $135.61 from account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $256.84
    ** Depositing $135.61 to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $392.45
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $398.26
    ** Withdrawing $222.68 from account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $398.26
    ** Depositing $222.68 to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $620.93
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.015%, Balance: $630.12

    ------------------------------------------
    Testing Account: 103, Interest Rate: 0.047%, Balance: $989.00
    ------------------------------------------
    ** Withdrawing $348.64 from account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $640.36
    ** Depositing $348.64 to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $989.00
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1035.10
    ** Withdrawing $-223.87 from account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1035.10
    ** Depositing $-223.87 to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1035.10
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1083.35
    ** Withdrawing $-261.94 from account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1083.35
    ** Depositing $-261.94 to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1083.35
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1133.85
    ** Withdrawing $464.52 from account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $669.33
    ** Depositing $464.52 to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1133.85
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.047%, Balance: $1186.71

    Process finished with exit code 0
 */

/*

SAMPLE RUN #2

    ------------------------------------------
    Testing Checking Account: 100, Balance: $90.0
    ------------------------------------------
    ** Withdrawing $336.28 from account 100...
    Checking Account: 100, Balance: $90.0
    ** Depositing $336.28 to account 100...
    Checking Account: 100, Balance: $426.28
    ** Withdrawing $27.81 from account 100...
    Checking Account: 100, Balance: $398.47
    ** Depositing $27.81 to account 100...
    Checking Account: 100, Balance: $426.28
    ** Withdrawing $-189.42 from account 100...
    Checking Account: 100, Balance: $426.28
    ** Depositing $-189.42 to account 100...
    Checking Account: 100, Balance: $426.28
    ** Withdrawing $287.18 from account 100...
    Checking Account: 100, Balance: $139.1
    ** Depositing $287.18 to account 100...
    Checking Account: 100, Balance: $426.28

    ------------------------------------------
    Testing Checking Account: 101, Balance: $223.0
    ------------------------------------------
    ** Withdrawing $427.34 from account 101...
    Checking Account: 101, Balance: $223.0
    ** Depositing $427.34 to account 101...
    Checking Account: 101, Balance: $650.34
    ** Withdrawing $210.85 from account 101...
    Checking Account: 101, Balance: $439.49
    ** Depositing $210.85 to account 101...
    Checking Account: 101, Balance: $650.34
    ** Withdrawing $-45.50 from account 101...
    Checking Account: 101, Balance: $650.34
    ** Depositing $-45.50 to account 101...
    Checking Account: 101, Balance: $650.34
    ** Withdrawing $-30.09 from account 101...
    Checking Account: 101, Balance: $650.34
    ** Depositing $-30.09 to account 101...
    Checking Account: 101, Balance: $650.34

    ------------------------------------------
    Testing Account: 102, Interest Rate: 0.062%, Balance: $868.00
    ------------------------------------------
    ** Withdrawing $463.11 from account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $868.00
    ** Depositing $463.11 to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1331.11
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1413.37
    ** Withdrawing $-380.92 from account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1413.37
    ** Depositing $-380.92 to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1413.37
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1500.71
    ** Withdrawing $-459.83 from account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1500.71
    ** Depositing $-459.83 to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1500.71
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1593.45
    ** Withdrawing $478.18 from account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1115.27
    ** Depositing $478.18 to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1593.45
    Adding interest to account 102...
    Account: 102, Interest Rate: 0.062%, Balance: $1691.93

    ------------------------------------------
    Testing Account: 103, Interest Rate: 0.070%, Balance: $915.00
    ------------------------------------------
    ** Withdrawing $-59.31 from account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $915.00
    ** Depositing $-59.31 to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $915.00
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $978.92
    ** Withdrawing $-166.19 from account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $978.92
    ** Depositing $-166.19 to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $978.92
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1047.31
    ** Withdrawing $138.70 from account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $908.60
    ** Depositing $138.70 to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1047.31
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1120.47
    ** Withdrawing $-418.69 from account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1120.47
    ** Depositing $-418.69 to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1120.47
    Adding interest to account 103...
    Account: 103, Interest Rate: 0.070%, Balance: $1198.74

    Process finished with exit code 0


 */