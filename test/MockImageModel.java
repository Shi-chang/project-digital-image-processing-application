import images.ImageModel;

/**
 * A mock class for the model of the image processing program.
 */
public class MockImageModel implements ImageModel {
  private StringBuilder modelLog;

  /**
   * Constructor for the mock.
   */
  public MockImageModel() {}

  /**
   * Constructor for the mock.
   * 
   * @param modelLog the model log that records messages
   */
  public MockImageModel(StringBuilder modelLog) {
    this.modelLog = modelLog;
  }

  /**
   * Mocks loading an image into the image model.
   * 
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong loading
   *         the image
   */
  @Override
  public void loadImage(String filename) throws IllegalArgumentException {
    this.modelLog.append("Loading image: " + filename + System.lineSeparator());
  }

  /**
   * Mocks saving the data in the image model to a file.
   * 
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong saving
   *         the file
   */
  @Override
  public void saveImage(String filename) throws IllegalArgumentException {
    this.modelLog.append("Saving image: " + filename + System.lineSeparator());
  }

  /**
   * Mocks applying the blur filter to the data in the image model.
   */
  @Override
  public void applyBlur() {
    this.modelLog.append("Applying blur" + System.lineSeparator());
  }

  /**
   * Mocks applying the sharpen filter to the data in the image model.
   */
  @Override
  public void applySharpen() {
    this.modelLog.append("Applying sharpen" + System.lineSeparator());
  }

  /**
   * Mocks applying the greyscale color transformation to the data in the image model.
   */
  @Override
  public void applyGreyscale() {
    this.modelLog.append("Applying greyscale" + System.lineSeparator());
  }

  /**
   * Mocks applying the sepia color transformation to the data in the image model.
   */
  @Override
  public void applySepia() {
    this.modelLog.append("Applying sepia" + System.lineSeparator());
  }

  /**
   * Mocks applying the dithering effect to the data in the image model.
   */
  @Override
  public void applyDither() {
    this.modelLog.append("Applying dither" + System.lineSeparator());
  }

  /**
   * Mocks applying the mosaic effect to the data in the image model.
   * 
   * @param seeds the number of seeds to use in the mosaic
   * @throws IllegalArgumentException if the number of seeds is not positive
   */
  @Override
  public void applyMosaic(int seeds) throws IllegalArgumentException {
    this.modelLog
        .append("Applying mosaic with the number of seeds at " + seeds + System.lineSeparator());
  }

  /**
   * Mocks getting the data of this image.
   */
  @Override
  public int[][][] getImageData() {
    this.modelLog.append("Getting image data" + System.lineSeparator());
    return new int[199][301][3];
  }

  /**
   * Mocks generating a rainbow.
   * 
   * @param width the width of the rainbow
   * @param height the height of the rainbow
   * @param rainbowType the type of the rainbow to be generated with 0 being the horizontal type and
   *        1 being the vertical type
   */
  @Override
  public void generateRainbow(int width, int height, int rainbowType) {
    this.modelLog.append("Generating rainbow: width = " + width + " height = " + height
        + " rainbow type = " + rainbowType + System.lineSeparator());
  }

  /**
   * Mock applying edge detection.
   */
  @Override
  public void applyEdgeDetection() {
    this.modelLog.append("Applying edge detection" + System.lineSeparator());
  }

  /**
   * Mocks applying greyscale enhancement.
   */
  @Override
  public void applyGreyscaleEnhancement() {
    this.modelLog.append("Applying greyscale enhancement" + System.lineSeparator());

  }

  /**
   * Mocks applying image cropping.
   * 
   * @return a new ImageModel representing the cropped image
   */
  @Override
  public ImageModel applyImageCropping(int x, int y, int width, int height) {
    this.modelLog.append("Applying image cropping " + "x = " + x + " y = " + y + " width = " + width
        + " height = " + height + System.lineSeparator());
    return new MockImageModel(this.modelLog);
  }

  /**
   * Mocks generating a checker board.
   * 
   * @param totalHeight the total height of the check board
   * @param numberOfSquares the number of squares of the check board
   * @param firstColor the first color of the check board
   * @param secondColor the second color of the check board
   */
  @Override
  public void generateCheckerBoard(int totalHeight, int numberOfSquares, int[] firstColor,
      int[] secondColor) {
    this.modelLog.append("Generating checkerboard " + "total height = " + totalHeight
        + " bumberOfSquares = " + numberOfSquares + " first color = " + firstColor[0] + " "
        + firstColor[1] + " " + firstColor[2] + " second color = " + secondColor[0] + " "
        + secondColor[1] + " " + +secondColor[2] + System.lineSeparator());
  }

  /**
   * Mocks generating a national flag of Norway.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfNorway(int width) {
    this.modelLog.append(
        "Generating a national flag of Norway with width at " + width + System.lineSeparator());
  }

  /**
   * Mocks generating a national flag of Greece.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfGreece(int width) {
    this.modelLog.append(
        "Generating a national flag of Greece with width at " + width + System.lineSeparator());
  }

  /**
   * Mocks generating a national flag of Swizerland.
   * 
   * @param width the width of the flag
   */
  @Override
  public void generateNationalFlagOfSwizerland(int width) {
    this.modelLog.append(
        "Generating a national flag of Swizerland with width at " + width + System.lineSeparator());
  }
}
