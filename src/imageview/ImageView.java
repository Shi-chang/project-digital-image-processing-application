package imageview;

import java.awt.Color;
import java.awt.Image;
import script.ScriptFeatures;

/**
 * This interface contains all the operations to be supported by the Graphical User Interface-based
 * view.
 */
public interface ImageView {
  /**
   * Return the x1.
   * 
   * @return the x1
   */
  public int getX1();

  /**
   * Return the y1.
   * 
   * @return the y1
   */
  public int getY1();

  /**
   * Return the x2.
   * 
   * @return the x2
   */
  public int getX2();

  /**
   * Return the y2.
   * 
   * @return the y2
   */
  public int getY2();

  /**
   * Accept a set of callbacks from the button and menu controller, and hook them up to
   * corresponding items.
   * 
   * @param f an implementation of the ButtonAndMenuFeatures interface
   */
  void setButtonAndMenuFeatures(ButtonAndMenuFeatures f);

  /**
   * Accept the callback from the applyScript button and menu controller, and hook it to the
   * corresponding action.
   * 
   * @param f an implementation of the ButtonAndMenuFeatures interface
   */
  void setScriptFeatures(ScriptFeatures f);

  /**
   * Return the script typed in by the user.
   * 
   * @return the script typed in by the user
   */
  String getScript();

  /**
   * Provide a dialog with a text field that allows the user to input data.
   * 
   * @param message the message shown on the dialogue box
   * @param title the title of the dialogue box
   * @return the string input by the user
   */
  public String getUserInput(String message, String title);

  /**
   * Provide a color chooser that allows the user to choose colors.
   * 
   * @param title the title of the dialog
   * @return the chosen colors
   */
  public Color getColorFromUser(String title);

  /**
   * Show the image on the view.
   * 
   * @param image the image to be shown
   */
  public void showImage(Image image);

  /**
   * Get the file name and path to be opened by the user.
   */
  public String getOpenImageFilePath();

  /**
   * Get the file name and path to be saved by the user.
   */
  public String getSaveImageFilePath();

  /**
   * Show error messages through a JOptionPane.
   * 
   * @param message the message to be shown
   */
  public void showMessage(String messageType, String message);


  /**
   * Show confirm dialog through a JOptionPane.
   * 
   * @param message the message to be shown
   */
  public int showConfirmDialog(String message);

  /**
   * Make the frame focusable and make request that this frame gets the focus.
   */
  public void resetFocus();
}
