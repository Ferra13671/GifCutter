package com.ferra13671.GifCutter;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GifCutter {

    public static void main(String[] args) {
        SetupManager.loadSettings();
        SetupManager.createFiles();

        File inputFolder = Paths.get(SettingManager.getInputFolder()).toFile();
        File[] files = inputFolder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("The folder with the input gifs is empty.");
            return;
        }
        List<GifDecompileThread> decompileThreads = new ArrayList<>();
        for (File file : files) {
            if (getFileExtension(file.getName()).equals("gif"))
                decompileThreads.add(new GifDecompileThread(new Gif(file)));
        }
        if (decompileThreads.isEmpty()) {
            System.out.println("No gifs found in the input folder.");
            return;
        }
        System.out.println("Found " + decompileThreads.size() + " gifs, start decompiling");
        while (!decompileThreads.isEmpty()) {
            int startedThreads = 0;
            for (GifDecompileThread thread : decompileThreads) {
                if (thread.getDecompileStatus() == GifDecompileThread.DecompileStatus.NOT_STARTED) {
                    if (startedThreads < SettingManager.getMaxThreads()) {
                        startedThreads++;
                        thread.start();
                    }
                } else
                if (thread.getDecompileStatus() == GifDecompileThread.DecompileStatus.STARTED) {
                    startedThreads++;
                }
            }
            decompileThreads.removeIf(thread -> thread.getDecompileStatus() == GifDecompileThread.DecompileStatus.SUCCESSFUL);
            Thread.yield();
        }
        System.out.println("Decompilation of the gifs is complete, check the result in the output folder.");
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.indexOf('.');
        return index == -1? null : filePath.substring(index).replace(".", "");
    }
}
