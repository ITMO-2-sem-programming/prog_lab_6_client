package ru.itmo.commands;


import ru.itmo.personalExceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExitCommand extends Command {

    private final static byte numberOfArguments = 0;



    private static void execute(boolean safeMode) {
        if (!safeMode) {
            System.exit(2);
        } else {
            System.out.println("Warning: Command will stop the program. All unsaved changes will be lost.\n" +
                    "Enter 'Yes' or 'Y' to confirm, other input will reject this command:");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String confirmation = reader.readLine().toLowerCase();
                if (confirmation.equals("yes") || confirmation.equals("y")) {
                    System.out.println("The program stops...");
                    System.exit(2);
                } else System.out.println("Command was rejected. Program continues working.");
            } catch (Exception e) {
                throw new InvalidCommandException("Error: Unresolved error was occurred. Command was rejected.");
            }
        }
    }



    public static void execute(String[] args, boolean safeMode) {

        execute(safeMode);
    }


    public static void validateArguments(String[] args) {
        checkNumberOfArguments(args, numberOfArguments);
    }
}
