package com.ferra13671.GifCutter;

import com.ferra13671.GifCutter.gif.GIFImageReader;
import com.ferra13671.GifCutter.gif.GIFImageReaderSpi;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gif {
    private final File gif;

    public Gif(File gif) {
        this.gif = gif;
    }


    public List<BufferedImage> decompileType1() throws IOException {
        List<BufferedImage> copies = new ArrayList<>();
        List<BufferedImage> frames = decompileType2();
        copies.add(frames.removeFirst());
        for (BufferedImage frame : frames) {
            BufferedImage img = new BufferedImage(copies.getFirst().getWidth(),
                    copies.getFirst().getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.drawImage(copies.getLast(),0,0,null);
            g.drawImage(frame,0,0,null);
            copies.add(img);
        }
        return copies;
    }

    public List<BufferedImage> decompileType2() throws IOException {
        List<BufferedImage> frames = new ArrayList<>();
        ImageReader ir = new GIFImageReader(new GIFImageReaderSpi());
        ir.setInput(ImageIO.createImageInputStream(gif));
        for(int i = 0; i < ir.getNumImages(true); i++)
            frames.add(ir.read(i));
        return frames;
    }

    public String getName() {
        return gif.getName().replace("." + GifCutter.getFileExtension(gif.getName()), "");
    }
}
