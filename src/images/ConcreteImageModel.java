package images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A concrete image model class that contains methods to load, modify and save images.
 */
public class ConcreteImageModel implements ImageModel {
  private int[][][] arrayOfImage;

  /**
   * A constructor that takes no arguments.
   */
  public ConcreteImageModel() {}

  /**
   * A constructor that takes an argument.
   * 
   * @param arrayOfImage the three dimensional array that stores the row, column, and rgb values of
   *        an image
   */
  public ConcreteImageModel(int[][][] arrayOfImage) {
    this.arrayOfImage = arrayOfImage;
  }

  /**
   * Load an image into the image model.
   * 
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong loading
   *         the image
   */
  @Override
  public void loadImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Invalid file name passed to model when loading.");
    }
    this.arrayOfImage = ImageUtilities.readImage(filename);
  }

  /**
   * Save the data in the image model to a file.
   * 
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong saving
   *         the file
   */
  @Override
  public void saveImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException("Invalid file name passed to model when saving.");
    }
    ImageUtilities.writeImage(this.arrayOfImage, filename);
  }

  /**
   * Return the data of this image model.
   */
  public int[][][] getImageData() {
    return this.arrayOfImage;
  }

  /**
   * A method that helps implement clamping.
   * 
   * @param value the value to be clamped to be between 0 and 255
   */
  private int clampValue(int value) {
    if (value < 0) {
      return 0;
    }
    if (value > 255) {
      return 255;
    }
    return value;
  }

  /**
   * Return the data created by filtering the three dimensional array of an image with the kernel
   * provided.
   *
   * @param arrayOfImage the three dimensional array of an image to be filtered
   * @param kernel the kernel matrix to be used in filtering
   * @return the data created by filtering the original data of the model with the kernel provided
   */
  private int[][][] filterImage(int[][][] arrayOfImage, double[][] kernel) {
    // Create a three-dimension array whose default values are 0
    int[][][] arrayOfFilteredImage = new int[arrayOfImage.length][arrayOfImage[0].length][3];
    int[][][] outputData = new int[arrayOfImage.length][arrayOfImage[0].length][3];
    int radiusOfKernel = kernel.length / 2;
    for (int i = radiusOfKernel; i < arrayOfImage.length - radiusOfKernel; i++) {
      for (int j = radiusOfKernel; j < arrayOfImage[0].length - radiusOfKernel; j++) {
        for (int k1 = 0; k1 < kernel.length; k1++) {
          for (int k2 = 0; k2 < kernel[0].length; k2++) {
            int[] correspondingPixel =
                arrayOfImage[i - radiusOfKernel + k1][j - radiusOfKernel + k2];
            arrayOfFilteredImage[i][j][0] += (int) (correspondingPixel[0] * kernel[k1][k2]);
            arrayOfFilteredImage[i][j][1] += (int) (correspondingPixel[1] * kernel[k1][k2]);
            arrayOfFilteredImage[i][j][2] += (int) (correspondingPixel[2] * kernel[k1][k2]);
          }
        }
        outputData[i][j][0] = this.clampValue(arrayOfFilteredImage[i][j][0]);
        outputData[i][j][1] = this.clampValue(arrayOfFilteredImage[i][j][1]);
        outputData[i][j][2] = this.clampValue(arrayOfFilteredImage[i][j][2]);
      }
    }
    return outputData;
  }

  /**
   * Return the data created by filtering the three dimensional array of an image with the kernel
   * provided and the resulting values are not clamped.
   *
   * @param arrayOfImage the three dimensional array of an image to be filtered
   * @param kernel the kernel matrix to be used in filtering
   * @return the data created by filtering the original data of the model with the kernel provided
   */
  private int[][][] filterImageWithoutClamping(int[][][] arrayOfImage, double[][] kernel) {
    // Create a three-dimension array whose default values are 0
    int[][][] arrayOfFilteredImage = new int[arrayOfImage.length][arrayOfImage[0].length][3];
    int radiusOfKernel = kernel.length / 2;
    for (int i = radiusOfKernel; i < arrayOfImage.length - radiusOfKernel; i++) {
      for (int j = radiusOfKernel; j < arrayOfImage[0].length - radiusOfKernel; j++) {
        for (int k1 = 0; k1 < kernel.length; k1++) {
          for (int k2 = 0; k2 < kernel[0].length; k2++) {
            int[] correspondingPixel =
                arrayOfImage[i - radiusOfKernel + k1][j - radiusOfKernel + k2];
            arrayOfFilteredImage[i][j][0] += (int) (correspondingPixel[0] * kernel[k1][k2]);
            arrayOfFilteredImage[i][j][1] += (int) (correspondingPixel[1] * kernel[k1][k2]);
            arrayOfFilteredImage[i][j][2] += (int) (correspondingPixel[2] * kernel[k1][k2]);
          }
        }
      }
    }
    return arrayOfFilteredImage;
  }

  /**
   * Apply the blur filter to the data in the image model.
   */
  @Override
  public void applyBlur() {
    double[][] kernel = {{1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}, {1.0 / 8.0, 1.0 / 4.0, 1.0 / 8.0},
        {1.0 / 16.0, 1.0 / 8.0, 1.0 / 16.0}};
    this.arrayOfImage = this.filterImage(this.arrayOfImage, kernel);
  }

  /**
   * Apply the sharpen filter to the data in the image model.
   */
  @Override
  public void applySharpen() {
    double[][] kernel = {{-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, -1.0 / 8.0},
        {-1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0, -1.0 / 8.0}};
    this.arrayOfImage = this.filterImage(this.arrayOfImage, kernel);
  }

  /**
   * Apply color transformations to the data in the image model.
   * 
   * @param kernel the kernel matrix to be used in color transformations
   */
  private void transformColorOfImage(double[][] kernel) {
    // Create a three-dimension array whose default values are 0
    int[][][] arrayOfColorTransformedImage =
        new int[this.arrayOfImage.length][this.arrayOfImage[0].length][3];
    for (int i = 0; i < this.arrayOfImage.length; i++) {
      for (int j = 0; j < this.arrayOfImage[0].length; j++) {

        arrayOfColorTransformedImage[i][j][0] = (int) (this.arrayOfImage[i][j][0] * kernel[0][0]
            + this.arrayOfImage[i][j][1] * kernel[0][1]
            + this.arrayOfImage[i][j][2] * kernel[0][2]);
        arrayOfColorTransformedImage[i][j][1] = (int) (this.arrayOfImage[i][j][0] * kernel[1][0]
            + this.arrayOfImage[i][j][1] * kernel[1][1]
            + this.arrayOfImage[i][j][2] * kernel[1][2]);
        arrayOfColorTransformedImage[i][j][2] = (int) (this.arrayOfImage[i][j][0] * kernel[2][0]
            + this.arrayOfImage[i][j][1] * kernel[2][1]
            + this.arrayOfImage[i][j][2] * kernel[2][2]);

        this.arrayOfImage[i][j][0] = this.clampValue(arrayOfColorTransformedImage[i][j][0]);
        this.arrayOfImage[i][j][1] = this.clampValue(arrayOfColorTransformedImage[i][j][1]);
        this.arrayOfImage[i][j][2] = this.clampValue(arrayOfColorTransformedImage[i][j][2]);
      }
    }
  }

  /**
   * Apply the greyscale color transformation to the data in the image model.
   */
  @Override
  public void applyGreyscale() {
    double[][] kernel =
      {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
    this.transformColorOfImage(kernel);
  }

  /**
   * Apply the sepia color transformation to the data in the image model.
   */
  @Override
  public void applySepia() {
    double[][] kernel = {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    this.transformColorOfImage(kernel);
  }

  /**
   * Apply the dithering effect to the data in the image model.
   */
  @Override
  public void applyDither() {
    // Apply the first step of dithering
    this.applyGreyscale();

    // Apply the second step of dithering
    for (int i = 1; i < this.arrayOfImage.length - 1; i++) {
      for (int j = 1; j < this.arrayOfImage[0].length - 1; j++) {
        for (int c = 0; c < 3; c++) {
          int oldColor = this.arrayOfImage[i][j][c];
          int newColor;
          // Set the new color for this pixel
          if (oldColor <= 127) {
            newColor = 0;
          } else {
            newColor = 255;
          }
          this.arrayOfImage[i][j][c] = newColor;
          // Modify the color for surrounding pixels
          int colorError = oldColor - newColor;
          this.arrayOfImage[i][j + 1][c] += (int) (colorError * 7.0 / 16.0);
          this.arrayOfImage[i + 1][j - 1][c] += (int) (colorError * 3.0 / 16.0);
          this.arrayOfImage[i + 1][j][c] += (int) (colorError * 5.0 / 16.0);
          this.arrayOfImage[i + 1][j + 1][c] += (int) (colorError * 1.0 / 16.0);
        }
      }
    }
  }

  /**
   * Calculate and return the distance between two points.
   * 
   * @param row1 the row number of the first point
   * @param column1 the column number of the first point
   * @param row2 the row number of the second point
   * @param column2 the column number of the second point
   * @return the distance between two points
   */
  private double calculateDistance(int row1, int column1, int row2, int column2) {
    return Math.sqrt((row1 - row2) * (row1 - row2) + (column1 - column2) * (column1 - column2));
  }

  /**
   * Apply the mosaic effect to the data in the image model.
   * 
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) throws IllegalArgumentException {
    if (seeds < 1) {
      throw new IllegalArgumentException("The number of seeds should be positive.");
    }
    // create a list containing randomly selected and distinct points as the seeds
    ArrayList<int[]> selectedPoints = new ArrayList<int[]>();
    int count = 0;
    while (count < seeds) {
      int randomRow = (int) (Math.random() * ((this.arrayOfImage.length - 1) - 0 + 1) + 0);
      int randomColumn = (int) (Math.random() * ((this.arrayOfImage[0].length - 1) - 0 + 1) + 0);
      int[] randomPoint = {randomRow, randomColumn};
      if (!selectedPoints.contains(randomPoint)) {
        selectedPoints.add(randomPoint);
        count++;
      }
    }
    // find the closest seed for each pixel and change the color of each pixel to the color of its
    // closest seed
    for (int i = 0; i < this.arrayOfImage.length; i++) {
      for (int j = 0; j < this.arrayOfImage[0].length; j++) {
        // get the location of the closest seed for each pixel
        double minimumDistanceBetweenSeedAndPixel = Double.POSITIVE_INFINITY;
        int[] locationOfClosestSeed = new int[2];
        for (int k = 0; k < selectedPoints.size(); k++) {
          double distanceBetweenSeedAndPixel =
              this.calculateDistance(i, j, selectedPoints.get(k)[0], selectedPoints.get(k)[1]);
          if (distanceBetweenSeedAndPixel < minimumDistanceBetweenSeedAndPixel) {
            minimumDistanceBetweenSeedAndPixel = distanceBetweenSeedAndPixel;
            locationOfClosestSeed = selectedPoints.get(k);
          }
        }
        // change the color of each pixel to the color of its closest seed
        for (int m = 0; m < 3; m++) {
          this.arrayOfImage[i][j][m] =
              this.arrayOfImage[locationOfClosestSeed[0]][locationOfClosestSeed[1]][m];
        }
      }
    }
  }

  /**
   * Find the maximum color values of the three dimensional array of an image model.
   * 
   * @return the maximum color values of the three dimensional array of an image model
   */
  private int[] findMax(int[][][] data) {
    int[] maximumValues = new int[] {0, 0, 0};
    for (int c = 0; c < 3; c++) {
      for (int i = 0; i < data.length; i++) {
        for (int j = 0; j < data[0].length; j++) {
          if (data[i][j][c] > maximumValues[c]) {
            maximumValues[c] = data[i][j][c];
          }
        }
      }
    }
    return maximumValues;
  }

  /**
   * Find the minimum color values of the three dimensional array of an image model.
   * 
   * @return the minimum color values of the three dimensional array of an image model
   */
  private int[] findMin(int[][][] data) {
    int[] minimumValues = new int[] {255, 255, 255};
    for (int c = 0; c < 3; c++) {
      for (int i = 0; i < data.length; i++) {
        for (int j = 0; j < data[0].length; j++) {
          if (data[i][j][c] < minimumValues[c]) {
            minimumValues[c] = data[i][j][c];
          }
        }
      }
    }
    return minimumValues;
  }

  /**
   * Apply edge detection filtering to the data of an image.
   * 
   * @param data the data of an image
   * @param kx the array of the first kernel
   * @param ky the array of the second kernel
   * @return a three dimension array of the model after edge detection filtering
   */
  private int[][][] edgeDetectionFilter(int[][][] data, double[][] kx, double[][] ky) {
    int[][][] gx = this.filterImageWithoutClamping(data, kx);
    int[][][] gy = this.filterImageWithoutClamping(data, ky);
    int[][][] gxy = new int[data.length][data[0].length][3];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data[0].length; j++) {
        for (int c = 0; c < 3; c++) {
          gxy[i][j][c] = (int) Math.sqrt(gx[i][j][c] * gx[i][j][c] + gy[i][j][c] * gy[i][j][c]);
        }
      }
    }
    return gxy;
  }

  /**
   * Apply edge detection to this image.
   */
  public void applyEdgeDetection() {
    double[][] kx = {{1.0, 0.0, -1.0}, {2.0, 0.0, -2.0}, {1.0, 0.0, -1.0}};
    double[][] ky = {{1.0, 2.0, 1.0}, {0.0, 0.0, 0.0}, {-1.0, -2.0, -1.0}};
    int[][][] gxy = this.edgeDetectionFilter(arrayOfImage, kx, ky);
    int[] maximumValues = this.findMax(gxy);
    int[] minimumValues = this.findMin(gxy);
    int[][][] tempData = new int[gxy.length][gxy[0].length][3];
    for (int i = 0; i < gxy.length; i++) {
      for (int j = 0; j < gxy[0].length; j++) {
        for (int c = 0; c < 3; c++) {
          tempData[i][j][c] =
              (gxy[i][j][c] - minimumValues[c]) * 255 / (maximumValues[c] - minimumValues[c]);
        }
      }
    }
    arrayOfImage = tempData;
    this.applyGreyscale();
  }

  /**
   * Apply greyscale enhancement to this image.
   */
  @Override
  public void applyGreyscaleEnhancement() {
    this.applyGreyscale();
    // Create a map to store the grey values and their corresponding occurrences in the image
    TreeMap<Integer, Integer> map1 = new TreeMap<Integer, Integer>();
    for (int i = 0; i < arrayOfImage.length; i++) {
      for (int j = 0; j < arrayOfImage[0].length; j++) {
        if (map1.containsKey(arrayOfImage[i][j][0])) {
          int newValue = map1.get(arrayOfImage[i][j][0]) + 1;
          map1.put(arrayOfImage[i][j][0], newValue);
        } else {
          map1.put(arrayOfImage[i][j][0], 1);
        }
      }
    }
    // Create a map to store the grey values and their frequency of occurrence
    TreeMap<Integer, Double> map2 = new TreeMap<Integer, Double>();
    int totalPixels = arrayOfImage.length * arrayOfImage[0].length;
    for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
      Integer newKey = entry.getKey();
      Double newValue = ((double) entry.getValue()) / totalPixels;
      map2.put(newKey, newValue);
    }
    // Create a map to store the grey values and their cumulative frequency of occurrence
    TreeMap<Integer, Double> map3 = new TreeMap<Integer, Double>();
    ArrayList<Integer> greyValues =
        (ArrayList<Integer>) map2.keySet().stream().collect(Collectors.toList());

    map3.put(greyValues.get(0), map2.get(greyValues.get(0)));
    for (int i = 1; i < greyValues.size(); i++) {
      Double newValue = 0.0;
      for (int j = 0; j <= i; j++) {
        newValue += map2.get(greyValues.get(j));
      }
      map3.put(greyValues.get(i), newValue);
    }
    // Create a map to store the grey values and their enhanced output values
    int greyLevel = 255;
    TreeMap<Integer, Integer> map4 = new TreeMap<Integer, Integer>();
    for (Map.Entry<Integer, Double> entry : map3.entrySet()) {
      int outputValues = (int) Math.round(entry.getValue() * greyLevel);
      map4.put(entry.getKey(), outputValues);
    }
    // Replace the original grey values of this model with the enhanced ones
    int[][][] enhancedValues = new int[arrayOfImage.length][arrayOfImage[0].length][3];
    for (int i = 0; i < arrayOfImage.length; i++) {
      for (int j = 0; j < arrayOfImage[0].length; j++) {
        for (int c = 0; c < 3; c++) {
          int enhancedValue = map4.get(arrayOfImage[i][j][0]);
          enhancedValues[i][j][c] = enhancedValue;
        }
      }
    }
    this.arrayOfImage = enhancedValues;
  }

  /**
   * Apply image cropping on the original image.
   * 
   * @param x1 the x coordinate of the upper left point of the cropping rectangle
   * @param y1 the y coordinate of the upper left point of the cropping rectangle
   * @param width the width of the cropping rectangle
   * @param height the height of the cropping rectangle
   * @return a new ImageModel representing the cropped image
   */
  @Override
  public ImageModel applyImageCropping(int x, int y, int width, int height) {
    BufferedImage bufferedImage =
        ImageUtilities.convertImage(this.arrayOfImage).getSubimage(x, y, width, height);

    int[][][] newData = new int[bufferedImage.getHeight()][bufferedImage.getWidth()][3];

    for (int i = 0; i < bufferedImage.getHeight(); i++) {
      for (int j = 0; j < bufferedImage.getWidth(); j++) {
        Color c = new Color(bufferedImage.getRGB(j, i));
        newData[i][j][0] = c.getRed();
        newData[i][j][1] = c.getGreen();
        newData[i][j][2] = c.getBlue();
      }
    }
    return new ConcreteImageModel(newData);
  }

  /**
   * Return an array containing the RGB values of red, orange, yellow, green, blue, indigo, and
   * violet.
   * 
   * @return an array containing the RGB values of red, orange, yellow, green, blue, indigo, and
   *         violet
   */
  private int[][] rainbowColors() {
    int[][] colors = new int[7][3];
    // Red
    colors[0] = new int[] {255, 0, 0};
    // Orange
    colors[1] = new int[] {255, 127, 0};
    // Yellow
    colors[2] = new int[] {255, 255, 0};
    // Green
    colors[3] = new int[] {0, 255, 0};
    // Blue
    colors[4] = new int[] {0, 0, 255};
    // Indigo
    colors[5] = new int[] {75, 0, 130};
    // Violet
    colors[6] = new int[] {148, 0, 211};
    return colors;
  }

  /**
   * Return a three-dimensional array containing the data for a horizontal stripped rainbow with the
   * specified width and height.
   * 
   * @param width the width of the rainbow
   * @param height the height of the rainbow
   * @return a three-dimensional array containing the data for a horizontal stripped rainbow with
   *         the specified width and height.
   */
  private int[][][] horizontalStrippedRainbow(int width, int height) {
    if (width < 1 || height < 7) {
      throw new IllegalArgumentException(
          "Invalid input: width should be at least 1 pixel and height should be at least 7 piexls"
              + " for a horizontal rainbow.");
    }

    int stripThickness = (int) Math.round(height / 7.0);
    int row = 7 * stripThickness;
    int column = width;
    int[][] colors = this.rainbowColors();
    int[][][] arrayOfHorizontalStrippedRainbows = new int[row][column][3];

    for (int m = 0; m < 7; m++) {
      for (int r = stripThickness * m; r < stripThickness * (m + 1); r++) {
        for (int c = 0; c < column; c++) {
          arrayOfHorizontalStrippedRainbows[r][c] = colors[m];
        }
      }
    }
    return arrayOfHorizontalStrippedRainbows;
  }

  /**
   * Return a three-dimensional array containing the data for a vertical stripped rainbow with the
   * specified width and height.
   * 
   * @param width the width of the rainbow
   * @param height the height of the rainbow
   * @return a three-dimensional array containing the data for a vertical stripped rainbow with the
   *         specified width and height.
   */
  private int[][][] verticalStrippedRainbow(int width, int height) {
    if (width < 7 || height < 1) {
      throw new IllegalArgumentException(
          "Invalid input: width should be at least 7 pixels and height should be at least 1 piexl"
              + " for a vertical rainbow.");
    }
    int row = height;
    int stripWidth = (int) Math.round(width / 7.0);
    int column = 7 * stripWidth;
    int[][] colors = this.rainbowColors();
    int[][][] arrayOfVerticalStrippedRainbows = new int[row][column][3];

    for (int r = 0; r < height; r++) {
      for (int m = 0; m < 7; m++) {
        for (int c = stripWidth * m; c < stripWidth * (m + 1); c++) {
          arrayOfVerticalStrippedRainbows[r][c] = colors[m];
        }
      }
    }
    return arrayOfVerticalStrippedRainbows;
  }

  /**
   * Change the data of this model to the data of a rainbow.
   * 
   * @param width the width of the rainbow
   * @param height the height of the rainbow
   * @param rainbowType the type of the rainbow to be generated with 0 being the horizontal type and
   *        1 being the vertical type
   */
  @Override
  public void generateRainbow(int width, int height, int rainbowType) {
    if (rainbowType == 0) {
      this.arrayOfImage = this.horizontalStrippedRainbow(width, height);
    } else if (rainbowType == 1) {
      this.arrayOfImage = this.verticalStrippedRainbow(width, height);
    } else {
      throw new IllegalArgumentException("Can only accept 0 or 1 for rainbowType.");
    }
  }

  /**
   * Change the data of this model to the data of a check board.
   * 
   * @param totalHeight the total height of the check board
   * @param numberOfSquares the number of squares of the check board
   * @param firstColor the first color of the check board
   * @param secondColor the second color of the check board
   */
  @Override
  public void generateCheckerBoard(int totalHeight, int numberOfSquares, int[] firstColor,
      int[] secondColor) {
    if (totalHeight < 1 || numberOfSquares < 1 || firstColor == null || secondColor == null) {
      throw new IllegalArgumentException("Invalid argument for the generateCheckerBoard method.");
    }

    int[][] colors = new int[][] {firstColor, secondColor};
    int squareHeight = (int) Math.round((double) totalHeight / numberOfSquares);
    int actualChceckBoardHeight = squareHeight * numberOfSquares;
    this.arrayOfImage = new int[actualChceckBoardHeight][actualChceckBoardHeight][3];

    for (int m = 0; m < numberOfSquares; m += 2) {
      // The even rows of the checkboard
      for (int row = m * squareHeight; row < (m + 1) * squareHeight; row++) {
        for (int n = 0; n < numberOfSquares; n++) {
          for (int column = n * squareHeight; column < (n + 1) * squareHeight; column++) {
            this.arrayOfImage[row][column] = colors[n % 2];
          }
        }
      }
      // The odd rows of the checkboard
      for (int row = (m + 1) * squareHeight; row < (m + 2) * squareHeight
          && row < actualChceckBoardHeight; row++) {
        for (int n = 0; n < numberOfSquares; n++) {
          for (int column = n * squareHeight; column < (n + 1) * squareHeight; column++) {
            this.arrayOfImage[row][column] = colors[(n + 1) % 2];
          }
        }
      }
    }
  }

  /**
   * Change the data of this model to the data of the national flag of Norway.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfNorway(int width) {
    if (width < 22) {
      throw new IllegalArgumentException("Width should be at least 22.");
    }
    // The colors of the flag(red, white, blue)
    int[][] colors = new int[][] {{186, 12, 47}, {255, 255, 255}, {0, 32, 91}};
    int scale = (int) Math.round(width / 22.0);
    int actualWidth = scale * 22;
    int actualHeight = scale * 16;
    this.arrayOfImage = new int[actualHeight][actualWidth][3];
    // Paint the red color
    for (int row = 0; row < actualHeight; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[0];
      }
    }
    // Paint the vertical and horizontal white stripes
    for (int row = 0; row < actualHeight; row++) {
      for (int column = 6 * scale; column < 10 * scale; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 6 * scale; row < 10 * scale; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    // Paint the vertical and horizontal blue stripes
    for (int row = 0; row < actualHeight; row++) {
      for (int column = 7 * scale; column < 9 * scale; column++) {
        this.arrayOfImage[row][column] = colors[2];
      }
    }
    for (int row = 7 * scale; row < 9 * scale; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[2];
      }
    }
  }

  /**
   * Change the data of this model to the data of the national flag of Greece.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfGreece(int width) {
    if (width < 27) {
      throw new IllegalArgumentException("Width should be at least 27.");
    }
    // The colors of the flag(cyan, white)
    int[][] colors = new int[][] {{0, 20, 137}, {255, 255, 255}};
    int scale = (int) Math.round(width / 27.0);
    int actualWidth = scale * 27;
    int actualHeight = scale * 18;
    this.arrayOfImage = new int[actualHeight][actualWidth][3];
    // Paint the cyan color
    for (int row = 0; row < actualHeight; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[0];
      }
    }
    // Paint the vertical white stripe
    for (int row = 0; row < 10 * scale; row++) {
      for (int column = 4 * scale; column < 6 * scale; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    // Paint the horizontal white stripes
    for (int row = 2 * scale; row < 4 * scale; row++) {
      for (int column = 10 * scale; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 4 * scale; row < 6 * scale; row++) {
      for (int column = 0; column < 10 * scale; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 6 * scale; row < 8 * scale; row++) {
      for (int column = 10 * scale; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 10 * scale; row < 12 * scale; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 14 * scale; row < 16 * scale; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
  }

  /**
   * Change the data of this model to the data of the national flag of Swizerland.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfSwizerland(int width) {
    if (width < 32) {
      throw new IllegalArgumentException("Width should be at least 32.");
    }
    // The colors of the flag(red, white)
    int[][] colors = new int[][] {{218, 41, 28}, {255, 255, 255}};
    int scale = (int) Math.round(width / 32.0);
    int actualWidth = scale * 32;
    int actualHeight = scale * 32;
    this.arrayOfImage = new int[actualHeight][actualWidth][3];
    // Paint the red color
    for (int row = 0; row < actualHeight; row++) {
      for (int column = 0; column < actualWidth; column++) {
        this.arrayOfImage[row][column] = colors[0];
      }
    }
    // Paint the white crossing
    for (int row = 6 * scale; row < 26 * scale; row++) {
      for (int column = 13 * scale; column < 19 * scale; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
    for (int row = 13 * scale; row < 19 * scale; row++) {
      for (int column = 6 * scale; column < 26 * scale; column++) {
        this.arrayOfImage[row][column] = colors[1];
      }
    }
  }
}
