package com.ferra13671.GifCutter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GifDecompileThread extends Thread {
    private final Gif gif;
    private DecompileStatus decompileStatus = DecompileStatus.NOT_STARTED;

    public GifDecompileThread(Gif gif) {
        this.gif = gif;
    }

    @Override
    public void start() {
        decompileStatus = DecompileStatus.STARTED;
        super.start();
    }

    @Override
    public void run() {
        try {
            List<BufferedImage> imagesType1 = gif.decompileType1();
            List<BufferedImage> imagesType2 = gif.decompileType2();
            String folderName = SettingManager.getOutputFolder() + "/" + gif.getName() + "_Results";
            Files.createDirectory(Paths.get(folderName + "_Type1"));
            Files.createDirectory(Paths.get(folderName + "_Type2"));
            int count = 1;
            for (BufferedImage image : imagesType1) {
                ImageIO.write(image, "png", Paths.get(folderName + "_Type1" + "/" + gif.getName() + "_" + count + ".png").toFile());
                count++;
            }
            count = 1;
            for (BufferedImage image : imagesType2) {
                ImageIO.write(image, "png", Paths.get(folderName + "_Type2" + "/" + gif.getName() + "_" + count + ".png").toFile());
                count++;
            }
        } catch (IOException ignored) {
        } finally {
            decompileStatus = DecompileStatus.SUCCESSFUL;
        }
    }

    public DecompileStatus getDecompileStatus() {
        return decompileStatus;
    }

    public enum DecompileStatus {
        NOT_STARTED,
        STARTED,
        SUCCESSFUL
    }
}
