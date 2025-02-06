package com.ferra13671.GifCutter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class SettingManager {
    private static String inputFolder = "";
    private static String outputFolder = "";
    private static int maxThreads = 0;


    public static void loadSettings(InputStream inputStream) {
        JsonObject jsonObject = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
        if (jsonObject.get("inputFolder") != null) inputFolder = jsonObject.get("inputFolder").getAsString();
        if (jsonObject.get("outputFolder") != null) outputFolder = jsonObject.get("outputFolder").getAsString();
        if (jsonObject.get("maxThreads") != null) maxThreads = jsonObject.get("maxThreads").getAsInt();
    }

    public static String getInputFolder() {
        return inputFolder;
    }

    public static String getOutputFolder() {
        return outputFolder;
    }

    public static int getMaxThreads() {
        return maxThreads;
    }
}
