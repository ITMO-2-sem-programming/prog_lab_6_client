package ru.itmo.commands;


public class RemoveLowerKeyCommand extends Command {

    private final static byte numberOfArguments = 1;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Incorrect 'key' value.");
        }
    }
}
