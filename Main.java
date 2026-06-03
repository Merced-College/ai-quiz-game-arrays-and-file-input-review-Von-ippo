//Savauhn Harvey
//5/30/2026
//AI Quiz Game
//changed: Added over 20 meaningful, conceptual comments and implemented a final score percentage breakdown enhancement.

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    // Global constants used to easily manage game configuration without modifying loops
    public static final int NUMBER_OF_QUESTIONS = 10;
    public static final int NUMBER_OF_CHOICES = 4;

    public static void main(String[] args) {
        // Parallel arrays to hold corresponding quiz data across the same index values
        String[] questions = new String[NUMBER_OF_QUESTIONS]; // Holds the plain text for each question
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES]; // Matrix storing multiple choices per question
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS]; // Stores the numerical index of the correct option

        // Invokes the file parser to read the external CSV file and load data into the arrays
        readQuizFile(questions, answers, correctAnswers);

        // Instantiates a stream reader to capture user inputs from the console
        Scanner input = new Scanner(System.in);
        int score = 0; // Tracks the total number of correct answers earned by the user

        System.out.println("Welcome to the AI Quiz Game!");
        System.out.println("Choose the correct answer by entering 1, 2, 3, or 4.\n");

        // Primary loop that cycles through each question in the dataset one by one
        for (int i = 0; i < questions.length; i++) {
            // Displays the question text to the player, adjusted by 1 so it is human-readable (Question 1 instead of 0)
            System.out.println("Question " + (i + 1) + ": " + questions[i]);

            // This nested loop displays each answer choice for the current quiz question
            for (int j = 0; j < answers[i].length; j++) {
                System.out.println((j + 1) + ". " + answers[i][j]);
            }

            System.out.print("Your answer: ");
            // Captures the user's raw choice and shifts it down by 1 to match 0-indexed arrays
            int userAnswer = input.nextInt() - 1;

            // Evaluates whether the user's selected index matches the recorded answer key value
            if (userAnswer == correctAnswers[i]) {
                System.out.println("Correct!\n");
                score++; // Awards a point for a matching answer
            } else {
                // Fallback messaging that pulls the literal text string of the correct choice from the 2D grid
                System.out.println("Incorrect.");
                System.out.println("The correct answer was: " + answers[i][correctAnswers[i]] + "\n");
            }
        }

        System.out.println("Quiz complete!");
        System.out.println("Your final score is: " + score + " out of " + questions.length);

        // Enhancement: This section calculates the user's percentage performance score, 
        // prints it formatted cleanly, and displays a tailored achievement message.
        double finalPercentage = ((double) score / questions.length) * 100;
        System.out.printf("Your Score Percentage: %.1f%%\n", finalPercentage);
        if (finalPercentage >= 80.0) {
            System.out.println("Exceptional work! You really know your stuff.");
        } else if (finalPercentage >= 50.0) {
            System.out.println("Not bad! Review the topics you missed to get a perfect score next time.");
        } else {
            System.out.println("Keep practicing! Try taking the quiz again to boost your score.");
        }

        // Closes the input pipeline to prevent system memory leaks
        input.close();
    }

    // Method dedicated to parsing external text files and systematically mapping tokens into programmatic storage
    public static void readQuizFile(String[] questions, String[][] answers, int[] correctAnswers) {
        try {
            // Directs the file system handler to open the specific csv file asset
            File file = new File("ai_quiz_questions.csv");
            Scanner fileReader = new Scanner(file);

            // Skips over the first row of text, which acts as the category columns/headers
            fileReader.nextLine();

            int index = 0; // Incremental tracker indicating which database slot we are filling next

            // Continues processing text until the file runs dry or our maximum array limits are hit
            while (fileReader.hasNextLine() && index < questions.length) {
                String line = fileReader.nextLine(); // Extracts an entire single row from the text document
                String[] data = line.split(","); // Splits the data row into individual elements using commas

                questions[index] = data[0]; // Allocates the first token directly as the primary question phrase

                // Loops through columns 1 to 4 to save the subsequent split elements as choices inside the 2D matrix
                for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
                    answers[index][i] = data[i + 1];
                }

                // Standardizes all correct answer patterns to index 0 for basic structural tracking
                correctAnswers[index] = 0;
                index++; // Advances our row pointer forward to begin preparation for the next text line
            }

            // Safely terminates the file scanner connection link once operations finish
            fileReader.close();

        } catch (FileNotFoundException e) {
            // Diagnostic catch block to handle missing file paths without crashing the whole application
            System.out.println("The quiz file could not be found.");
        }
    }
}