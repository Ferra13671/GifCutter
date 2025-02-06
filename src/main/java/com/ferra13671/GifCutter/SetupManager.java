package com.ferra13671.GifCutter;

import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SetupManager {
    private static final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();

    public static void createFiles() {
        Path inputPath = Paths.get(SettingManager.getInputFolder());
        Path outputFolder = Paths.get(SettingManager.getOutputFolder());

        try {
            if (!Files.exists(inputPath)) Files.createDirectory(inputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (!Files.exists(outputFolder)) Files.createDirectory(outputFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            createSettingFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        Path settingFile = Paths.get("setting.json");
        InputStream defaultSettings = SettingManager.class.getClassLoader().getResourceAsStream("defaultSettings.json");
        /*
        If any settings are missing in the custom settings file, they will be set from the default settings file.
         */
        SettingManager.loadSettings(defaultSettings);

        if (Files.exists(settingFile)) {
            try {
                SettingManager.loadSettings(Files.newInputStream(settingFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            defaultSettings.close();
        } catch (IOException ignored) {}
    }

    public static void createSettingFile() throws IOException {
        Path settingFile = Paths.get("setting.json");

        if (!Files.exists(settingFile)) {
            Files.createFile(settingFile);

            BufferedWriter writer = Files.newBufferedWriter(settingFile, StandardCharsets.UTF_8);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("inputFolder", new JsonPrimitive("Input"));
            jsonObject.add("outputFolder", new JsonPrimitive("Output"));
            jsonObject.add("maxThreads", new JsonPrimitive(4));
            writer.write(gson.toJson(JsonParser.parseString(jsonObject.toString())));
            writer.close();
        }
    }
}
