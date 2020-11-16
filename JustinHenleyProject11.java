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
    Testing Checking Account #100, Balance: $58.00
    ------------------------------------------
    ** Withdrawing $-326.47 from account 100...
    Checking Account #100, Balance: $58.00
    ** Depositing $-326.47 to account 100...
    Checking Account #100, Balance: $58.00
    ** Withdrawing $315.83 from account 100...
    Checking Account #100, Balance: $58.00
    ** Depositing $315.83 to account 100...
    Checking Account #100, Balance: $373.83
    ** Withdrawing $360.30 from account 100...
    Checking Account #100, Balance: $13.53
    ** Depositing $360.30 to account 100...
    Checking Account #100, Balance: $373.83
    ** Withdrawing $10.77 from account 100...
    Checking Account #100, Balance: $363.06
    ** Depositing $10.77 to account 100...
    Checking Account #100, Balance: $373.83

    ------------------------------------------
    Testing Checking Account #101, Balance: $709.00
    ------------------------------------------
    ** Withdrawing $-179.58 from account 101...
    Checking Account #101, Balance: $709.00
    ** Depositing $-179.58 to account 101...
    Checking Account #101, Balance: $709.00
    ** Withdrawing $-150.15 from account 101...
    Checking Account #101, Balance: $709.00
    ** Depositing $-150.15 to account 101...
    Checking Account #101, Balance: $709.00
    ** Withdrawing $419.20 from account 101...
    Checking Account #101, Balance: $289.80
    ** Depositing $419.20 to account 101...
    Checking Account #101, Balance: $709.00
    ** Withdrawing $-219.20 from account 101...
    Checking Account #101, Balance: $709.00
    ** Depositing $-219.20 to account 101...
    Checking Account #101, Balance: $709.00

    ------------------------------------------
    Testing Savings Account #102, Interest Rate: 0.083%, Balance: $900.00
    ------------------------------------------
    ** Withdrawing $-318.76 from account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $900.00
    ** Depositing $-318.76 to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $900.00
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $974.42
    ** Withdrawing $123.71 from account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $850.71
    ** Depositing $123.71 to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $974.42
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1055.00
    ** Withdrawing $-2.54 from account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1055.00
    ** Depositing $-2.54 to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1055.00
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1142.24
    ** Withdrawing $113.50 from account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1028.73
    ** Depositing $113.50 to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1142.24
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.083%, Balance: $1236.69

    ------------------------------------------
    Testing Savings Account #103, Interest Rate: 0.009%, Balance: $892.00
    ------------------------------------------
    ** Withdrawing $323.02 from account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $568.98
    ** Depositing $323.02 to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $892.00
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $900.10
    ** Withdrawing $-211.78 from account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $900.10
    ** Depositing $-211.78 to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $900.10
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $908.27
    ** Withdrawing $217.51 from account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $690.75
    ** Depositing $217.51 to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $908.27
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $916.51
    ** Withdrawing $139.60 from account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $776.92
    ** Depositing $139.60 to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $916.51
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.009%, Balance: $924.83

    Process finished with exit code 0
 */

/*

SAMPLE RUN #2

    ------------------------------------------
    Testing Checking Account #100, Balance: $419.00
    ------------------------------------------
    ** Withdrawing $253.69 from account 100...
    Checking Account #100, Balance: $165.31
    ** Depositing $253.69 to account 100...
    Checking Account #100, Balance: $419.00
    ** Withdrawing $396.19 from account 100...
    Checking Account #100, Balance: $22.81
    ** Depositing $396.19 to account 100...
    Checking Account #100, Balance: $419.00
    ** Withdrawing $-347.53 from account 100...
    Checking Account #100, Balance: $419.00
    ** Depositing $-347.53 to account 100...
    Checking Account #100, Balance: $419.00
    ** Withdrawing $-383.68 from account 100...
    Checking Account #100, Balance: $419.00
    ** Depositing $-383.68 to account 100...
    Checking Account #100, Balance: $419.00

    ------------------------------------------
    Testing Checking Account #101, Balance: $734.00
    ------------------------------------------
    ** Withdrawing $-292.00 from account 101...
    Checking Account #101, Balance: $734.00
    ** Depositing $-292.00 to account 101...
    Checking Account #101, Balance: $734.00
    ** Withdrawing $148.79 from account 101...
    Checking Account #101, Balance: $585.21
    ** Depositing $148.79 to account 101...
    Checking Account #101, Balance: $734.00
    ** Withdrawing $-43.35 from account 101...
    Checking Account #101, Balance: $734.00
    ** Depositing $-43.35 to account 101...
    Checking Account #101, Balance: $734.00
    ** Withdrawing $-281.62 from account 101...
    Checking Account #101, Balance: $734.00
    ** Depositing $-281.62 to account 101...
    Checking Account #101, Balance: $734.00

    ------------------------------------------
    Testing Savings Account #102, Interest Rate: 0.044%, Balance: $652.00
    ------------------------------------------
    ** Withdrawing $-402.03 from account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $652.00
    ** Depositing $-402.03 to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $652.00
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $680.50
    ** Withdrawing $225.66 from account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $680.50
    ** Depositing $225.66 to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $906.16
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $945.77
    ** Withdrawing $-196.46 from account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $945.77
    ** Depositing $-196.46 to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $945.77
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $987.11
    ** Withdrawing $-377.48 from account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $987.11
    ** Depositing $-377.48 to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $987.11
    Adding interest to account 102...
    Savings Account #102, Interest Rate: 0.044%, Balance: $1030.25

    ------------------------------------------
    Testing Savings Account #103, Interest Rate: 0.011%, Balance: $449.00
    ------------------------------------------
    ** Withdrawing $312.94 from account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $449.00
    ** Depositing $312.94 to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $761.94
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $770.16
    ** Withdrawing $-308.11 from account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $770.16
    ** Depositing $-308.11 to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $770.16
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $778.46
    ** Withdrawing $-430.82 from account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $778.46
    ** Depositing $-430.82 to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $778.46
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $786.85
    ** Withdrawing $-117.89 from account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $786.85
    ** Depositing $-117.89 to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $786.85
    Adding interest to account 103...
    Savings Account #103, Interest Rate: 0.011%, Balance: $795.33

    Process finished with exit code 0
 */