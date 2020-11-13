// Project 09:  MyString Class
// Description: Demonstrates class creation and use
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-11-13

import java.util.Arrays;

public class JustinHenleyProject10 {
    static MyString s1, s2, s3, s4;
    static void setup() {
        char[] c1 = {'h', 'e', 'l', 'l', 'o'};
        char[] c2 = {'h', 'e', 'l'};
        char[] c3 = {'H'};
        char[] c4 = {};
        s1 = new MyString(c1);
        s2 = new MyString(c2);
        s3 = new MyString(c3);
        s4 = new MyString(c4);
    }

    static void assertTrue(boolean b) {
        if (!b) {
            throw new AssertionError();
        }
    }

    static void assertFalse(boolean b) {
        if (b) {
            throw new AssertionError();
        }
    }

    static void testCharAt() {
        assertTrue(s1.charAt(0) == 'h');
        assertTrue(s2.charAt(2) == 'l');
    }

    static void testCompareTo() {
        assertTrue(s1.compareTo(s2) == 2);
        assertTrue(s2.compareTo(s1) == -2);
        assertTrue(s3.compareTo(s1) == 'H' - 'h');
        assertTrue(s1.compareTo(s3) == 'h' - 'H');
        assertTrue(s1.compareTo(s4) == 5);
        assertTrue(s4.compareTo(s1) == -5);
        assertTrue(s1.compareTo(s1) == 0);
        assertTrue(s4.compareTo(s4) == 0);
    }

    static void testEquals() {
        Integer a = new Integer(123);
        char c[] = {'h', 'e', 'l'};
        MyString s = new MyString(c);
        assertTrue(s.equals(s2));
        assertFalse(s1.equals(s2));
        assertFalse(s3.equals(a));
    }

    static void testEqualsIgnoreCase() {
        char c[] = {'H', 'E', 'L'};
        assertTrue(s2.equalsIgnoreCase(new MyString(c)));
        assertFalse(s1.equalsIgnoreCase(s2));
    }

    static void testLength() {
        assertTrue(s1.length() == 5);
        assertTrue(s2.length() == 3);
        assertTrue(s3.length() == 1);
        assertTrue(s4.length() == 0);
    }

    static void testOutput() {
        System.out.print("s1: ");
        s1.output();
        System.out.print("\ns2: ");
        s2.output();
        System.out.print("\n-1029384756 passed as integer: ");
        MyString.valueOf(-1029384756).output();
        System.out.println();
    }

    static void testSubstring() {
        assertTrue(s1.substring(0, 3).equals(s2));
        assertTrue(s1.substring(0, 1).equalsIgnoreCase(s3));
        assertFalse(s1.substring(2).equals(s2));
    }

    static void testToLowerCase() {
        char [] upper = {'H', 'E', 'L'};
        MyString s = new MyString(upper);
        assertTrue(s.toLowerCase().equals(s2));
    }

    static void testToUpperCase() {
        char [] upper = {'H', 'E', 'L'};
        MyString s = new MyString(upper);
        assertTrue(s2.toUpperCase().equals(s));
    }

    public static void main(String[] args) {
        System.out.println("\nRunning tests...\n");

        // Instead of writing all tests to main, I took advantage of the ad-hoc testing
        // you created in the starter code to create tests for all functions.
        // The exceptions thrown on failure are not caught, and will interrupt execution.
        setup();
        testCharAt();
        testCompareTo();
        testEquals();
        testEqualsIgnoreCase();
        testLength();
        testOutput();
        testSubstring();
        testToLowerCase();
        testToUpperCase();

        System.out.println("\nAll tests complete.");
    }
}

class MyString implements Comparable<MyString> {
    // Data field
    char[] arr;

    // Constructor
    public MyString(char[] arr) {
        this.arr = arr;
    }

    // Methods

    // A method for returning a single character from MyString at the specified index
    // Requires: int i is within the range of this MyString.arr, else an IndecOutOfBounds exception is thrown
    // Receives: An integer i representing an index location within this.arr
    // Returns:  The char at arr[i]
    public char charAt(int i) {
        // Check that i is in range for MyString
        if (i < 0 || i > arr.length - 1)
            throw new IndexOutOfBoundsException();

        // If in valid range, return the character
        return arr[i];
    }

    // Compares to MyString instances lexicographically
    // Receives:    A MyString object to compare to this
    // Returns:     If both are exactly the same, returns 0
    //              If both are identical within the range shared by both, returns this.length() - s.length()
    //              If a difference is encountered at some index k within the shared range, returns this.charAt(k) - s.charAt(k)
    public int compareTo(MyString s) {
        // If both MyStrings are equal, return 0
        if (this.equals(s)) {
            return 0;
        }

        // If not equal, find first character different, and return the value:
        //      this.charAt(k)-anotherString.charAt(k), where k is  the index of the lowest difference
        for (int i = 0; i < this.length() && i < s.length(); i++) {
            if (this.arr[i] != s.arr[i]) {
                return this.arr[i] - s.arr[i];
            }
        }

        // If the code reaches this point, the two strings are identical within the range shared by both
        // Thus, one must be shorter than the other, return:
        //      this.length()-anotherString.length()
        return this.length() - s.length();
    }

