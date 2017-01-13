package abalone;

import abalone.model.AbaloneGame;
import abalone.model.Board;
import abalone.model.Color;
import abalone.model.Player;

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

    private static final String ERROR = "Error! ";
    private static final String PROMPT = "abalone> ";
    private static int level = 2;
    private static Board game = new AbaloneGame(level);

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
                throw new IllegalArgumentException("Difficulty level has to be"
                        + " higher than 0.");
            } else {
                Shell.level = level;
                game.setLevel(level);
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void switchOpener() {
        game = new AbaloneGame(game.getOpeningPlayer(), game.getSize(), level);
        newGame();
        if (game.getOpeningPlayer() == Player.MACHINE) {
            machineM();
        }
    }

    private static void numbersOfBalls() {
        System.out.println("X: " + game.getNumberOfBalls(Color.BLACK));
        System.out.println("O: " + game.getNumberOfBalls(Color.WHITE));
    }

    private static void checkMove(Scanner scanner) {
        try {
            int fromRow = parseScannerInt(scanner);
            int fromDiag = parseScannerInt(scanner);
            if (game.isValidPosition(fromRow - 1, fromDiag - 1)) {
                int toRow = parseScannerInt(scanner);
                int toDiag = parseScannerInt(scanner);
                if (game.isValidTarget(toRow - 1, toDiag - 1)) {
                    makeMove(fromRow - 1, fromDiag - 1, toRow - 1, toDiag - 1);
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

    private static void makeMove(int fromRow, int fromDiag, int toRow,
                                 int toDiag) {
        Board save = game;
        game = game.move(fromRow, fromDiag, toRow, toDiag);
        if (game != null) {
            if (game.isGameOver()) {
                isOver();
            } else if (game.getNextPlayer() == Player.HUMAN) {
                System.out.println("I must skip (no possible moves).");
            } else {
                machineM();
            }
        } else {
            game = save;
            throw new IllegalArgumentException("Illegal move.");
        }
    }

    private static void machineM() {
        game = game.machineMove();
        if (game.isGameOver()) {
            isOver();
        } else {
            while (game.getNextPlayer() == Player.MACHINE) {
                game = game.machineMove();
                System.out.println("You must skip (no possible moves).");
                if (game.isGameOver()) {
                    isOver();
                    break;
                }
            }
        }
    }

    private static void isOver() {
        if (game.getWinner() == Player.HUMAN) {
            System.out.println("Congratulations! You won.");
        } else {
            System.out.println("Sorry! Machine wins");
        }
    }

    private static void startNewGame(Scanner scanner) {
        try {
            int size = parseScannerInt(scanner);
            if (size < Board.MIN_SIZE) {
                throw new IllegalArgumentException("Board size too small.");
            } else if (size % 2 == 0) {
                throw new IllegalArgumentException("Board size needs be odd.");
            } else {
                game = new AbaloneGame(size, game.getOpeningPlayer(), level);
                newGame();
                if (game.getOpeningPlayer() == Player.MACHINE) {
                    machineM();
                }
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private static void help() {
        System.out.println("This game is a variation of the game Abalone by "
                + "Michel Lalet and Laurent Lévi.");
        System.out.println("Here is a short "
                + "description of all the commands you can use.");
        System.out.println("Note: You only need the first letter of a command"
                + " and the case sensitivity is ignored.");
        System.out.println("<NEW> <boardSize>           : Starts new game with "
                + "a <boardSize> big game board.");
        System.out.println("<SWITCH>                    : Starts a new game "
                + "with switched colors.");
        System.out.println("<PRINT>                     : Prints the game "
                + "board.");
        System.out.println("<LEVEL> <newLevel>          : Changes the game "
                + "difficulty to <newLevel>.");
        System.out.println("<MOVE> <r1> <d1> <r2> <d2>  : Moves ball from "
                + "<r1> <d1> to <r2>< d2>.");
        System.out.println("<BALLS>                     : Returns number of  "
                + "black and white balls.");
        System.out.println("<QUIT>                      : Ends the program.");
    }

    private static void error(String err) {
        System.err.println(ERROR + err);
    }

    private static void newGame() {
        System.out.println("New game started. You are " + game.getHumanColor()
                + ".");
    }
}
