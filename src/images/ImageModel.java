package images;

/**
 * An interface for the model of image modification. It contains the methods that can modify images.
 */
public interface ImageModel {

  /**
   * Load an image into the image model.
   * 
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong loading
   *         the image
   */
  public void loadImage(String filename) throws IllegalArgumentException;

  /**
   * Save the data in the image model to a file.
   * 
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong saving
   *         the file
   */
  public void saveImage(String filename) throws IllegalArgumentException;

  /**
   * Apply the blur filter to the data in the image model.
   */
  public void applyBlur();

  /**
   * Apply the sharpen filter to the data in the image model.
   */
  public void applySharpen();

  /**
   * Apply the greyscale color transformation to the data in the image model.
   */
  public void applyGreyscale();

  /**
   * Apply the sepia color transformation to the data in the image model.
   */
  public void applySepia();

  /**
   * Apply the dithering effect to the data in the image model.
   */
  public void applyDither();

  /**
   * Apply the mosaic effect to the data in the image model.
   * 
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  public void applyMosaic(int seeds) throws IllegalArgumentException;

  /**
   * Apply edge detection to this image.
   */
  public void applyEdgeDetection();

  /**
   * Apply greyscale enhancement to this image.
   */
  public void applyGreyscaleEnhancement();

  /**
   * Apply image cropping on the original image.
   * 
   * @param x1 the x coordinate of the upper left point of the cropping rectangle
   * @param y1 the y coordinate of the upper left point of the cropping rectangle
   * @param width the width of the cropping rectangle
   * @param height the height of the cropping rectangle
   * @return a new ImageModel representing the cropped image
   */
  public ImageModel applyImageCropping(int x, int y, int width, int height);

  /**
   * Return the data of this image model.
   * 
   * @return a three dimension integer array that represents the model of this image
   */
  public int[][][] getImageData();

  /**
   * Change the data of this model to the data of a rainbow.
   * 
   * @param width the width of the rainbow
   * @param height the height of the rainbow
   * @param rainbowType the type of the rainbow to be generated with 0 being the horizontal type and
   *        1 being the vertical type
   */
  public void generateRainbow(int width, int height, int rainbowType);

  /**
   * Change the data of this model to the data of a check board.
   * 
   * @param totalHeight the total height of the check board
   * @param numberOfSquares the number of squares of the check board
   * @param firstColor the first color of the check board
   * @param secondColor the second color of the check board
   */
  public void generateCheckerBoard(int totalHeight, int numberOfSquares, int[] firstColor,
      int[] secondColor);

  /**
   * Change the data of this model to the data of the national flag of Norway.
   * 
   * @param width the width of the flag
   */
  public void generateNationalFlagOfNorway(int width);

  /**
   * Change the data of this model to the data of the national flag of Greece.
   * 
   * @param width the width of the flag
   */
  public void generateNationalFlagOfGreece(int width);

  /**
   * Change the data of this model to the data of the national flag of Swizerland.
   * 
   * @param width the width of the flag
   */
  public void generateNationalFlagOfSwizerland(int width);
}
