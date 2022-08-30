package imageview;

import images.ConcreteImageModel;
import images.ImageModel;
import script.ScriptController;

/**
 * Main program that drives the image processing program.
 */
public class Driver {
  /**
   * Starting method for the image processing program.
   * 
   * @param args not used
   */
  public static void main(String[] args) {
    ImageModel model = new ConcreteImageModel();
    ImageView view = new ConcreteImageView("Image Processor");
    ButtonAndMenuController buttonAndMenuController = new ButtonAndMenuController(model);
    ScriptController textController = new ScriptController(model);
    buttonAndMenuController.setView(view);
    textController.setView(view);
  }
}
