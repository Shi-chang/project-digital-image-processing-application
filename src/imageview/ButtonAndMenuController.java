package imageview;

import images.ImageModel;
import images.ImageUtilities;
import java.awt.Color;
import java.io.IOException;
import java.util.Stack;

/**
 * An implementation of the ButtonAndMenuFeatures interface.
 */
public class ButtonAndMenuController implements ButtonAndMenuFeatures {
  private ImageModel model;
  private ImageView view;
  private Stack<ImageModel> modelStack;

  /**
   * None-argument constructor for this class.
   */
  public ButtonAndMenuController() {}

  /**
   * Constructor for this class.
   * 
   * @param model the model to use
   * @throws IllegalArgumentException if the input model is null
   */
  public ButtonAndMenuController(ImageModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException(
          "The argument passed to the controller constructor is invalid.");
    }
    this.model = model;
    modelStack = new Stack<ImageModel>();
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
    this.view.setButtonAndMenuFeatures(this);
    view.resetFocus();
  }

  /**
   * Tell the view to provide a JFileChooser to the user for file path, ask the model to load the
   * image and ask the view to show the loaded image.
   * 
   * @throws IOException if the file cannot be successfully loaded
   */
  @Override
  public void loadImage() throws IllegalArgumentException {
    if (view == null) {
      return;
    }
    String imageFilePath = view.getOpenImageFilePath();
    if (!("".equals(imageFilePath))) {
      model.loadImage(imageFilePath);
      view.showImage(ImageUtilities.convertImage(model.getImageData()));
    }
  }

  /**
   * Tell the view to provide a JFileChooser to the user for file path and file name and save the
   * data of the image model to the input file path and file name.
   * 
   * @throws IOException if the file cannot be successfully loaded
   */
  @Override
  public void saveImage() throws IllegalArgumentException {
    if (view == null) {
      return;
    }
    if (model.getImageData() == null) {
      view.showMessage("Error Message", "Please load the image first.");
      return;
    }
    String imageFilePath = view.getSaveImageFilePath();
    if (!("".equals(imageFilePath))) {
      ImageUtilities.writeImage(model.getImageData(), imageFilePath);
    }
  }

  /**
   * Exit the program.
   */
  @Override
  public void exit() {
    System.exit(0);
  }

  /**
   * Apply image cropping. Show the cropped image to the user and ask confirmation from the user. If
   * the user does not want to keep the cropped image, restore the previous image.
   */
  @Override
  public void applyImageCropping() {
    if (model.getImageData() == null) {
      view.showMessage("Oops", "Please load an image first.");
      return;
    }
    modelStack.push(model);
    // Allow cropping using any two points on the image by setting x, y as the upper left corner of
    // the rectangle
    int x1 = Math.max(0, Math.min(view.getX1(), model.getImageData()[0].length));
    int y1 = Math.max(0, Math.min(view.getY1(), model.getImageData().length));
    int x2 = Math.max(0, Math.min(view.getX2(), model.getImageData()[0].length));
    int y2 = Math.max(0, Math.min(view.getY2(), model.getImageData().length));
    int x = Math.min(x1, x2);
    int y = Math.min(y1, y2);
    int width = Math.abs(x2 - x1);
    int height = Math.abs(y2 - y1);

    if (width != 0 && height != 0) {
      model = model.applyImageCropping(x, y, width, height);
    } else {
      view.showMessage("Oops", "Zero cropping width or height. Please try again.");
      return;
    }

    if (model.getImageData() != null) {
      view.showImage(ImageUtilities.convertImage(model.getImageData()));
    }

    int option = view.showConfirmDialog("Are you sure you want to crop?");
    if (option != 0) {
      model = null;
      model = modelStack.pop();
      view.showImage(ImageUtilities.convertImage(model.getImageData()));
    }
  }

  /**
   * Apply operations to the image model and ask the view to show the changed model.
   * 
   * @param a batch of operation commands to be applied
   */
  @Override
  public void applyOperations(String operationCommand) {
    switch (operationCommand) {
      case "blur":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applyBlur();
        break;

      case "sharpen":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applySharpen();
        break;

      case "greyscale":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applyGreyscale();
        break;

      case "sepia":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applySepia();
        break;

      case "dither":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applyDither();
        break;

      case "mosaic":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        boolean validInput1 = false;
        String message1 = "Input the number of seeds: ";
        String title1 = "Number of seeds: ";
        // if the user inputs a valid number and clicks the "Confirm" button, apply this mosaic
        // operation; otherwise, do nothing.
        while (!validInput1) {
          String userInput = view.getUserInput(message1, title1);
          if (userInput == null) {
            validInput1 = true;
          } else {
            try {
              int seeds = Integer.parseInt(userInput);
              if (seeds > 0) {
                validInput1 = true;
                model.applyMosaic(seeds);
              }
            } catch (NumberFormatException e) {
              message1 = "The number of seeds should be a positive integer: ";
            }
          }
        }
        break;

      case "rainbowH":
        // If the user clicks "Confirm" on the dialog, get a width from the user until the width
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int widthOfRainbowH = 0;
        int heightOfRainbowH = 0;
        String inputWidthOfRainbowH = null;

        boolean validWidthOfRainbowH = false;
        String message2 = "Input the width of the rainbow: ";
        String title2 = "Width of Horizontal Stripped Rainbow";
        while (!validWidthOfRainbowH) {
          inputWidthOfRainbowH = view.getUserInput(message2, title2);
          if (inputWidthOfRainbowH == null) {
            validWidthOfRainbowH = true;
          } else {
            try {
              widthOfRainbowH = Integer.parseInt(inputWidthOfRainbowH);
              if (widthOfRainbowH >= 1) {
                validWidthOfRainbowH = true;
              } else {
                message2 = "Width should be an integer(>0). Try again: ";
              }
            } catch (NumberFormatException e) {
              message2 = "Width should be an integer(>0). Try again: ";
            }
          }
        }
        // If the user clicks "Confirm" on the dialog, get a height from the user until the height
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        if (inputWidthOfRainbowH != null) {
          // Get the height from the user
          boolean validHeightOfRainbowH = false;
          String message3 = "Input the height of the rainbow: ";
          String title3 = "Height of Horizontal Stripped Rainbow";
          while (!validHeightOfRainbowH) {
            String inputHeightOfRainbowH = view.getUserInput(message3, title3);
            if (inputHeightOfRainbowH == null) {
              validHeightOfRainbowH = true;
            } else {
              try {
                heightOfRainbowH = Integer.parseInt(inputHeightOfRainbowH);
                if (heightOfRainbowH >= 7 && widthOfRainbowH >= 1) {
                  validHeightOfRainbowH = true;
                  model.generateRainbow(widthOfRainbowH, heightOfRainbowH, 0);
                } else {
                  message3 = "Height should be an integer(>6)";
                }
              } catch (NumberFormatException e) {
                message3 = "Height should be an integer(>6)";
              }
            }
          }
        }
        break;

      case "rainbowV":
        // If the user clicks "Confirm" on the dialog, get a width from the user until the width
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int widthOfRainbowV = 0;
        int heightOfRainbowV = 0;
        String inputWidthOfRainbowV = null;
        boolean validInput4 = false;
        String message4 = "Input the width of the rainbow: ";
        String title4 = "Width of Horizontal Stripped Rainbow";
        while (!validInput4) {
          inputWidthOfRainbowV = view.getUserInput(message4, title4);
          if (inputWidthOfRainbowV == null) {
            validInput4 = true;
          } else {
            try {
              widthOfRainbowV = Integer.parseInt(inputWidthOfRainbowV);
              if (widthOfRainbowV >= 7) {
                validInput4 = true;
              } else {
                message4 = "Width should be an integer(>0). Try again: ";
              }
            } catch (NumberFormatException e) {
              message4 = "Width should be an integer(>0). Try again: ";
            }
          }
        }
        // If the user clicks "Confirm" on the dialog, get a height from the user until the height
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        if (inputWidthOfRainbowV != null) {
          boolean validInput5 = false;
          String message5 = "Input the height of the rainbow: ";
          String title5 = "Height of Horizontal Stripped Rainbow";
          while (!validInput5) {
            String userInputHeight = view.getUserInput(message5, title5);
            if (userInputHeight == null) {
              validInput5 = true;
            } else {
              try {
                heightOfRainbowV = Integer.parseInt(userInputHeight);
                if (heightOfRainbowV >= 7 && widthOfRainbowV >= 1) {
                  validInput5 = true;
                  model.generateRainbow(widthOfRainbowV, heightOfRainbowV, 1);
                } else {
                  message5 = "Height should be an integer(>6)";
                }
              } catch (NumberFormatException e) {
                message5 = "Height should be an integer(>6)";
              }
            }
          }
        }
        break;

      case "checkboard":
        // If the user clicks "Confirm" on the dialog, get a height from the user until the height
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int[] firstColor = new int[3];
        int[] secondColor = new int[3];
        int heightOfCheckBoard = 0;
        int numberOfSquares = 0;
        String inputHeightOfCheckBoard = null;
        boolean validInput6 = false;
        String message6 = "Input total height of the checkboard: ";
        String title6 = "Total height of checkboard";
        while (!validInput6) {
          inputHeightOfCheckBoard = view.getUserInput(message6, title6);
          if (inputHeightOfCheckBoard == null) {
            validInput6 = true;
          } else {
            try {
              heightOfCheckBoard = Integer.parseInt(inputHeightOfCheckBoard);
              if (heightOfCheckBoard >= 1) {
                validInput6 = true;
              } else {
                message6 = "Total height of checkboard should be an integer(>0). Try again: ";
              }
            } catch (NumberFormatException e) {
              message6 = "Total height of checkboard should be an integer(>0). Try again: ";
            }
          }
        }
        // If the user clicks "Confirm" on the dialog, get the number of squares from the user until
        // the height is valid. If the user clicks "Cancel" on the dialog, do nothing.
        if (inputHeightOfCheckBoard != null) {
          boolean validInput7 = false;
          String message7 = "Input number of squares per side: ";
          String title7 = "Number of squares per side";
          while (!validInput7) {
            String inputNumberOfSquares = view.getUserInput(message7, title7);
            if (inputNumberOfSquares == null) {
              validInput7 = true;
            } else {
              try {
                numberOfSquares = Integer.parseInt(inputNumberOfSquares);
                if (heightOfCheckBoard >= 1 && numberOfSquares >= 1) {
                  validInput7 = true;
                } else {
                  message7 = "Number of squares should be an integer(>0). Try again: ";
                }
              } catch (NumberFormatException e) {
                message7 = "Number of squares should be an integer(>0). Try again: ";
              }
            }
          }
        }
        // If the user clicks "Confirm" on the dialog, get the first color. If the user clicks
        // "Cancel" on the dialog, do nothing.
        Color firstColorChosenByUser =
            view.getColorFromUser("Choose the first color for the checkboard");
        if (firstColorChosenByUser != null) {
          firstColor = new int[] {firstColorChosenByUser.getRed(),
              firstColorChosenByUser.getGreen(), firstColorChosenByUser.getBlue()};

          Color secondColorChosenByUser =
              view.getColorFromUser("Choose the second color for the checkboard");
          if (secondColorChosenByUser != null) {
            secondColor = new int[] {secondColorChosenByUser.getRed(),
                secondColorChosenByUser.getGreen(), secondColorChosenByUser.getBlue()};
            model.generateCheckerBoard(heightOfCheckBoard, numberOfSquares, firstColor,
                secondColor);
          }
        }
        break;

      case "norway":
        // If the user clicks "Confirm" on the dialog, get the input from the user until the number
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int widthOfNorwayFlag = 0;
        String inputWidthOfNorwayFlag = null;

        boolean validWidthOfNorwayFlag = false;
        String message8 = "Input the width of the flag: ";
        String title8 = "Width of Flag";
        while (!validWidthOfNorwayFlag) {
          inputWidthOfNorwayFlag = view.getUserInput(message8, title8);
          if (inputWidthOfNorwayFlag == null) {
            validWidthOfNorwayFlag = true;
          } else {
            try {
              widthOfNorwayFlag = Integer.parseInt(inputWidthOfNorwayFlag);
              if (widthOfNorwayFlag >= 22) {
                validWidthOfNorwayFlag = true;
                model.generateNationalFlagOfNorway(widthOfNorwayFlag);
              } else {
                message8 = "Width should be an integer(>=22). Try again: ";
              }
            } catch (NumberFormatException e) {
              message8 = "Width should be an integer(>=22). Try again: ";
            }
          }
        }
        break;

      case "greece":
        // If the user clicks "Confirm" on the dialog, get the input from the user until the number
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int widthOfGreeceFlag = 0;
        String inputWidthOfGreeceFlag = null;

        boolean validWidthOfGreeceFlag = false;
        String message9 = "Input the width of the flag: ";
        String title9 = "Width of Flag";
        while (!validWidthOfGreeceFlag) {
          inputWidthOfGreeceFlag = view.getUserInput(message9, title9);
          if (inputWidthOfGreeceFlag == null) {
            validWidthOfGreeceFlag = true;
          } else {
            try {
              widthOfGreeceFlag = Integer.parseInt(inputWidthOfGreeceFlag);
              if (widthOfGreeceFlag >= 27) {
                validWidthOfGreeceFlag = true;
                model.generateNationalFlagOfGreece(widthOfGreeceFlag);
              } else {
                message9 = "Width should be an integer(>=27). Try again: ";
              }
            } catch (NumberFormatException e) {
              message9 = "Width should be an integer(>=27). Try again: ";
            }
          }
        }
        break;

      case "swizerland":
        // If the user clicks "Confirm" on the dialog, get the input from the user until the number
        // is valid. If the user clicks "Cancel" on the dialog, do nothing.
        int widthOfSwizerlandFlag = 0;
        String inputWidthOfSwizerlandFlag = null;

        boolean validWidthOfSwizerlandFlag = false;
        String message10 = "Input the width of the flag: ";
        String title10 = "Width of Flag";
        while (!validWidthOfSwizerlandFlag) {
          inputWidthOfSwizerlandFlag = view.getUserInput(message10, title10);
          if (inputWidthOfSwizerlandFlag == null) {
            validWidthOfSwizerlandFlag = true;
          } else {
            try {
              widthOfSwizerlandFlag = Integer.parseInt(inputWidthOfSwizerlandFlag);
              if (widthOfSwizerlandFlag >= 32) {
                validWidthOfSwizerlandFlag = true;
                model.generateNationalFlagOfSwizerland(widthOfSwizerlandFlag);
              } else {
                message10 = "Width should be an integer(>=32). Try again: ";
              }
            } catch (NumberFormatException e) {
              message10 = "Width should be an integer(>=32). Try again: ";
            }
          }
        }
        break;

      case "edgeDetection":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applyEdgeDetection();
        break;

      case "greyscaleEnhancement":
        if (model.getImageData() == null) {
          view.showMessage("Oops", "Please load an image first.");
          break;
        }
        model.applyGreyscaleEnhancement();
        break;

      default:
        // do nothing here
    }
    if (model.getImageData() != null) {
      view.showImage(ImageUtilities.convertImage(model.getImageData()));
    }
  }
}
