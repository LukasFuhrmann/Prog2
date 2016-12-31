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
            try {
                if (scanner.hasNext()) {
                    quit = execute(scanner);
                } else {
                    throw new IllegalArgumentException("No command!");
                }
            } catch (IllegalArgumentException e) {
                error(e.getMessage());
            }
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
        try {
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
                    throw new IllegalArgumentException("Illegal command. Use "
                            + "<Help> for a overview of commands.");
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
        return false;
    }

    private static int parseScannerInt(Scanner scanner) {
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("Command needs integers.");
        } else {
            return scanner.nextInt();
        }
    }

    private static void changeLevel(Scanner scanner) {
        try {
            int level = parseScannerInt(scanner);
            if (level <= 0) {
                throw new IllegalArgumentException("Difficult level has to be"
                        + " higher than 0.");
            } else {
                game.setLevel(level);
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void switchOpener() {
        game = new AbaloneGame(game.getOpeningPlayer());
    }

    private static void numbersOfBalls() {
    }

    private static void checkMove(Scanner scanner) {
        try {
            int fromRow = parseScannerInt(scanner);
            int fromDiag = parseScannerInt(scanner);
            if (game.isValidPosition(fromRow, fromDiag)) {
                int toRow = parseScannerInt(scanner);
                int toDiag = parseScannerInt(scanner);
                if (game.isValidTarget(toRow, toDiag)) {
                    game.move(fromRow, fromDiag, toRow, toDiag);
                } else {
                    throw new IllegalArgumentException("Illegal target "
                            + "coordinates.");
                }
            } else {
                throw new IllegalArgumentException("Illegal start coordinates"
                        + ".");
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void startNewGame(Scanner scanner) {
        try {
            int size = parseScannerInt(scanner);
            if (size < game.MIN_SIZE) {
                throw new IllegalArgumentException("Board size too small.");
            } else {
                game = new AbaloneGame(size);
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
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
