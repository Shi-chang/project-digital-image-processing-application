import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import images.ImageModel;
import imageview.ButtonAndMenuController;
import imageview.ImageView;
import org.junit.Before;
import org.junit.Test;

/**
 * A JUnit test for the ButtonAndMenuController.
 */
public class ButtonAndMenuControllerTest {
  private StringBuilder modelLog;
  private ImageModel model;
  private StringBuilder viewLog;
  private ImageView view;
  private ButtonAndMenuController controller;

  /**
   * Set up the variables before using them in the methods.
   */
  @Before
  public void setup() {
    modelLog = new StringBuilder();
    model = new MockImageModel(modelLog);

    viewLog = new StringBuilder();
    view = new MockImageView(viewLog);

    controller = new ButtonAndMenuController(model);
    controller.setView(view);
  }

  /**
   * Test the first constructor.
   */
  @Test
  public void testFirstConstructor() {
    ButtonAndMenuController controller = new ButtonAndMenuController();
    assertTrue(controller.getModel() == null);
    assertTrue(controller.getView() == null);
  }

  /**
   * Test the second constructor with invalid argument.
   */
  @Test
  public void testSecondConstructorWithInvalidArgument() {
    try {
      ImageModel tempModel = null;
      new ButtonAndMenuController(tempModel);
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the second constructor with valid argument.
   */
  @Test
  public void testSecondConstructorWithValidArgument() {
    ButtonAndMenuController controller = new ButtonAndMenuController(model);
    assertTrue(controller.getModel() == model);
    assertTrue(controller.getView() == null);
  }

  /**
   * Test the setView method with invalid argument.
   */
  @Test
  public void testSetViewWithInvalidArgument() {
    try {
      ImageView tempView = null;
      controller.setView(tempView);
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the setView method with valid argument.
   */
  @Test
  public void testSetViewWithValidArgument() {
    assertTrue(controller.getView() == view);

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the loadImage method.
   */
  @Test
  public void testLoadImage() {
    controller.loadImage();

    String expectedModelLog = "Loading image: " + "G6H7I8..." + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting the file path of the image to be opened: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the saveImage method when the data of the image model is empty.
   */
  @Test
  public void testSaveImageWithEmptyModel() {
    controller = new ButtonAndMenuController();
    controller.saveImage();
    
    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the saveImage method when the data of the image model is not empty.
   */
  @Test
  public void testSaveImageWithModelNotEmpty() {
    controller.saveImage();

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator()
        + "Getting the file path of the image to be saved: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying blur.
   */
  @Test
  public void testApplyingBlur() {
    String operationCommand = "blur";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Applying blur"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying sharpen.
   */
  @Test
  public void testApplyingSharpen() {
    String operationCommand = "sharpen";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying greyscale.
   */
  @Test
  public void testApplyingGreyscale() {
    String operationCommand = "greyscale";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Applying greyscale"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying sepia.
   */
  @Test
  public void testApplyingSepia() {
    String operationCommand = "sepia";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Applying sepia"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying dither.
   */
  @Test
  public void testApplyingDither() {
    String operationCommand = "dither";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Applying dither"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying mosaic.
   */
  @Test
  public void testApplyingMosaic() {
    String operationCommand = "mosaic";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator()
        + "Applying mosaic with the number of seeds at 321" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the number of seeds: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying horizontal rainbow.
   */
  @Test
  public void testApplyHorizontalRainbow() {
    String operationCommand = "rainbowH";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating rainbow: width = 321 height = 321 rainbow type = 0"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the width of the rainbow: "
            + System.lineSeparator() + "Getting user input: Input the height of the rainbow: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying vertical rainbow.
   */
  @Test
  public void testApplyVerticalRainbow() {
    String operationCommand = "rainbowV";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating rainbow: width = 321 height = 321 rainbow type = 1"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the width of the rainbow: "
            + System.lineSeparator() + "Getting user input: Input the height of the rainbow: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying checker board.
   */
  @Test
  public void testApplyCheckerBoard() {
    String operationCommand = "checkboard";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating checkerboard total height = 321 bumberOfSquares = 321 "
        + "first color = 0 255 0 second color = 0 255 0" + System.lineSeparator() 
        + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator()
        + "Getting user input: Input total height of the checkboard: " + System.lineSeparator()
        + "Getting user input: Input number of squares per side: " + System.lineSeparator()
        + "Getting color from the user and the title of the color chooser is Choose the first "
        + "color for the checkboard" + System.lineSeparator()
        + "Getting color from the user and the title of the color chooser is Choose the second "
        + "color for the checkboard" + System.lineSeparator() + "Showing image: " 
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying generating a national flag of Norway.
   */
  @Test
  public void testGeneratingFlagOfNorway() {
    String operationCommand = "norway";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating a national flag of Norway with width at 321"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the width of the flag: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying generating a national flag of Greece.
   */
  @Test
  public void testGeneratingFlagOfGreece() {
    String operationCommand = "greece";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating a national flag of Greece with width at 321"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the width of the flag: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying generating a national flag of Swizerland.
   */
  @Test
  public void testGeneratingFlagOfSwizerland() {
    String operationCommand = "swizerland";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Generating a national flag of Swizerland with width at 321"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog =
        "Setting ButtonAndMenuFeatures: " + System.lineSeparator() + "Reseting focus: "
            + System.lineSeparator() + "Getting user input: Input the width of the flag: "
            + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying edge detection.
   */
  @Test
  public void testApplyEdgeDetection() {
    String operationCommand = "edgeDetection";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator()
        + "Applying edge detection" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying greyscale enhancement.
   */
  @Test
  public void testApplyGreyscaleEnhancement() {
    String operationCommand = "greyscaleEnhancement";
    controller.applyOperations(operationCommand);

    String expectedModelLog = "Getting image data" + System.lineSeparator()
        + "Applying greyscale enhancement" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Showing image: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying image cropping.
   */
  @Test
  public void testApplyImageCropping() {
    controller.applyImageCropping();

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying image cropping x = 101 y = 104 width = 6 height = 6"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator() + "Getting x1" + System.lineSeparator()
        + "Getting y1" + System.lineSeparator() + "Getting x2" + System.lineSeparator()
        + "Getting y2" + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing confimation dialog and the message shown is Are you sure you want to crop?";
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying a batch of commands.
   */
  @Test
  public void testBatchOfCommands() {
    controller.loadImage();
    controller.applyOperations("blur");
    controller.applyOperations("sharpen");
    controller.applyOperations("greyscale");
    controller.applyOperations("sepia");
    controller.applyOperations("dither");
    controller.applyOperations("mosaic");
    controller.applyOperations("rainbowH");
    controller.applyOperations("rainbowV");
    controller.applyOperations("checkboard");
    controller.applyOperations("norway");
    controller.applyOperations("greece");
    controller.applyOperations("swizerland");
    controller.applyOperations("edgeDetection");
    controller.applyOperations("greyscaleEnhancement");
    controller.applyImageCropping();
    controller.saveImage();

    String expectedModelLog = "Loading image: G6H7I8..." + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying blur" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying greyscale" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator() + "Applying sepia"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying dither" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Applying mosaic with the number of seeds at 321" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Generating rainbow: width = 321 height = 321 rainbow type = 0"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 321 height = 321 rainbow type = 1" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator()
        + "Generating checkerboard total height = 321 bumberOfSquares = 321 first color = 0 255 0 "
        + "second color = 0 255 0"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating a national flag of Norway with width at 321" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Generating a national flag of Greece with width at 321"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating a national flag of Swizerland with width at 321" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Applying edge detection" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying greyscale enhancement"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Applying image cropping x = 101 y = 104 width = 6 height = 6" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ButtonAndMenuFeatures: " + System.lineSeparator()
        + "Reseting focus: " + System.lineSeparator()
        + "Getting the file path of the image to be opened: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input the number of seeds: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input the width of the rainbow: " + System.lineSeparator()
        + "Getting user input: Input the height of the rainbow: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input the width of the rainbow: " + System.lineSeparator()
        + "Getting user input: Input the height of the rainbow: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input total height of the checkboard: " + System.lineSeparator()
        + "Getting user input: Input number of squares per side: " + System.lineSeparator()
        + "Getting color from the user and the title of the color chooser is Choose the first "
        + "color for the checkboard" + System.lineSeparator()
        + "Getting color from the user and the title of the color chooser is Choose the second "
        + "color for the checkboard" + System.lineSeparator() + "Showing image: " 
        + System.lineSeparator() + "Getting user input: Input the width of the flag: " 
        + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input the width of the flag: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting user input: Input the width of the flag: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Getting x1" + System.lineSeparator() + "Getting y1" + System.lineSeparator()
        + "Getting x2" + System.lineSeparator() + "Getting y2" + System.lineSeparator()
        + "Showing image: " + System.lineSeparator()
        + "Showing confimation dialog and the message shown is Are you sure you want to crop?"
        + "Getting the file path of the image to be saved: "
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
    assertTrue(view.getSaveImageFilePath().equals("J9K10L11...."));
  }
}
