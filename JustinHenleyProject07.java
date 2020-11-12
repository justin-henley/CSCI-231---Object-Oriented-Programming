// Project 07:  Single-Dimensional Array Methods
// Description: Demonstrates a variety of functions for processing integer arrays
// Author:      Justin Henley, jahenley@mail.fhsu.edu
// Date:        2020-10-18

// Sample runs are included at the bottom

import java.util.Random;
import java.util.Scanner;

public class JustinHenleyProject07 {
    public static void main(String[] args) {
        // Create a new Scanner object
        Scanner input = new Scanner(System.in);

        // Declare an int array of size 10
        int[] array = new int[10];

        // Initialize the array by calling InitializeArray function
        initializeArray(array);

        // Print out the array by calling printArray function
        System.out.println("\n** The Array **");
        printArray(array);
        System.out.println("***************\n");

        // Print out the largest and smallest value of the array by using
        //   System.out.println and calling largest and smallest functions
        System.out.println("The largest value of the array is: " + largest(array));
        System.out.println("The smallest value of the array is: " + smallest(array));

        // Print out the range of the array
        System.out.println("The range of the array is: " + range(array));

        // Print out the average of the array elements
        System.out.println("The average of the array elements is: " + average(array));

        // Ask user to enter a search key
        System.out.print("\nEnter a key to search for in the array: ");
        int key = input.nextInt();

        // Call the linearSearch function with the array and user-supplied
        //   key. Based on the result, print out an appropriate message
        System.out.printf("\nThe key was%s found!\n", (linearSearch(array, key) == -1) ? " not":"");

        // Call the selectSort function to sort the array
        selectSort(array);

        //Print out array again to show the sorted array using printArray function
        System.out.println("\n**Sorted Array*");
        printArray(array);
        System.out.println("***************");
    }

    // Return the index of the first occurrence of key in arr.
    // If key is not found in arr, return -1.
    public static int linearSearch(int arr[], int key) {
        // Search for the key in the array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == key)
                return i;
        }

        // Can only be reached if key was not found, so return -1 (not found)
        return -1;
    }

    // Sort arr from least to largest using a selection sort algorithm.
    public static void selectSort(int arr[]) {
        // Iterate over the entire array once, excluding the last element
        for (int i = 0; i < arr.length - 1; i++) {
            // Find the current minimum in the array from arr[i] to the end
            int currMin = arr[i];
            int currMinIndex = i;

            // Iterate over the array from i+1 to end to check for a smaller value
            for (int j = i + 1; j < arr.length; j++) {
                if (currMin > arr[j]) {
                    currMin = arr[j];
                    currMinIndex = j;
                }
            }

            // Swap the values at i and currMinIndex if needed
            if (currMinIndex != i) {
                arr[currMinIndex] = arr[i];
                arr[i] = currMin;
            }
        }
    }

    // Print out all array elements, 5 elements per line.
    public static void printArray(int arr[]) {
        // Iterate over the array
        for (int i = 0; i < arr.length; i++) {
            // Print each element
            System.out.print(arr[i] + " ");

            // Print a new line after every 5 elements
            if ((i + 1) % 5 == 0)
                System.out.print("\n");
        }
    }

    // Assign each element in array with a random number
    // between 1 and 100, inclusive.
    public static void initializeArray(int arr[]) {
        // Create a new random number generator
        Random rand = new Random();

        for (int i = 0; i < arr.length; i++) {
            // nextInt will return a value [0, 100), adding one makes it [1, 100]
            arr[i] = rand.nextInt(100) + 1;
        }
    }

    // Find the range of all the array’s elements.
    // The range is defined as the difference between the
    // largest and smallest elements in the array.
    public static int range(int arr[]) {
        return largest(arr) - smallest(arr);
    }

    // Find the largest element in arr
    public static int largest(int arr[]) {
        // Initialize max to the first value in the array
        int max = arr[0];

        // Iteratively check for a new maximum number
        // I know this will check arr[0] again, but I allow it in case the array only has a single element
        // Saves writing a guard clause for arr.length == 1
        for (int e: arr) {
            if (e > max)
                max = e;
        }
        // return the largest number found
        return max;
    }

    // Find the smallest element in arr
    public static int smallest(int arr[]) {
        // Initialize min to the smallest number in the array
        int min = arr[0];

        // Iteratively check for a new minimum number
        for (int e: arr) {
            if (e < min)
                min = e;
        }
        // return the smallest number found
        return min;
    }

    // Find the average value of all elements in arr
    public static double average(int arr[]) {
        double sum = 0;

        // sum all the elements of the array
        for (int e: arr) {
            sum += e;
        }

        // return the average
        return sum / arr.length;
    }

}


/* SAMPLE RUNS

RUN 1:
    ** The Array **
    22 17 74 51 36
    49 97 14 39 66
    ***************

    The largest value of the array is: 97
    The smallest value of the array is: 14
    The range of the array is: 83
    The average of the array elements is: 46.5

    Enter a key to search for in the array: 7

    The key was not found!

    **Sorted Array*
    14 17 22 36 39
    49 51 66 74 97
    ***************

RUN 2:
    ** The Array **
    42 56 37 4 55
    94 40 97 99 87
    ***************

    The largest value of the array is: 99
    The smallest value of the array is: 4
    The range of the array is: 95
    The average of the array elements is: 61.1

    Enter a key to search for in the array: 55

    The key was found!

    **Sorted Array*
    4 37 40 42 55
    56 87 94 97 99
    ***************

RUN 3:
    ** The Array **
    70 73 92 69 77
    100 21 55 10 24
    ***************

    The largest value of the array is: 100
    The smallest value of the array is: 10
    The range of the array is: 90
    The average of the array elements is: 59.1

    Enter a key to search for in the array: 100

    The key was found!

    **Sorted Array*
    10 21 24 55 69
    70 73 77 92 100
    ***************

 */