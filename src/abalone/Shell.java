package abalone;

import abalone.model.AbaloneGame;
import abalone.model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


/**
 * This program implements the input interface for the user for a program
 * which implements the game abalone. The user can play against the machine
 * via the input.
 */
public final class Shell {

    private static Board game = new AbaloneGame();

    private Shell() {
    }

    /**
     * Starts the program. A reader for inputs into the console initialized.
     *
     * @param args not used.
     * @throws IOException If an input or output exception occurred.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader stdin;
        stdin = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;
        while (!quit) {
            System.out.print("abalone> ");
            String input = stdin.readLine();
            if (input == null) {
                break;
            }
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\s+");
            if (scanner.hasNext()) {
                String command = scanner.next();
                char cmd = command.toUpperCase().charAt(0);
                quit = execute(scanner);
            } else {
                System.out.println("Error! No command.");
            }
            scanner.close();
        }
    }

    /**
     * Implements the input interface. Checks if user inputs are valid
     * commands and communicates with the game.
     *
     * @param scanner reads command from console.
     * @return true if program is stopped by command.
     * @throws IOException If an input or output exception occurred.
     */
    private static boolean execute(Scanner scanner) throws IOException {
        try {
            if (scanner.hasNext()) {
                String command = scanner.next();
                char cmd = command.toUpperCase().charAt(0);
                switch (cmd) {
                    case 'N':
                        startNewGame(scanner);
                        break;
                    case 'S':
                        switchOpener();
                        break;
                    case 'B':
                        numbersOfBalls();
                        break;
                    case 'P':
                        System.out.println(game);
                        break;
                    case 'L':
                        changeLevel(scanner);
                        break;
                    case 'M':
                        checkMove(scanner);
                        break;
                    case 'H':
                        help();
                        break;
                    case 'Q':
                        return true;
                    break;
                    default:
                        System.out.println("Error! Illegal command. Use "
                                + "<Help> for a overview of commands.");
                }
            } else {
                System.out.println("Error! No command.");
            }
            scanner.close();
        } catch (Exception) {

        }
        return true;
    }

    private static void changeLevel(Scanner scanner) {
    }

    private static void switchOpener() {
    }

    private static void numbersOfBalls() {
    }

    private static void checkMove(Scanner scanner) {

    }

    private static void startNewGame(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            error("Two integers are needed.");
        } else {
            int size = scanner.nextInt();
            if (size < game.MIN_SIZE) {
                error("Game board size too small.");
            } else {
                game = new AbaloneGame(size);
            }
        }
    }

    private static void help() {
        System.out.println("HELP: Here will you find all available commands. ");
        System.out.println("NOTE: You only need the first letter of a command"
                + " and the case sensitivity is ignored.");
        System.out.println("<NEW>          : Creates a new field.");
        System.out.println("<PRINT>        : Prints out all points in current"
                + " field.");
        System.out.println("<ADD><X><Y>    : Adds the point (X, Y) to the "
                + "field. Only integers are allowed for X,Y.");
        System.out.println("<REMOVE><X><Y> : Removes the point (X, Y) from "
                + "the field. Only integers are allowed for X,Y.");
        System.out.println();
        System.out.println("<QUIT>         : Ends the program.");
    }

    private static void error(String err) {
        System.out.println("Error! " + err);
    }
}
