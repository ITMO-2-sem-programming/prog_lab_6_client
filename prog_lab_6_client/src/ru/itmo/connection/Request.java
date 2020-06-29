package ru.itmo.connection;

import ru.itmo.classes.MusicBand;

import java.io.Serializable;

public class Request implements Serializable {


    String commandName;
    String argumentKey = null;
    MusicBand argumentElement = null;


    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getArgumentKey() {
        return argumentKey;
    }

    public void setArgumentKey(String argumentKey) {
        this.argumentKey = argumentKey;
    }

    public MusicBand getArgumentMusicBand() {
        return argumentElement;
    }

    public void setArgumentMusicBand(MusicBand argumentElement) {
        this.argumentElement = argumentElement;
    }
}
