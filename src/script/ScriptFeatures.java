package script;

import images.ImageModel;
import imageview.ImageView;

/**
 * This interface contains part of the operations to be supported by the Graphical User
 * Interface-based controller.
 */
public interface ScriptFeatures {
  /**
   * Return the model of this controller.
   * 
   * @return the model of this controller
   */
  public ImageModel getModel();

  /**
   * Return the view of this controller.
   * 
   * @return the view of this controller
   */
  public ImageView getView();

  /**
   * Return the input of this controller.
   * 
   * @return the input of this controller
   */
  public String getInput();
  
  /**
   * Set the string input.
   * 
   * @param str the string input
   */
  public void setInput(String str);
  
  /**
   * Set the view for the controller.
   * 
   * @param v the view to use
   */
  public void setView(ImageView v);

  /**
   * Set the script as the input for the controller.
   * 
   * @param inputScript the input script to be attached to the controller
   * @throws IllegalArgumentException if the argument is invalid
   */
  void getScriptAsInput();

  /**
   * Load an image into the image model.
   * 
   * @param filename the name of the file containing the image.
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong loading
   *         the image
   */
  public void loadImage(String fileName) throws IllegalArgumentException;

  /**
   * Save the data in the image model to a file.
   * 
   * @param filename the name of the file to save to
   * @throws IllegalArgumentException if the filename is invalid or if something goes wrong saving
   *         the file
   */
  public void saveImage(String fileName) throws IllegalArgumentException;

  /**
   * Ask the view to get the text script, ask the model to apply the commands and ask the view to
   * show the changed image.
   */
  void applyScript();
}
