package ru.itmo.commands;


public class ShowCommand extends Command {

    private final static byte numberOfArguments = 0;

    public static void validateArguments(String[] args) {

        if (args.length > 1) throw new IllegalArgumentException("Error: Command gets not more than 1 argument.");
        if (args.length == 1) {
            try {
                Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: Incorrect 'key' value.");
            }
        }
    }
}
