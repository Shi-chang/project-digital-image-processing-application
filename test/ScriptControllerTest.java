import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import images.ImageModel;
import imageview.ImageView;
import org.junit.Before;
import org.junit.Test;
import script.ScriptController;

/**
 * A JUnit test for the ScriptController.
 */
public class ScriptControllerTest {
  private StringBuilder modelLog;
  private ImageModel model;
  private StringBuilder viewLog;
  private ImageView view;
  private ScriptController controller;

  /**
   * Set up the variables before using them in the methods.
   */
  @Before
  public void setup() {
    modelLog = new StringBuilder();
    model = new MockImageModel(modelLog);

    viewLog = new StringBuilder();
    view = new MockImageView(viewLog);

    controller = new ScriptController(model);
    controller.setView(view);
  }

  /**
   * Test the first constructor.
   */
  @Test
  public void testFirstConstructor() {
    ScriptController controller = new ScriptController();
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
      new ScriptController(tempModel);
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the second constructor with valid argument.
   */
  @Test
  public void testSecondConstructorWithValidArgument() {
    ScriptController controller = new ScriptController(model);
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

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the getScriptAsInput method with valid argument.
   */
  @Test
  public void testGetScriptAsInput() {
    controller.getScriptAsInput();

    assertTrue(controller.getInput().equals("A1B2C3.."));

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Getting script: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the loadImage method when the view is null.
   */
  @Test
  public void testLoadImageWhenViewIsNull() {
    String fileName = "Some birds.png";
    ScriptController tempController = new ScriptController();
    tempController.loadImage(fileName);

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the loadImage method when the view is not null but the file name is null.
   */
  @Test
  public void testLoadImageWhenViewIsNotNullButFileNameIsNull() {
    String fileName = null;
    try {
      controller.loadImage(fileName);
      fail("An IllegalArgumentException should have been thrown.");
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the loadImage method when the view is not null but the file name is an empty string.
   */
  @Test
  public void testLoadImageWhenViewIsNotNullButFileNameIsEmptyString() {
    String fileName = "";
    try {
      controller.loadImage(fileName);
      fail("An IllegalArgumentException should have been thrown.");
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the loadImage method when the view is not null and the file name is valid.
   */
  @Test
  public void testLoadImageWhenViewAndFileNameAreNotNull() {
    String fileName = "Some birds.png";
    controller.loadImage(fileName);

    String expectedModelLog = "Loading image: Some birds.png" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the saveImage method when the view is null.
   */
  @Test
  public void testSaveImageWhenViewIsNull() {
    String fileName = "Some birds.png";
    ScriptController tempController = new ScriptController();
    tempController.saveImage(fileName);

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the saveImage method when the view is not null but the file name is null.
   */
  @Test
  public void testSaveImageWhenViewIsNotNullButFileNameIsNull() {
    String fileName = null;
    try {
      controller.saveImage(fileName);
      fail("An IllegalArgumentException should have been thrown.");
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the saveImage method when the view is not null but the file name is an empty string.
   */
  @Test
  public void testSaveImageWhenViewIsNotNullButFileNameIsEmptyString() {
    String fileName = "";
    try {
      controller.saveImage(fileName);
      fail("An IllegalArgumentException should have been thrown.");
    } catch (IllegalArgumentException e) {
      // this is expected
    }
  }

  /**
   * Test the saveImage method when the view is not null and the file name is valid.
   */
  @Test
  public void testSaveImageWhenViewAndFileNameAreNotNull() {
    String fileName = "Some birds.png";
    controller.saveImage(fileName);

    String expectedModelLog = "Saving image: Some birds.png" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the applyScript method when the input is empty.
   */
  @Test
  public void testEmptyInput() {
    controller.setInput("");
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error message. The script is empty." + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test loading image with the applyScript method.
   */
  @Test
  public void testLoadImageWithApplyScriptMethod() {
    String loadImageName = "Birds-at-fleetwood-park.png";
    String input = "load " + loadImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test loading image with the applyScript method and the image format is not correct.
   */
  @Test
  public void testLoadImageWithApplyScriptMethodWhenImageFormatNotCorrect() {
    String loadImageName = "Birds-at-fleetwood-park";
    String input = "load " + loadImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Format of the image should be \"jpg\" or"
        + " \"png\" or \"gif\" or \"bmp\"" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "load" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfLoadInApplyScriptMethod() {
    String loadImageName = "Birds-at-fleetwood-park.png";
    String input = "lOaD " + loadImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "load" is not followed by a filename in the applyScript method.
   */
  @Test
  public void testLoadWithoutFileName() {
    String loadImageName = "";
    String input = "load " + loadImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Should be: load filename"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying blur with the applyScript method.
   */
  @Test
  public void testBlurImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "blur"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying blur" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "blur" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfBlurInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "BluR"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying blur" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "blur" is followed by a filename in the applyScript method.
   */
  @Test
  public void testBlurWithFileName() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "blur"
        + "Birds-at-fleetwood-park.png" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Error Message. Invalid input on line 2."
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying sharpen with the applyScript method.
   */
  @Test
  public void testSharpenImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "sharpen"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "sharpen" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfSharpenInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "ShArPen"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "sharpen" is followed by a filename in the applyScript method.
   */
  @Test
  public void testSharpenWithFileName() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "sharpen"
        + "Birds-at-fleetwood-park.png" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Error Message. Invalid input on line 2."
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying greyscale with the applyScript method.
   */
  @Test
  public void testGreyscaleImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "greyscale"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying greyscale"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "greyscale" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfGreyscaleInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "GreyScalE"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying greyscale"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "greyscale" is followed by a filename in the applyScript method.
   */
  @Test
  public void testGreyscaleWithFileName() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "greyscale"
        + "Birds-at-fleetwood-park.png" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Error Message. Invalid input on line 2."
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying sepia with the applyScript method.
   */
  @Test
  public void testSepiaImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "sepia"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sepia" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "sepia" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfSepiaInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "SEPIa"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sepia" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "sepia" is followed by a filename in the applyScript method.
   */
  @Test
  public void testSepiaWithFileName() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "sepia"
        + "Birds-at-fleetwood-park.png" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Error Message. Invalid input on line 2."
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying dither with the applyScript method.
   */
  @Test
  public void testDitherImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "dither"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying dither" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "dither" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfDitherInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "DiThEr"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying dither" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "dither" is followed by a filename in the applyScript method.
   */
  @Test
  public void testDitherWithFileName() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "dither"
        + "Birds-at-fleetwood-park.png" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Error Message. Invalid input on line 2."
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test applying mosaic with the applyScript method.
   */
  @Test
  public void testMosaicImageWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "mosaic 5000"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog =
        "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator() + "Getting image data"
            + System.lineSeparator() + "Applying mosaic with the number of seeds at 5000"
            + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "mosaic" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfMosaicInApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "MOSaic 5000"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog =
        "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator() + "Getting image data"
            + System.lineSeparator() + "Applying mosaic with the number of seeds at 5000"
            + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "mosaic" is not followed by a number in the applyScript method.
   */
  @Test
  public void testMosaicNotFollowedByNumber() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "MOSaic abc"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator()
        + "Showing message: Error Message. Check line 2. Should be: mosaic number_of_seeds"
        + "(a positive integer)" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowH" is not followed by two numbers.
   */
  @Test
  public void testRainbowhNotFollowedByTwoNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowH"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator()
        + "Showing message: Error Message. Check line 2. "
        + "Should be: rainbowH width(positive integer) height(positive integer)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowH" is followed by two invalid numbers.
   */
  @Test
  public void testRainbowhFollowedByTwoInvalidNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowH 0 4"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator()
        + "Showing message: Error Message. Check line 2. "
        + "Should be: rainbowH width(positive integer) height(positive integer>6)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowH" is followed by two valid numbers.
   */
  @Test
  public void testRainbowhFollowedByTwoValidNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowH 500 300"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 500 height = 300 rainbow type = 0" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "rainbowH" is case insensitive.
   */
  @Test
  public void testRainbowhCaseSensitivity() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "RaInBoWh 500 300"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 500 height = 300 rainbow type = 0" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowV" is not followed by two numbers.
   */
  @Test
  public void testRainbowvNotFollowedByTwoNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowV"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator()
        + "Showing message: Error Message. Check line 2. "
        + "Should be: rainbowV width(positive integer>6) height(positive integer)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowH" is followed by two invalid numbers.
   */
  @Test
  public void testRainbowvFollowedByTwoInvalidNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowV 0 4"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator()
        + "Showing message: Error Message. Check line 2. "
        + "Should be: rainbowV width(positive integer>6) height(positive integer)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "rainbowV" is followed by two valid numbers.
   */
  @Test
  public void testRainbowvFollowedByTwoValidNumbers() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "rainbowV 500 300"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 500 height = 300 rainbow type = 1" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "rainbowV" is case insensitive.
   */
  @Test
  public void testRainbowvCaseSensitivity() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "RaInBoWv 500 300"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 500 height = 300 rainbow type = 1" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "checkboard" when the input line is invalid.
   */
  @Test
  public void testCheckBoardWhenInputInvalid() {
    String input = "checkboard" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. "
        + "Should be: checkboard height(positive integer>0) "
        + "number_of_squares(positive integer>0) "
        + "red_value_of_first_color(0-255) "
        + "green_value_of_first_color(0-255) "
        + "blue_value_of_first_color(0-255) "
        + "red_value_of_second_color(0-255) "
        + "green_value_of_second_color(0-255) "
        + "blue_value_of_second_color(0-255)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "checkboard" when the input line is valid.
   */
  @Test
  public void testCheckBoardWhenInputValid() {
    String input = "checkboard 500 50 255 255 255 0 0 0" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog =
        "Generating checkerboard total height = 500 bumberOfSquares = 50 first color = 255 255 255 "
        + "second color = 0 0 0" + System.lineSeparator() + "Getting image data" 
        + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "checkboard" is case insensitive.
   */
  @Test
  public void testCheckBoardCaseSensitivity() {
    String input = "CHeckBoarD 500 50 255 255 255 0 0 0" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog =
        "Generating checkerboard total height = 500 bumberOfSquares = 50 first color = 255 255 255 "
        + "second color = 0 0 0" + System.lineSeparator() + "Getting image data" 
        + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "norway" when the input is invalid.
   */
  @Test
  public void testNorwayWhenInputInvalid() {
    String input = "norway" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. "
        + "Should be: norway width(positive integer>=22)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "norway" when the input is valid.
   */
  @Test
  public void testNorwayWhenInputValid() {
    String input = "norway 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Norway with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "norway" when the input is case insensitive.
   */
  @Test
  public void testNorwayCaseSensitivity() {
    String input = "NORwaY 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Norway with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }


  /**
   * Test the keyword "greece" when the input is invalid.
   */
  @Test
  public void testGreeceWhenInputInvalid() {
    String input = "greece" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. "
        + "Should be: greece width(positive integer>=27)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "greece" when the input is valid.
   */
  @Test
  public void testGreeceWhenInputValid() {
    String input = "greece 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Greece with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "greece" when the input is case insensitive.
   */
  @Test
  public void testGreeceCaseSensitivity() {
    String input = "GReeCe 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Greece with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "swizerland" when the input is invalid.
   */
  @Test
  public void testSwizerlandWhenInputInvalid() {
    String input = "swizerland" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. "
        + "Should be: swizerland width(positive integer>=32)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "swizerland" when the input is valid.
   */
  @Test
  public void testSwizerlandWhenInputValid() {
    String input = "swizerland 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Swizerland with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "swizerland" is case insensitive.
   */
  @Test
  public void testSwizerlandCaseSensitivity() {
    String input = "SWIZerland 300" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Generating a national flag of Swizerland with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "edgeDetection" when the input is invalid.
   */
  @Test
  public void testEdgeDetectionWhenInputInvalid() {
    String input = "edgeDetection 100" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Should be: edge detection"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "edgeDetection" when the input is valid.
   */
  @Test
  public void testEdgeDetectionWhenInputValid() {
    String input = "edgeDetection" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Applying edge detection" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "edgeDetection" is case insensitive.
   */
  @Test
  public void testEdgeDetectionCaseSensitivity() {
    String input = "eDGEDetecTION" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Applying edge detection" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "greyscaleEnhancement" when the input is invalid.
   */
  @Test
  public void testGreyscaleEnhancementWhenInputInvalid() {
    String input = "greyscaleEnhancement 100" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Should be: greyscale enhancement"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "greyscaleEnhancement" when the input is valid.
   */
  @Test
  public void testGreyscaleEnhancementWhenInputValid() {
    String input = "greyscaleEnhancement" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Applying greyscale enhancement" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "greyscaleEnhancement" is case insensitive.
   */
  @Test
  public void testGreyscaleEnhancementCaseSensitivity() {
    String input = "greyscaleEnhancement" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Applying greyscale enhancement" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test the keyword "imageCropping" when the input is invalid.
   */
  @Test
  public void testImageCroppingWhenInputInvalid() {
    String input = "imageCropping" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Should be: imagecropping x y width height"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "imageCropping" is case insensitive.
   */
  @Test
  public void testimageCroppingCaseSensitivity() {
    String input = "imageCropping 0 0 100 150" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying image cropping x = 0 y = 0 width = 100 height = 150"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test whether the keyword "imageCropping" when the input is valid.
   */
  @Test
  public void testImageCroppingWhenInputValid() {
    String input = "imageCropping 0 0 100 150" + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying image cropping x = 0 y = 0 width = 100 height = 150"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test saving image with the applyScript method.
   */
  @Test
  public void testSaveImageWithApplyScriptMethod() {
    String saveImageName = "Birds-at-fleetwood-park-mosaic-5000.png";
    String input = "save " + saveImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Saving image: Birds-at-fleetwood-park-mosaic-5000.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test if the keyword "save" is truly case insensitive in the applyScript method.
   */
  @Test
  public void testCaseSensitivityOfSaveInApplyScriptMethod() {
    String saveImageName = "Birds-at-fleetwood-park-mosaic-5000.png";
    String input = "SAVe " + saveImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Saving image: Birds-at-fleetwood-park-mosaic-5000.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing message: Done. Script commands successfully applied!"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test when the keyword "save" is not followed by a filename in the applyScript method.
   */
  @Test
  public void testSaveWithoutFileName() {
    String saveImageName = "";
    String input = "SAVe " + saveImageName + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "";
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator()
        + "Showing message: Error Message. Check line 1. Should be: save filename(example.jpg)"
        + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }

  /**
   * Test a batch of commands with the applyScript method.
   */
  @Test
  public void testBatchOfCommandsWithApplyScriptMethod() {
    String input = "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "blur"
        + System.lineSeparator() + "save Birds-at-fleetwood-park-blurred-1.png"
        + System.lineSeparator() + "blur" + System.lineSeparator()
        + "save Birds-at-fleetwood-park-blurred-2.png" + System.lineSeparator()
        + "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "sharpen"
        + System.lineSeparator() + "save Birds-at-fleetwood-park-sharpen-1.png"
        + System.lineSeparator() + "sharpen" + System.lineSeparator()
        + "save Birds-at-fleetwood-park-sharpen-2.png" + System.lineSeparator()
        + "load Birds-at-fleetwood-park.png" + System.lineSeparator() + "greyscale"
        + System.lineSeparator() + "save Birds-at-fleetwood-park-greyscale.png"
        + System.lineSeparator() + "load Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "sepia" + System.lineSeparator() + "save Birds-at-fleetwood-park-sepia.png"
        + System.lineSeparator() + "load Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "dither" + System.lineSeparator() + "save Birds-at-fleetwood-park-dither.png"
        + System.lineSeparator() + "load Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "mosaic 4000" + System.lineSeparator() + "save Birds-at-fleetwood-park-mosaic-4000.png"
        + System.lineSeparator() + "load Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "mosaic 8000" + System.lineSeparator() + "save Birds-at-fleetwood-park-mosaic-8000.png"
        + System.lineSeparator() + "load Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "mosaic 15000" + System.lineSeparator() + "save Birds-at-fleetwood-park-mosaic-15000.png"
        + System.lineSeparator() + "rainbowH 500 300" + System.lineSeparator() + "save rainbowH.png"
        + System.lineSeparator() + "rainbowV 200 500" + System.lineSeparator() + "save rainbowV.png"
        + System.lineSeparator() + "checkboard 500 25 255 255 255 0 0 0" + System.lineSeparator()
        + "save checkboard.png" + System.lineSeparator() + "norway 300" + System.lineSeparator()
        + "save norway.png" + System.lineSeparator() + "greece 300" + System.lineSeparator()
        + "save greece.png" + System.lineSeparator() + "swizerland 300" + System.lineSeparator()
        + "save swizerland.png" + System.lineSeparator() + "edgeDetection" + System.lineSeparator()
        + "save edgeDetection.png" + System.lineSeparator() + "greyscaleEnhancement"
        + System.lineSeparator() + "save greyscaleEnhancement.png" + System.lineSeparator()
        + "imageCropping 0 0 120 100" + System.lineSeparator() + "save imageCropping.png"
        + System.lineSeparator();
    controller.setInput(input);
    controller.applyScript();

    String expectedModelLog = "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying blur" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-blurred-1.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying blur" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-blurred-2.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-sharpen-1.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sharpen"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-sharpen-2.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying greyscale"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-greyscale.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying sepia" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-sepia.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Applying dither" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-dither.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Applying mosaic with the number of seeds at 4000" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-mosaic-4000.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Applying mosaic with the number of seeds at 8000" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-mosaic-8000.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Loading image: Birds-at-fleetwood-park.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Applying mosaic with the number of seeds at 15000" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Saving image: Birds-at-fleetwood-park-mosaic-15000.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 500 height = 300 rainbow type = 0" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Saving image: rainbowH.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Generating rainbow: width = 200 height = 500 rainbow type = 1" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Saving image: rainbowV.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Generating checkerboard total height = 500 bumberOfSquares = 25 "
        + "first color = 255 255 255 second color = 0 0 0"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: checkboard.png" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Generating a national flag of Norway with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: norway.png" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Generating a national flag of Greece with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: greece.png" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Generating a national flag of Swizerland with width at 300"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Saving image: swizerland.png" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Applying edge detection" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Saving image: edgeDetection.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Applying greyscale enhancement" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Saving image: greyscaleEnhancement.png" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Getting image data"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator()
        + "Applying image cropping x = 0 y = 0 width = 120 height = 100" + System.lineSeparator()
        + "Getting image data" + System.lineSeparator() + "Saving image: imageCropping.png"
        + System.lineSeparator() + "Getting image data" + System.lineSeparator();
    assertEquals(expectedModelLog, modelLog.toString());

    String expectedViewLog = "Setting ScriptFeatures: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator() + "Showing image: "
        + System.lineSeparator() + "Showing image: " + System.lineSeparator()
        + "Showing message: Done. Script commands successfully applied!" + System.lineSeparator();
    assertEquals(expectedViewLog, viewLog.toString());
  }
}
