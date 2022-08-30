package script;

import images.ImageModel;
import images.ImageUtilities;
import imageview.ImageView;
import java.util.Scanner;

/**
 * An implementation of the ScriptFeatures interface.
 */
public class ScriptController implements ScriptFeatures {
  private ImageModel model;
  private ImageView view;
  private String input;

  /**
   * Constructor for this class.
   */
  public ScriptController() {}

  /**
   * The constructor for this class.
   * 
   * @param model the model to use
   * @throws IllegalArgumentException if the input model is null
   */
  public ScriptController(ImageModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException(
          "The argument passed to the controller constructor is invalid.");
    }
    this.model = model;
  }

  /**
   * Return the model of this controller.
   * 
   * @return the model of this controller
   */
  public ImageModel getModel() {
    return model;
  }

  /**
   * Return the view of this controller.
   * 
   * @return the view of this controller
   */
  public ImageView getView() {
    return view;
  }

  /**
   * Return the input of this controller.
   * 
   * @return the input of this controller
   */
  public String getInput() {
    return input;
  }

  /**
   * Set the string input. This method and the field "input" is created so that in the controller
   * test the input string can be manipulated.
   * 
   * @param str the string input
   */
  public void setInput(String str) {
    this.input = str;
  }

  /**
   * Set the view for the controller.
   * 
   * @param v the view to use
   * @throws IllegalArgumentException if the input v is null
   */
  @Override
  public void setView(ImageView v) throws IllegalArgumentException {
    if (v == null) {
      throw new IllegalArgumentException("The argument passed to the setView method is invalid.");
    }
    this.view = v;
    this.view.setScriptFeatures(this);
  }

  /**
   * Set the script as the input for the controller.
   * 
   * @param inputScript the input script to be attached to the controller
   * @throws IllegalArgumentException if the argument is invalid
   */
  @Override
  public void getScriptAsInput() {
    if (view == null) {
      return;
    }
    this.input = view.getScript();
  }

  /**
   * Load an image into the image model.
   * 
   * @param filename the name of the file containing the image
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong loading
   *         the image
   */
  @Override
  public void loadImage(String fileName) throws IllegalArgumentException {
    if (view == null) {
      return;
    }
    if (fileName == null || "".equals(fileName)) {
      throw new IllegalArgumentException("Invalid image name passed to the controller.");
    }
    model.loadImage(fileName);
  }

  /**
   * Save the data in the image model to a file.
   * 
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong saving
   *         the file
   */
  @Override
  public void saveImage(String fileName) throws IllegalArgumentException {
    if (view == null) {
      return;
    }
    if (fileName == null || "".equals(fileName)) {
      throw new IllegalArgumentException("Invalid image name passed to the controller.");
    }
    model.saveImage(fileName);
  }

  /**
   * Ask the view to get the text script, ask the model to apply the commands and ask the view to
   * show the changed image.
   */
  @Override
  public void applyScript() {
    // Check if the input is empty
    if ("".equals(input)) {
      view.showMessage("Error message", "The script is empty.");
      return;
    }

    int lineCount = 0;
    try (Scanner in = new Scanner(input)) {
      while (in.hasNext()) {
        lineCount++;
        String currentLine = in.nextLine();
        String[] currentWords = currentLine.split(" ");
        String option = currentWords[0].toUpperCase();
        switch (option) {
          case "LOAD":
            // Confirm that the format of loading is correct
            if (currentWords.length != 2) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: load filename");
              return;
            }
            // Confirm that the format of the image is correct
            if (currentWords[1].contains(".jpg") || currentWords[1].contains(".png")
                || currentWords[1].contains(".gif") || currentWords[1].contains(".bmp")) {
              try {
                this.loadImage(currentWords[1]);
              } catch (IllegalArgumentException e) {
                view.showMessage("Oops", "The file " + currentWords[1]
                    + " does not exist! Please check line" + lineCount);
                return;
              }
            } else {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Format of the image should be \"jpg\" or \"png\" or \"gif\" or \"bmp\"");
              return;
            }
            break;

          case "BLUR":
            if (currentWords.length != 1) {
              view.showMessage("Error Message", "Check line " + lineCount + ". Should be: blur");
              return;
            }
            model.applyBlur();
            break;

          case "SHARPEN":
            if (currentWords.length != 1) {
              view.showMessage("Error Message", "Check line " + lineCount + ". Should be: sharpen");
              return;
            }
            model.applySharpen();
            break;

          case "GREYSCALE":
            if (currentWords.length != 1) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: greyscale");
              return;
            }
            model.applyGreyscale();
            break;

          case "SEPIA":
            if (currentWords.length != 1) {
              view.showMessage("Error Message", "Check line " + lineCount + ". Should be: sepia");
              return;
            }
            model.applySepia();
            break;

          case "DITHER":
            if (currentWords.length != 1) {
              view.showMessage("Error Message", "Check line " + lineCount + ". Should be: dither");
              return;
            }
            model.applyDither();
            break;

          case "MOSAIC":
            if (currentWords.length != 2) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: mosaic number_of_seeds(a positive integer)");
              return;
            }
            try {
              Integer.parseInt(currentWords[1]);
            } catch (NumberFormatException e) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: mosaic number_of_seeds(a positive integer)");
              return;
            }
            model.applyMosaic(Integer.parseInt(currentWords[1]));
            break;

          case "RAINBOWH":
            if (currentWords.length != 3) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: rainbowH width(positive integer) height(positive integer)");
              return;
            }
            for (int i = 1; i < 3; i++) {
              try {
                Integer.parseInt(currentWords[i]);
              } catch (NumberFormatException e) {
                view.showMessage("Error Message", "Check line " + lineCount
                    + ". Should be: rainbowH width(positive integer) height(positive integer>6)");
                return;
              }
            }
            if (Integer.parseInt(currentWords[1]) < 1 || Integer.parseInt(currentWords[2]) < 7) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: rainbowH width(positive integer) height(positive integer>6)");
              return;
            }
            model.generateRainbow(Integer.parseInt(currentWords[1]),
                Integer.parseInt(currentWords[2]), 0);
            break;

          case "RAINBOWV":
            if (currentWords.length != 3) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: rainbowV width(positive integer>6) height(positive integer)");
              return;
            }
            for (int i = 1; i < 3; i++) {
              try {
                Integer.parseInt(currentWords[i]);
              } catch (NumberFormatException e) {
                view.showMessage("Error Message", "Check line " + lineCount
                    + ". Should be: rainbowV width(positive integer>6) height(positive integer)");
                return;
              }
            }
            if (Integer.parseInt(currentWords[1]) < 7 || Integer.parseInt(currentWords[2]) < 1) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: rainbowV width(positive integer>6) height(positive integer)");
              return;
            }
            model.generateRainbow(Integer.parseInt(currentWords[1]),
                Integer.parseInt(currentWords[2]), 1);
            break;

          case "CHECKBOARD":
            if (currentWords.length != 9) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: checkboard height(positive integer>0) "
                      + "number_of_squares(positive integer>0) "
                      + "red_value_of_first_color(0-255) " + "green_value_of_first_color(0-255) "
                      + "blue_value_of_first_color(0-255) " + "red_value_of_second_color(0-255) "
                      + "green_value_of_second_color(0-255) "
                      + "blue_value_of_second_color(0-255)");
              return;
            }
            for (int i = 1; i < 9; i++) {
              try {
                Integer.parseInt(currentWords[i]);
              } catch (NumberFormatException e) {
                view.showMessage("Error Message", "Check line " + lineCount
                    + ". Should be: checkboard height(positive integer>0) "
                    + "number_of_squares(positive integer>0) " + "red_value_of_first_color(0-255) "
                    + "green_value_of_first_color(0-255) " + "blue_value_of_first_color(0-255) "
                    + "red_value_of_second_color(0-255) " + "green_value_of_second_color(0-255) "
                    + "blue_value_of_second_color(0-255)");
                return;
              }
            }
            if (Integer.parseInt(currentWords[1]) < 1 || Integer.parseInt(currentWords[2]) < 1) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: checkboard height(positive integer>0) "
                      + "number_of_squares(positive integer>0) "
                      + "red_value_of_first_color(0-255) " + "green_value_of_first_color(0-255) "
                      + "blue_value_of_first_color(0-255) " + "red_value_of_second_color(0-255) "
                      + "green_value_of_second_color(0-255) "
                      + "blue_value_of_second_color(0-255)");
              return;
            }
            for (int i = 3; i < 9; i++) {
              if (Integer.parseInt(currentWords[i]) < 0
                  || Integer.parseInt(currentWords[i]) > 255) {
                view.showMessage("Error Message", "Check line " + lineCount
                    + ". Should be: checkboard height(positive integer>0) "
                    + "number_of_squares(positive integer>0) " + "red_value_of_first_color(0-255) "
                    + "green_value_of_first_color(0-255) " + "blue_value_of_first_color(0-255) "
                    + "red_value_of_second_color(0-255) " + "green_value_of_second_color(0-255) "
                    + "blue_value_of_second_color(0-255)");
                return;
              }
            }
            int[] firstColor = new int[] {Integer.parseInt(currentWords[3]),
                Integer.parseInt(currentWords[4]), Integer.parseInt(currentWords[5])};
            int[] secondColor = new int[] {Integer.parseInt(currentWords[6]),
                Integer.parseInt(currentWords[7]), Integer.parseInt(currentWords[8])};;
            model.generateCheckerBoard(Integer.parseInt(currentWords[1]),
                Integer.parseInt(currentWords[2]), firstColor, secondColor);
            break;

          case "NORWAY":
            if (currentWords.length != 2) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: norway width(positive integer>=22)");
              return;
            }
            try {
              Integer.parseInt(currentWords[1]);
            } catch (NumberFormatException e) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: norway width(positive integer>=22)");
              return;
            }
            if (Integer.parseInt(currentWords[1]) < 22) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: norway width(positive integer>=22)");
              return;
            }
            model.generateNationalFlagOfNorway(Integer.parseInt(currentWords[1]));
            break;

          case "GREECE":
            if (currentWords.length != 2) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: greece width(positive integer>=27)");
              return;
            }
            try {
              Integer.parseInt(currentWords[1]);
            } catch (NumberFormatException e) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: greece width(positive integer>=27)");
              return;
            }
            if (Integer.parseInt(currentWords[1]) < 27) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: greece width(positive integer>=27)");
              return;
            }
            model.generateNationalFlagOfGreece(Integer.parseInt(currentWords[1]));
            break;

          case "SWIZERLAND":
            if (currentWords.length != 2) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: swizerland width(positive integer>=32)");
              return;
            }
            try {
              Integer.parseInt(currentWords[1]);
            } catch (NumberFormatException e) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: swizerland width(positive integer>=32)");
              return;
            }
            if (Integer.parseInt(currentWords[1]) < 32) {
              view.showMessage("Error Message", "Check line " + lineCount
                  + ". Should be: swizerland width(positive integer>=32)");
              return;
            }
            model.generateNationalFlagOfSwizerland(Integer.parseInt(currentWords[1]));
            break;

          case "EDGEDETECTION":
            if (currentWords.length != 1) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: edge detection");
              return;
            }
            model.applyEdgeDetection();
            break;

          case "GREYSCALEENHANCEMENT":
            if (currentWords.length != 1) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: greyscale enhancement");
              return;
            }
            model.applyGreyscaleEnhancement();
            break;

          case "IMAGECROPPING":
            if (currentWords.length != 5) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: imagecropping x y width height");
              return;
            }
            for (int i = 1; i < 5; i++) {
              try {
                Integer.parseInt(currentWords[i]);
              } catch (NumberFormatException e) {
                view.showMessage("Error Message",
                    "Check line " + lineCount + ". Should be: imagecropping x y width height");
                return;
              }
            }
            for (int i = 1; i < 5; i++) {
              if (Integer.parseInt(currentWords[i]) < 0) {
                view.showMessage("Error Message",
                    "Check line " + lineCount + ". x, y, width and height should be > 0");
                return;
              }
            }
            if (Integer.parseInt(currentWords[1])
                + Integer.parseInt(currentWords[3]) > model.getImageData()[0].length) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". x + width should be <= image width");
              return;
            }
            if (Integer.parseInt(currentWords[2])
                + Integer.parseInt(currentWords[4]) > model.getImageData().length) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". y + height should be <= image height");
              return;
            }
            model = model.applyImageCropping(Integer.parseInt(currentWords[1]),
                Integer.parseInt(currentWords[2]), Integer.parseInt(currentWords[3]),
                Integer.parseInt(currentWords[4]));
            break;

          case "SAVE":
            if (currentWords.length != 2) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: save filename(example.jpg)");
              return;
            }
            if (!(currentWords[1].contains(".jpg") || currentWords[1].contains(".png")
                || currentWords[1].contains(".bmp") || currentWords[1].contains(".gif"))) {
              view.showMessage("Error Message",
                  "Check line " + lineCount + ". Should be: save filename(example.jpg)");
              return;
            }

            this.saveImage(currentWords[1]);
            break;

          default:
            view.showMessage("Error Message", "Invalid input on line " + lineCount + ".");
            return;
        }
        view.showImage(ImageUtilities.convertImage(model.getImageData()));
      }
    }
    view.showMessage("Done", "Script commands successfully applied!");
  }
}
