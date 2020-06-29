package ru.itmo;


import java.io.*;
import java.util.ArrayList;

public class FileManager {


    public static ArrayList<String> getCommandsFromFile(String filePath) throws IOException {

        if (filePath == null) throw new FileNotFoundException("Error: Can't resolve the file path (null).");
        File file = new File(filePath);
//        if (!file.isFile()) throw new IOException("Error: The path leads to folder, not file."); Работает некорректно
        if (!file.exists()) throw new FileNotFoundException("Error: The file doesn't exist.");
        if (!file.canRead()) throw new SecurityException("Error: Can't read the file: Access denied.");


        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));) {

            ArrayList<String> fileData = new ArrayList<>();
            String newLine;

            while ((newLine = inputStreamReader.readLine()) != null) {
                fileData.add(newLine);
            }

            return fileData;

        } catch (IOException e) {
            throw new IOException("Error: Unknown error during reading the file was occurred.");
        }
    }
}

