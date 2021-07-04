package br.com.spaciatto.imageresizer.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class ImageResizer {

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight) {

        // reads all image files in inputImagePath
        FileWalker fileWalker = new FileWalker();
        List<File> imageFiles = fileWalker.walk(inputImagePath);
        System.out.print(String.format("Loaded %d image files...", imageFiles.size()));
        imageFiles.forEach(f -> {
            try {
                imageResize(f, outputImagePath, scaledWidth, scaledHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        System.out.println(" done :)");
    }

    public static void resize(String inputImagePath,
                              String outputImagePath, double percent) {

        // reads all image files in inputImagePath
        FileWalker fileWalker = new FileWalker();
        List<File> imageFiles = fileWalker.walk(inputImagePath);
        System.out.println(String.format("Loaded %d image files...", imageFiles.size()));
        imageFiles.forEach(f -> {
            try {
                BufferedImage inputImage = ImageIO.read(f);
                int scaledWidth = (int) (inputImage.getWidth() * percent);
                int scaledHeight = (int) (inputImage.getHeight() * percent);

                imageResize(f, outputImagePath, scaledWidth, scaledHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private static void imageResize(File f,
                                    String outputImagePath, int scaledWidth, int scaledHeight)
            throws Exception {

        // reads input image
        BufferedImage inputImage = ImageIO.read(f);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName =  f.getName().substring(f.getName().lastIndexOf(".") + 1);

        // writes to output file
        File outputFile = new File(outputImagePath + f.getName());
        if (outputFile.exists()) {
            outputFile.delete();
        }
        ImageIO.write(outputImage, formatName, outputFile);

    }

}
