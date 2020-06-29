package ru.itmo.commands;


import ru.itmo.CollectionManager;
import ru.itmo.classes.MusicBand;

import java.io.IOException;
import java.util.Iterator;


public abstract class Command {


    public static void checkNumberOfArguments(String[] arguments, byte numberOfArguments) {
        if (arguments == null) throw new IllegalArgumentException("Error: Arguments can't be 'null' !");
        if (numberOfArguments != arguments.length)
            throw new IllegalArgumentException(String.format("Error: Command gets only %s argument(s).", numberOfArguments));
    }


    public static MusicBand getNewMusicBand(Integer key, boolean executionOfScript, Iterator<String> iterator) throws IOException {

        if (key == null || key <= 0) throw new IllegalArgumentException("Error: Value of 'key' {Integer} can't be 'null' and must be greater than zero.");

        MusicBand musicBand;

        if (executionOfScript) musicBand = CollectionManager.getNewMusicBandFromFile(iterator);
        else musicBand = CollectionManager.getNewMusicBandFromStandardInput();
        musicBand.setId(key);

        return musicBand;
    }

}
