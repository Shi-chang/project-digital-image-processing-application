import imageview.ButtonAndMenuFeatures;
import imageview.ImageView;
import java.awt.Color;
import java.awt.Image;
import script.ScriptFeatures;

/**
 * A mock class for the the view of the image processing program.
 */
public class MockImageView implements ImageView {
  private StringBuilder viewLog;

  /**
   * Constructor for this class.
   * 
   * @param viewLog the view log that records messages
   */
  public MockImageView(StringBuilder viewLog) {
    this.viewLog = viewLog;
  }

  /**
   * Mocks setting ButtonAndMenuFeatures.
   * 
   * @param f an implementation of the ButtonAndMenuFeatures interface
   */
  @Override
  public void setButtonAndMenuFeatures(ButtonAndMenuFeatures f) {
    this.viewLog.append("Setting ButtonAndMenuFeatures: " + System.lineSeparator());
  }

  /**
   * Mocks setting TextFeatures.
   * 
   * @param f an implementation of the ButtonAndMenuFeatures interface
   */
  @Override
  public void setScriptFeatures(ScriptFeatures f) {
    this.viewLog.append("Setting ScriptFeatures: " + System.lineSeparator());
  }

  /**
   * Mocks returning the script typed in by the user.
   * 
   * @return a string
   */
  @Override
  public String getScript() {
    this.viewLog.append("Getting script: " + System.lineSeparator());
    return "A1B2C3..";
  }

  /**
   * Mocks getting input from the user.
   * 
   * @param message the message to be shown
   * @return a string input by the user
   */
  @Override
  public String getUserInput(String message, String title) {
    this.viewLog.append("Getting user input: " + message + System.lineSeparator());
    return "321";
  }

  /**
   * Mocks showing the image on the view.
   * 
   * @param image the image to be shown
   */
  @Override
  public void showImage(Image image) {
    this.viewLog.append("Showing image: " + System.lineSeparator());
  }

  /**
   * Mocks getting the file name and path to be opened by the user.
   */
  @Override
  public String getOpenImageFilePath() {
    this.viewLog
        .append("Getting the file path of the image to be opened: " + System.lineSeparator());
    return "G6H7I8...";
  }

  /**
   * Mocks getting the file name and path to be saved by the user.
   */
  @Override
  public String getSaveImageFilePath() {
    this.viewLog
        .append("Getting the file path of the image to be saved: " + System.lineSeparator());
    return "J9K10L11....";
  }

  /**
   * Mocks showing error messages.
   * 
   * @param message the message to be shown
   */
  @Override
  public void showMessage(String messageType, String message) {
    this.viewLog
        .append("Showing message: " + messageType + ". " + message + System.lineSeparator());
  }

  /**
   * Mocks making the frame focusable and make request that this frame gets the focus.
   */
  @Override
  public void resetFocus() {
    this.viewLog.append("Reseting focus: " + System.lineSeparator());
  }

  /**
   * Mocks returning the x1.
   * 
   * @return the x1
   */
  @Override
  public int getX1() {
    this.viewLog.append("Getting x1" + System.lineSeparator());
    return 101;
  }

  /**
   * Mocks returning the y1.
   * 
   * @return the y1
   */
  @Override
  public int getY1() {
    this.viewLog.append("Getting y1" + System.lineSeparator());
    return 104;
  }

  /**
   * Mocks returning the x2.
   * 
   * @return the x2
   */
  @Override
  public int getX2() {
    this.viewLog.append("Getting x2" + System.lineSeparator());
    return 107;
  }

  /**
   * Mocks returning the y2.
   * 
   * @return the y2
   */
  @Override
  public int getY2() {
    this.viewLog.append("Getting y2" + System.lineSeparator());
    return 110;
  }

  /**
   * Mocks getting colors from the user.
   * 
   * @param title the title of the dialog
   * @return the chosen colors
   */
  @Override
  public Color getColorFromUser(String title) {
    this.viewLog.append("Getting color from the user and the title of the color chooser is " + title
        + System.lineSeparator());
    return Color.GREEN;
  }

  @Override
  public int showConfirmDialog(String message) {
    this.viewLog.append("Showing confimation dialog and the message shown is " + message);
    return 0;
  }
}
