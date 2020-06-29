package ru.itmo.commands;


import ru.itmo.CollectionManager;
import ru.itmo.FileManager;
import ru.itmo.Main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class ExecuteScriptCommand extends Command {

    private final static byte numberOfArguments = 1;


    public static String execute(String filePath) {

        ArrayList<String> commands;
        try {
            commands = FileManager.getCommandsFromFile(filePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        StringBuilder ignoredCommands = new StringBuilder("\nIgnored commands:");
        String input = "";
        Iterator<String> iterator = commands.iterator();
        while (iterator.hasNext()) {
            try {
                input = iterator.next();
                System.out.println(Main.runCommand(Main.getConnectionManager(), input, true, iterator));
//                CollectionManager.executeCommand(input, true, iterator);
            } catch (Exception e) {
                ignoredCommands.append(String.format("\nCommand: %s\n%s", input, e.getMessage()));
                ignoredCommands.append("\nWarning: The execution of the commands from the file was finished because of Error.");
                break;
            }
        }
        if (ignoredCommands.length() == 18) return "";
        else return ignoredCommands.toString();
    }



    public static String execute(String[] args) {

        return execute(args[0]);
    }


    public static void validateArguments(String[] args) {
        checkNumberOfArguments(args, numberOfArguments);
    }
}
