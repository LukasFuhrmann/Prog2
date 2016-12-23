package abalone;

import abalone.model.AbaloneGame;
import abalone.model.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.IllegalFormatException;
import java.util.Scanner;


/**
 * This program implements the input interface for the user for a program
 * which implements the game abalone. The user can play against the machine
 * via the input.
 */
public final class Shell {

    private static Board game = new AbaloneGame();
    private static final String ERROR = "Error! ";
    private static final String PROMPT = "abalone> ";

    private Shell() {
    }

    /**
     * Starts the program. A reader for inputs into the console initialized.
     *
     * @param args not used.
     * @throws IOException If an input or output exception occurred.
     */
    public static void main(String[] args) throws IOException {
        try {
            BufferedReader stdin = new BufferedReader(
                    new InputStreamReader(System.in));
            boolean quit = false;
            while (!quit) {
                System.out.print(PROMPT);
                String input = stdin.readLine();
                if (input == null) {
                    break;
                }
                Scanner scanner = new Scanner(input);
                scanner.useDelimiter("\\s+");
                if (scanner.hasNext()) {
                    quit = execute(scanner);
                } else {
                    throw new IllegalArgumentException("No command!");
                }
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            error(e.getMessage());
        } catch (IOException eIO) {

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
        char cmd = scanner.next().toUpperCase().charAt(0);
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
            default:
                throw new IllegalArgumentException("Illegal command. Use <Help>"
                        + " for a overview of commands.");
        }
        return false;
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
            throw new IllegalArgumentException("Two integers are needed.");
        } else {
            int size = scanner.nextInt();
            if (size < game.MIN_SIZE) {
                throw new IllegalArgumentException("Board size too small.");
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
        System.err.println(ERROR + err);
    }
}