    // Tests if the two objects have identical contents
    // Receives:    An Object
    // Returns:     If o is not an instance of MyString, returns false
    //              If o is an instance of MyString that does not have the same contents as this, returns false
    //              If o has the same contents as this, returns true
    public boolean equals(Object o) {
        // Check for non-MyString Objects, which are by definition not equal
        if (!(o instanceof MyString)) {
            return false;
        }

        // Now that o is known to be of type MyString, cast to a MyString variable for easy manipulation
        MyString s = (MyString) o;

        // If lengths are not the same, cannot be equal
        if (this.length() != s.length()) {
            return false;
        }

        // If lengths are same, compare the two
        for (int i = 0; i < this.length(); i++) {
            if (this.arr[i] != s.arr[i]) {
                return false;
            }
        }

        // If the function has not yet returned, then the two must be equal
        return true;
    }

    // Tests if the two objects have identical contents without regards for case
    // Receives:    An Object
    // Returns:     If o is not an instance of MyString, returns false
    //              If o is an instance of MyString that does not have the same (case-insensitive) contents as this, returns false
    //              If o has the same contents as this without regards to case, returns true
    public boolean equalsIgnoreCase(MyString s) {
        // If lengths are not the same, cannot be equal
        if (this.length() != s.length()) {
            return false;
        }

        // If lengths are same, compare the two
        for (int i = 0; i < this.length(); i++) {
            if (Character.toUpperCase(this.arr[i]) != Character.toUpperCase(s.arr[i])) {
                return false;
            }
        }

        // If the function has not yet returned, then the two must be equal
        return true;
    }

    // Returns the length of the internal char array representing the string
    // Receives:    Nothing
    // Returns:     The length of the internal char array
    public int length() {
        return arr.length;
    }

    // Displays the string to standard output without whitespace padding or newlines
    public void output() {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
    }

    // Returns a substring from the given index to the end of the string
    // Requires:    begin is within the range (0, this.length() )
    // Receives:    An index into the internal char array
    // Returns:     A MyString instance containing the substring from (begin, this.length]
    public MyString substring(int begin) {
        // Generate the subarray int the range [begin, arr.length)
        // Will throw exception if begin is out of range (o, this.length())
        char[] sub = Arrays.copyOfRange(arr, begin, arr.length);
        // Return a new MyString initialized to the subarray
        return new MyString(sub);
    }

    // Returns a substring from the given begin index to the end index
    // Requires:    begin is within the range (0, this.length() ), end > begin
    // Receives:    Two indexes into the internal char array
    // Returns:     A MyString instance containing the substring from (begin, end]
    public MyString substring(int begin, int end) {
        // Generate the subarray int the range [begin, end)
        char[] sub = Arrays.copyOfRange(arr, begin, end);
        // Return a new MyString initialized to the subarray
        return new MyString(sub);
    }

    // Converts this MyString to all lower case
    // Returns: A new instance of MyString, containing the string in all lower case
    public MyString toLowerCase() {
        // Create a copy of the MyString array
        char[] copy = Arrays.copyOf(arr, arr.length);

        // Iterate over each char in copy array
        for (int i = 0; i < copy.length; i++) {
            // Check for uppercase letters
            if (Character.isUpperCase(copy[i])) {
                // Convert to lowercase
                copy[i] = Character.toLowerCase(copy[i]);
            }
        }

        // Return the now-lowercase copy
        return new MyString(copy);
    }

    // Converts this MyString to all upper case
    // Returns: A new instance of MyString, containing the string in all upper case
    public MyString toUpperCase() {
        // Create a copy of the MyString array
        char[] copy = Arrays.copyOf(arr, arr.length);

        // Iterate over each char in copy array
        for (int i = 0; i < copy.length; i++) {
            // Check for lowercase letters
            if (Character.isLowerCase(copy[i])) {
                // Convert to uppercase
                copy[i] = Character.toUpperCase(copy[i]);
            }
        }

        // Return the now-uppercase copy
        return new MyString(copy);
    }

    // Converts a 32-bit signed integer into a string
    // Receives:    A 32-bit signed integer
    // Returns:     The string representation of the integer, without padding
    public static MyString valueOf(int i) {
        // Max length of a 32-bit signed integer is 10 digits plus sign
        final int MAX_LENGTH = 11;
        char[] num = new char[MAX_LENGTH];
        boolean isNegative = false;

        // Early return for i = 0;
        if (i == 0) {
            char[] zero = {'0'};
            return new MyString(zero);
        }

        // Check for negative value, store the sign, and switch i to positive for processing
        if (i < 0) {
            isNegative = true;
            i *= -1;
        }

        // Process i from least- to most-significant digit
        int iter;  // Needs to be accessed after for loop to set sign
        for (iter = MAX_LENGTH - 1; iter >= 0 && i > 0; iter-- ) {
            // Convert least significant remaining digit into a character
            num[iter] = Character.forDigit(i % 10, 10);
            // Remove least significant digit from i
            i /= 10;
        }

        // If i was negative, prepend the negative sign
        if (isNegative) {
            num[iter] = '-';
            iter--;  // Prep for later increment when moving to unpadded array
        }

        // Strip leading null padding from return for cleanliness
        iter++;  // move back to last filled position;
        char[] unpadded = Arrays.copyOfRange(num, iter, MAX_LENGTH);

        // Return as a MyString object
        return new MyString(unpadded);
    }
}