package abalone;

import abalone.model.AbaloneGame;

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
        execute(stdin);
    }

    /**
     * Implements the input interface. Checks if user inputs are valid
     * commands and communicates with the game.
     *
     * @param stdin input reader for command on console.
     * @throws IOException If an input or output exception occurred.
     */
    private static void execute(BufferedReader stdin) throws IOException {
        boolean quit = false;
        AbaloneGame game = new AbaloneGame();
        while (!quit) {
            System.out.print("ch> ");
            String input = stdin.readLine();
            if (input == null) {
                break;
            }
            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\s+");
            if (scanner.hasNext()) {
                String command = scanner.next();
                char cmd = command.toUpperCase().charAt(0);
                switch (cmd) {
                    case 'N':
                        startNewGame(scanner);
                        break;
                    case 'A':
                        add(scanner, field);
                        break;
                    case 'R':
                        remove(scanner, field);
                        break;
                    case 'P':
                        System.out.println(game.toString());
                        break;
                    case 'C':
                        System.out.println(field.printConvexHull());
                        break;
                    case 'H':
                        help();
                        break;
                    case 'Q':
                        quit = true;
                        break;
                    default:
                        System.out.println("Error! Illegal command. Use "
                                + "<Help> for a overview of commands.");
                }
            } else {
                System.out.println("Error! No command.");
            }
            scanner.close();
        }
    }

    private static void startNewGame(Scanner scanner) {
    }


    private static void add(Scanner scanner, Field field) {
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Two integers are needed.");
        } else {
            int firstIn = scanner.nextInt();
            if (!scanner.hasNextInt()) {
                System.out.println("Error! Two integers are needed.");
            } else {
                field.addPoint(firstIn, scanner.nextInt());
            }
        }
    }

    private static void remove(Scanner scanner, Field field) {
        if (!scanner.hasNextInt()) {
            System.out.println("Error! Two integers are needed.");
        } else {
            int firstIn = scanner.nextInt();
            if (!scanner.hasNextInt()) {
                System.out.println("Error! Two integers are needed.");
            } else {
                field.removePoint(firstIn, scanner.nextInt());
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
        System.out.println("<CONVEX>       : Prints all points that are "
                + "elements of the convex hull of the set of all points.");
        System.out.println("<QUIT>         : Ends the program.");
    }
}
