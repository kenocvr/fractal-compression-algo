package com.example.fractalcompression;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FractalCompressionUtil {

    public static final int SIZE = 513;
    public static final double MAX_TIME = 10.0;
    public static final double VARIATION_FACTOR = 0.6;
    public static final double INITIAL_DISPLACEMENT = 1.0;

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new 
File("input.png"));
            double[][] heightmap = new double[SIZE][SIZE];
            heightmap[0][0] = heightmap[0][SIZE - 1] = heightmap[SIZE - 
1][0] = heightmap[SIZE - 1][SIZE - 1] = INITIAL_DISPLACEMENT;
            // generateFractalHeightmap(heightmap, SIZE - 1, MAX_TIME);

            // Perform the compression using the heightmap
            BufferedImage compressedImage = compressImage(originalImage, 
heightmap);
            ImageIO.write(compressedImage, "png", new 
File("compressed.png"));

            // Decompress the image using the heightmap
            BufferedImage decompressedImage = 
decompressImage(compressedImage, heightmap);
            ImageIO.write(decompressedImage, "png", new 
File("decompressed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage compressImage(BufferedImage originalImage, 
double[][] heightmap) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage compressedImage = new BufferedImage(width, height, 
BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelValue = (originalImage.getRGB(x, y) & 0xFF) + 
(int) (heightmap[x % SIZE][y % SIZE] * 128);
                compressedImage.setRGB(x, y, pixelValue << 16 | pixelValue 
<< 8 | pixelValue);
            }
        }

        return compressedImage;
    }

    public static BufferedImage decompressImage(BufferedImage 
compressedImage, double[][] heightmap) {
        int width = compressedImage.getWidth();
        int height = compressedImage.getHeight();
        BufferedImage decompressedImage = new BufferedImage(width, height, 
BufferedImage.TYPE_BYTE_GRAY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelValue = (compressedImage.getRGB(x, y) & 0xFF) - (int) (heightmap[x % SIZE][y % SIZE] * 128);
                decompressedImage.setRGB(x, y, pixelValue << 16 | pixelValue << 8 | pixelValue);
            }
        }

        return decompressedImage;
    }
}
