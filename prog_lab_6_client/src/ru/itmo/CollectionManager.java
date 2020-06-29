package ru.itmo;


import ru.itmo.connection.*;

import ru.itmo.classes.*;
import ru.itmo.commands.*;
import ru.itmo.personalExceptions.StopCreatingTheMusicBandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CollectionManager {

    static Request request;
    static String stopCreatingMusicBandCommand = "stop_creating";

//

    public static Request executeCommand(String commandLineInput, boolean executionOfScript, Iterator<String> iterator) throws IOException {

        if (commandLineInput == null) throw new IllegalArgumentException("Error: 'null' command can't be executed.");
        String[] commandLineInputArgs = commandLineInput.trim().toLowerCase().split(" ");
        String commandName = commandLineInputArgs[0];
        String[] args;
        if (commandLineInputArgs.length == 1) {
            args = new String[]{};
        } else args = Arrays.copyOfRange(commandLineInputArgs, 1, commandLineInputArgs.length);


        request = new Request();

        String argumentKey = null;
        MusicBand argumentMusicBand = null;

        try {
            switch (commandName) {
                case ("help"): // {0 , 1} args
                    HelpCommand.validateArguments(args);
                    if (args.length == 1) argumentKey = args[0];
        //                System.out.println(helpCommand.execute(collection, args));
                    break;

                case ("info"): // No args
                    InfoCommand.validateArguments(args);
        //                System.out.println(infoCommand.execute(collection, args, fileManager.getDateOfInitialisation().toString()));
                    break;

                case ("show"): // {0 , 1} args
                    ShowCommand.validateArguments(args);
                    if (args.length == 1) argumentKey = args[0];
        //                System.out.println(showCommand.execute(collection, args));
                    break;

                case ("insert"): // key and MusicBand
                    InsertCommand.validateArguments(args);

                    argumentKey = args[0];
                    argumentMusicBand = Command.getNewMusicBand(Integer.parseInt(args[0]), executionOfScript, iterator);

        //                insertCommand.execute(collection, args, executionOfScript, iterator);
                    break;

                case ("update"): // key and MusicBand
                    UpdateCommand.validateArguments(args);

                    argumentKey = args[0];
                    argumentMusicBand = Command.getNewMusicBand(Integer.parseInt(args[0]), executionOfScript, iterator);

        //                updateCommand.execute(collection, args, executionOfScript, iterator);
                    break;

                case ("remove_key"): // 1 arg
                    RemoveByKeyCommand.validateArguments(args);

                    argumentKey = args[0];

        //                removeByKeyCommand.execute(collection, args);
                    break;

                case ("clear"): // No args
                    ClearCommand.validateArguments(args);
                    break;

                case ("execute_script"): // Stand-alone command
        //                System.out.println(executeScriptCommand.execute(collection, args));
                    ExecuteScriptCommand.validateArguments(args);

                    System.out.println(ExecuteScriptCommand.execute(args)); // Возвращает строку. Как с ней поступить?
                    return null;

                case ("exit"): // Stand-alone command
                    ExitCommand.validateArguments(args);
                    request.setCommandName("save");
                    Main.getConnectionManager().send(Serializer.toByteArray(request));
                    ExitCommand.execute(args, !executionOfScript);
                    break;

                case ("remove_greater"): // MusicBand
                    RemoveGreaterCommand.validateArguments(args);

                    argumentMusicBand = Command.getNewMusicBand(1, executionOfScript, iterator);

        //                removeGreaterCommand.execute(collection, args, executionOfScript, iterator);
                    break;

                case ("replace_if_lower"): // key and musicBand
                    ReplaceIfLowerCommand.validateArguments(args);

                    argumentKey = args[0];
                    argumentMusicBand = Command.getNewMusicBand(Integer.parseInt(args[0]), executionOfScript, iterator);

        //                replaceIfLowerCommand.execute(collection, args, executionOfScript, iterator);
                    break;

                case ("remove_lower_key"): // 1 arg
                    RemoveLowerKeyCommand.validateArguments(args);

                    argumentKey = args[0];
        //                removeLowerKeyCommand.execute(collection, args);
                    break;

                case ("remove_all_by_genre"): // 1 arg
                    RemoveAllByGenreCommand.validateArguments(args);

                    argumentKey = args[0];
        //                removeAllByGenreCommand.execute(collection, args);
                    break;

                case ("max_by_front_man"): // No args
                    MaxByFrontManCommand.validateArguments(args);
        //                System.out.println(maxByFrontManCommand.execute(collection, args));
                    break;

                case ("filter_greater_than_singles_count"): // 1 arg
                    FilterGreaterThanSinglesCountCommand.validateArguments(args);

                    argumentKey = args[0];
        //                System.out.println(filterGreaterThanSinglesCountCommand.execute(collection, args));
                    break;
                case (""):
                    return null;


                default:
                    throw new IllegalArgumentException(String.format("Error: Command '%s' isn't supported.", commandLineInputArgs[0]));
            }
        } catch (StopCreatingTheMusicBandException e) {
            return null;
        }

        request.setCommandName(commandName);
        request.setArgumentKey(argumentKey);
        request.setArgumentMusicBand(argumentMusicBand);
//        clientManager.send(Serializer.toByteArray(new Student(1,4, new Teacher("Lolbject", 9)))); // Rewrite MusicBand to Request
//        clientManager.send("WgWaHackishness".getBytes());

        return request;

//        try {
//            connectionManager.send(Serializer.toByteArray(request));
//            response = (Response) Serializer.toObject(connectionManager.receive());
//        } catch (IOException e) {
//            System.out.println("Error: Can't connect server.");
//            System.out.println(e.toString());
//        }
//
//
//        System.out.println(response.getMessage());

//        response = (Response) Serializer.toObject(clientManager.receive());
//        System.out.println("Received message: " + new String(clientManager.receive()));

    }


    public static Request executeCommand(String commandLineInput) throws IOException {
        return executeCommand(commandLineInput, false, null);
    }


































    public static MusicBand getNewMusicBandFromStandardInput() throws IOException {
        MusicBand newMusicBand = new MusicBand();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String input;
        boolean correctInputIndicator = false;


        System.out.println(String.format("Creating new Music band. Please, fill the information...\n" +
                "Tip: You can type '%s' to finish creating the band. Attention: No input will be saved.", stopCreatingMusicBandCommand));

        while (!correctInputIndicator) {
            try {
                System.out.println("Enter band name {String}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);

                newMusicBand.setName(Validator.validateString(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + MusicBand.musicBandFieldsDescription.get("name"));
            }
        }

        newMusicBand.setCoordinates(getNewCoordinatesFromStandardInput());

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter number of participants {long}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                newMusicBand.setNumberOfParticipants(Validator.validateLongPrimitive(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + MusicBand.musicBandFieldsDescription.get("numberOfParticipants"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter number of singles {int}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                newMusicBand.setSinglesCount(Validator.validateIntegerPrimitive(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + MusicBand.musicBandFieldsDescription.get("singlesCount"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter music genre type {Genre}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                newMusicBand.setGenre(Validator.validateEnum(MusicGenre.class, input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + MusicBand.musicBandFieldsDescription.get("genre"));
            }
        }


        System.out.format("Creating new front man..." +
                "\nEnter one of the following symbols to set value to 'null' : '%s', enter anything else to continue creating:", Arrays.toString(Validator.getSymbolsForNullValues()));

        Person person;
        input = reader.readLine().trim();
        checkForStopCreatingMusicBandException(input);
        if (Arrays.asList(Validator.getSymbolsForNullValues()).contains(input)) {
            person = null;
        } else {
            person = getNewPersonFromStandardInput();
        }

        newMusicBand.setFrontMan(person);

        System.out.println("MusicBand was created!");

        return newMusicBand;
    }


    public static Coordinates getNewCoordinatesFromStandardInput() throws IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String input;
        boolean correctInputIndicator = false;

        Coordinates coordinates = new Coordinates();

        System.out.println("Creating new coordinates...");

        while (!correctInputIndicator) {
            try {
                System.out.println("Enter x {double} coordinate:");
                input = reader.readLine().trim();

                checkForStopCreatingMusicBandException(input);

                coordinates.setX(Validator.validateDoublePrimitive(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Coordinates.coordinatesFieldsDescription.get("x"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter y {int} coordinate:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                coordinates.setY(Validator.validateIntegerPrimitive(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Coordinates.coordinatesFieldsDescription.get("y"));
            }
        }


        return coordinates;
    }


    public static Person getNewPersonFromStandardInput() throws IOException {
        Person person = new Person();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String input;
        boolean correctInputIndicator = false;


        System.out.println("Creating new front man...");

        while (!correctInputIndicator) {
            try {
                System.out.println("Enter front man name {String}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                person.setName(Validator.validateString(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Person.personFieldsDescription.get("name"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter front man height {Long}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                person.setHeight(Validator.validateLong(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Person.personFieldsDescription.get("height"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter front man heir color {Color}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                person.setHeirColor(Validator.validateEnum(Color.class, input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Person.personFieldsDescription.get("heirColor"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter front man nationality {Country}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                person.setNationality(Validator.validateEnum(Country.class, input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Person.personFieldsDescription.get("nationality"));
            }
        }

        person.setLocation(getNewLocationFromStandardInput());


        return person;
    }


    public static Location getNewLocationFromStandardInput() throws IOException {
        Location location = new Location();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String input;
        boolean correctInputIndicator = false;


        System.out.println("Creating new location...");

        while (!correctInputIndicator) {
            try {
                System.out.println("Enter x {Integer} location coordinate:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                location.setX(Validator.validateInteger(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Location.locationFieldsDescription.get("x"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter y {int} location coordinate:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                location.setY(Validator.validateIntegerPrimitive(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Location.locationFieldsDescription.get("y"));
            }
        }

        correctInputIndicator = false;
        while (!correctInputIndicator) {
            try {
                System.out.println("Enter location name {String}:");
                input = reader.readLine().trim();
                checkForStopCreatingMusicBandException(input);
                location.setName(Validator.validateString(input));
                correctInputIndicator = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + Location.locationFieldsDescription.get("name"));
            }
        }

        return location;
    }



    public static MusicBand getNewMusicBandFromFile(Iterator<String> iterator) {
        MusicBand newMusicBand = new MusicBand();

        String input;
        try {
            try {
                newMusicBand.setName(Validator.validateString(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + MusicBand.musicBandFieldsDescription.get("name"));
            }

            newMusicBand.setCoordinates(getNewCoordinatesFromFile(iterator));

            try {
                newMusicBand.setNumberOfParticipants(Validator.validateLongPrimitive(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + MusicBand.musicBandFieldsDescription.get("numberOfParticipants"));
            }

            try {
                newMusicBand.setSinglesCount(Validator.validateIntegerPrimitive(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + MusicBand.musicBandFieldsDescription.get("singlesCount"));
            }

            try {
                newMusicBand.setGenre(Validator.validateEnum(MusicGenre.class, iterator.next().toUpperCase()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + MusicBand.musicBandFieldsDescription.get("genre"));
            }

            Person person;
            input = iterator.next();
            if (Arrays.asList(Validator.getSymbolsForNullValues()).contains(input)) {
                person = null;
            } else {
                person = getNewPersonFromFile(iterator);
            }

            newMusicBand.setFrontMan(person);

            return newMusicBand;

        } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("Error: Creating new 'music band': Not all of the fields are present.");
        }
    }


    public static Coordinates getNewCoordinatesFromFile(Iterator<String> iterator) {

        Coordinates coordinates = new Coordinates();

        try {
            try {
                coordinates.setX(Validator.validateDoublePrimitive(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Coordinates.coordinatesFieldsDescription.get("x"));
            }

            try {
                coordinates.setY(Validator.validateIntegerPrimitive(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Coordinates.coordinatesFieldsDescription.get("y"));
            }

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Error: Creating new 'Coordinates': Not all of the fields are present.");
        }
        return coordinates;
    }


    public static Person getNewPersonFromFile(Iterator<String> iterator) {

        Person person = new Person();

        try {
            try {
                person.setName(iterator.next());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Person.personFieldsDescription.get("name"));
            }

            try {
                person.setHeight(Validator.validateLong(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Person.personFieldsDescription.get("height"));
            }

            try {
                person.setHeirColor(Validator.validateEnum(Color.class, iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Person.personFieldsDescription.get("heirColor"));
            }

            try {
                person.setNationality(Validator.validateEnum(Country.class, iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Person.personFieldsDescription.get("nationality"));
            }

            person.setLocation(getNewLocationFromFile(iterator));

            return person;

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Error: Creating new 'Person': Not all of the fields are present.");
        }
    }


    public static Location getNewLocationFromFile(Iterator<String> iterator) {

        Location location = new Location();

        try {
            try {
                location.setX(Validator.validateInteger(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Location.locationFieldsDescription.get("x"));
            }

            try {
                location.setY(Validator.validateIntegerPrimitive(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Location.locationFieldsDescription.get("y"));
            }

            try {
                location.setName(Validator.validateString(iterator.next()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Error: " + Location.locationFieldsDescription.get("name"));
            }

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Error: Creating new 'location': Not all of the fields are present.");
        }
        return location;
    }


    private static void checkForStopCreatingMusicBandException(String command) {
        if (command.equals(stopCreatingMusicBandCommand)) {
            throw new StopCreatingTheMusicBandException("Creating new music band was stopped.");
        }
    }


    public String getStopCreatingMusicBandCommand() {
        return stopCreatingMusicBandCommand;
    }


    public void setStopCreatingMusicBandCommand(String stopCreatingMusicBandCommand) {
        CollectionManager.stopCreatingMusicBandCommand = stopCreatingMusicBandCommand;
    }
}
