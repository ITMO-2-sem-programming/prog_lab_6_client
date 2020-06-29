package ru.itmo.commands;


public class MaxByFrontManCommand extends Command{

    private final static byte numberOfArguments = 0;


    public static void validateArguments(String[] args) {

        checkNumberOfArguments(args, numberOfArguments);

    }
}
