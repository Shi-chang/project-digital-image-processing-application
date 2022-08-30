package imageview;

import images.ImageModel;
import java.io.IOException;

/**
 * This interface contains the operations to be supported by the Graphical User Interface-based
 * controller.
 */
public interface ButtonAndMenuFeatures {
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
   * Create connection between Features and the view.
   * 
   * @param v the view that the implementation of this Features is connected to
   */
  void setView(ImageView v);

  /**
   * Tell the view to provide a JFileChooser to the user for file path, ask the model to load the
   * image and ask the view to show the loaded image.
   * 
   * @throws IOException if the file cannot be successfully loaded
   */
  void loadImage() throws IllegalArgumentException;

  /**
   * Tell the view to provide a JFileChooser to the user for file path and save the current model to
   * the file path and file name provided.
   * 
   * @throws IOException if the file cannot be successfully saved
   */
  void saveImage() throws IOException;

  /**
   * Exit the program.
   */
  void exit();

  /**
   * Apply image cropping.
   */
  public void applyImageCropping();
  
  /**
   * Apply operations to the image model and ask the view to show the changed model.
   * 
   * @param a batch of operation commands to be applied
   */
  void applyOperations(String operationCommand);
}
