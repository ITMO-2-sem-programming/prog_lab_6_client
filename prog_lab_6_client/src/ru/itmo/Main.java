package ru.itmo;



import ru.itmo.connection.ConnectionManager;
import ru.itmo.connection.Request;
import ru.itmo.connection.Response;
import ru.itmo.connection.Serializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamCorruptedException;
import java.util.Iterator;


public class Main {

    private static ConnectionManager connectionManager;


    public static void main(String[] args) {

        Validator.setSymbolsForNullValues(new String[] {""});
        run();
    }


    public static void run()  {
        try {
            connectionManager = new ConnectionManager("localhost", 61531);
            System.out.println("Successfully connected to server!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Client is running..." +
                "\nEnter command:");

        String command;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                command = reader.readLine();
                System.out.println(runCommand(command));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Error: Unknown error during reading the input was occurred.");
            }
        }

    }


    public static String runCommand(String command) {
        return runCommand(connectionManager, command, false, null);
    }


    public static String runCommand(ConnectionManager connectionManager, String command, boolean executionOfScript, Iterator<String> iterator) {

        Request request;
        Response response;


        try {
            request = CollectionManager.executeCommand(command, executionOfScript, iterator);

            if (request != null) {
                try {
                    connectionManager.send(Serializer.toByteArray(request));

                    while (true) {
                        try {
                            response = (Response) Serializer.toObject(connectionManager.receive());
                            break;
                        } catch (StreamCorruptedException ignored) {}
                    }

                    return response.getMessage();
                } catch (IOException e) {
                    throw new IllegalArgumentException("Error: Can't connect server.");
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            return "";
        } catch (IllegalArgumentException | IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static ConnectionManager getConnectionManager() {
        return connectionManager;
    }

}


