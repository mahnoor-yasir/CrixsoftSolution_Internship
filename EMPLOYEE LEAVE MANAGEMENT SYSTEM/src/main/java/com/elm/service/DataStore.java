package com.elm.service;

import com.elm.model.AppData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataStore {
    private static DataStore instance;
    private AppData appData;
    private final String DATA_DIR = "data";
    private final String DATA_FILE = "elm-data.ser";

    private DataStore() {}

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    public void initialize() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
            }

            Path dataFile = Paths.get(DATA_DIR, DATA_FILE);
            if (Files.exists(dataFile)) {
                loadData();
            } else {
                appData = SeedData.createSeedData();
                saveData();
            }
        } catch (IOException e) {
            e.printStackTrace();
            appData = SeedData.createSeedData();
            saveData();
        }
    }

    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(Paths.get(DATA_DIR, DATA_FILE).toFile()))) {
            appData = (AppData) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            appData = SeedData.createSeedData();
            saveData();
        }
    }

    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Paths.get(DATA_DIR, DATA_FILE).toFile()))) {
            oos.writeObject(appData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppData getAppData() {
        if (appData == null) {
            initialize();
        }
        return appData;
    }

    public void setAppData(AppData appData) {
        this.appData = appData;
        saveData();
    }
}
