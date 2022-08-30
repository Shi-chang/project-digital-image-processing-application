package images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Image utility class that has methods to read an image from file and write to a file.
 */
public class ImageUtilities {

  /**
   * Read an image from a file and convert it to a 3D array of integer values. The first dimension
   * (rows) represents the height, the second dimension (columns) represents the width, and the
   * third represents the number of channels in the image: the first value is the RED channel, the
   * second value is the GREEN channel, and the third is the BLUE channel. There is only ever 3
   * channels in our representation since we do not support transparent images.
   * 
   * @param filename the name of the file to be read
   * @return a 3D array of integer values
   * @throws IllegalArgumentException if the filename provided does not contain an image.
   */
  public static int[][][] readImage(String filename) throws IllegalArgumentException {
    if (filename == null || "".equals(filename)) {
      throw new IllegalArgumentException("Invalid filename provided for reading the image file.");
    }
    int[][][] result = null;
    try {
      BufferedImage input = ImageIO.read(new FileInputStream(filename));

      result = new int[input.getHeight()][input.getWidth()][3];

      for (int i = 0; i < input.getHeight(); i++) {
        for (int j = 0; j < input.getWidth(); j++) {
          int color = input.getRGB(j, i);
          Color c = new Color(color);
          result[i][j][0] = c.getRed();
          result[i][j][1] = c.getGreen();
          result[i][j][2] = c.getBlue();
        }
      }
    } catch (IOException ex) {
      throw new IllegalArgumentException("Something went wrong reading the image file.");
    }
    return result;
  }

  /**
   * Return the width of an image in a file.
   * 
   * @param filename the name of the file containing the image.
   * @return the width of the image in the file
   * @throws if the filename provided does not contain an image.
   */
  public static int getWidth(String filename) {
    if (filename == null || "".equals(filename)) {
      throw new IllegalArgumentException("Invalid filename provided for getting the width of file");
    }
    int width = 0;
    try {
      BufferedImage input = ImageIO.read(new FileInputStream(filename));
      width = input.getWidth();
    } catch (IOException ex) {
      throw new IllegalArgumentException(
          "Something went wrong getting the width from the image file");
    }
    return width;
  }

  /**
   * Return the height of an image in a file.
   * 
   * @param filename the name of the file containing the image.
   * @return the height of the image in the file
   * @throws if the filename provided does not contain an image.
   */
  public static int getHeight(String filename) throws IOException {
    if (filename == null || "".equals(filename)) {
      throw new IllegalArgumentException("Invalid filename provided for getting the width of file");
    }
    int height = 0;
    try {
      BufferedImage input = ImageIO.read(new FileInputStream(filename));
      height = input.getHeight();
    } catch (IOException ex) {
      throw new IllegalArgumentException(
          "Something went wrong getting the height from the image file");
    }
    return height;
  }

  /**
   * Writing an image to a file and convert it from a 3D array of integer values. The first
   * dimension (rows) represents the height, the second dimension (columns) represents the width,
   * and the third represents the number of channels in the image: the first value is the RED
   * channel, the second value is the GREEN channel, and the third is the BLUE channel. There is
   * only ever 3 channels in our representation since we do not support transparent images.
   * 
   * @param rgbData the 3D array of integer values
   * @param width the width of the image
   * @param height the height of the image
   * @param filename the name of the file to write the image to
   * @throws IllegalArgumentException if the rgbData or filename provided was invalid
   * @throws IllegalArgumentException if something went wrong when writing go the filename
   */
  public static void writeImage(int[][][] rgbData, String filename)
      throws IllegalArgumentException {
    if (rgbData == null) {
      throw new IllegalArgumentException("Invalid image data provided");
    }
    if (filename == null || "".equals(filename)) {
      throw new IllegalArgumentException("Invalid filename provided");
    }
    try {
      int height = rgbData.length;
      int width = rgbData[0].length;

      BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = rgbData[i][j][0];
          int g = rgbData[i][j][1];
          int b = rgbData[i][j][2];

          // color is stored in 1 integer, with the 4 bytes storing ARGB in that
          // order. Each of r,g,b are stored in 8 bits (hence between 0 and 255).
          // So we put them all in one integer by using bit-shifting << as below
          int color = (r << 16) + (g << 8) + b;
          output.setRGB(j, i, color);
        }
      }
      String extension = filename.substring(filename.indexOf(".") + 1);
      ImageIO.write(output, extension, new FileOutputStream(filename));
    } catch (IOException ex) {
      throw new IllegalArgumentException("Error occurred writing the data to the file");
    }
  }

  /**
   * Convert an image to a file and convert it from a 3D array of integer values. The first
   * dimension (rows) represents the height, the second dimension (columns) represents the width,
   * and the third represents the number of channels in the image: the first value is the RED
   * channel, the second value is the GREEN channel, and the third is the BLUE channel. There is
   * only ever 3 channels in our representation since we do not support transparent images.
   * 
   * @param rgbData the 3D array of integer values
   * @param width the width of the image
   * @param height the height of the image
   * @throws IllegalArgumentException if the rgbData provided was invalid
   */
  public static BufferedImage convertImage(int[][][] rgbData) throws IllegalArgumentException {
    if (rgbData == null) {
      throw new IllegalArgumentException("Invalid image data provided");
    }
    int height = rgbData.length;
    int width = rgbData[0].length;
    BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = rgbData[i][j][0];
        int g = rgbData[i][j][1];
        int b = rgbData[i][j][2];

        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    return output;
  }
}
