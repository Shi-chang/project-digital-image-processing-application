package imageview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import script.ScriptFeatures;

/**
 * An implementation of the ImageView interface.
 */
public class ConcreteImageView extends JFrame implements ImageView {
  /**
   * A serial version UID.
   */
  private static final long serialVersionUID = -7400956026178549875L;
  // Menu items shown under the "File" menu
  private JMenuItem loadImage;
  private JMenuItem saveImage;

  // Menu items shown under the "Edit" menu
  private JMenuItem blurMenuItem;
  private JMenuItem sharpenMenuItem;
  private JMenuItem greyscaleMenuItem;
  private JMenuItem sepiaMenuItem;
  private JMenuItem ditherMenuItem;
  private JMenuItem mosaicMenuItem;
  private JMenuItem edgeDetectionMenuItem;
  private JMenuItem greyscaleEnhancementMenuItem;
  private JMenuItem imageCroppingMenuItem;
  private JMenuItem strippedRainbow;
  private JMenuItem horizontalStrippedRainbow;
  private JMenuItem verticalStrippedRainbow;
  private JMenuItem checkBoard;
  private JMenuItem nationalFlags;
  private JMenuItem norway;
  private JMenuItem greece;
  private JMenuItem swizerland;

  // Buttons shown under the image
  private JButton blurButton;
  private JButton sharpenButton;
  private JButton greyscaleButton;
  private JButton sepiaButton;
  private JButton ditherButton;
  private JButton mosaicButton;
  private JButton edgeDetectionButton;
  private JButton greyscaleEnhancementButton;
  private JButton imageCroppingButton;
  private JButton exitButton;

  // Script text area and buttons shown under the script
  private JScrollPane imageScrollPane;
  private JLabel imageLabel;
  private JTextArea textArea;
  private JButton applyScriptButton;
  // Keep track of the status of mouse
  private boolean mouseDragged = false;
  private boolean croppingDone = false;
  private int x1;
  private int y1;
  private int x2;
  private int y2;

  /**
   * Constructor for this class.
   * 
   * @param title the title of the frame
   */
  public ConcreteImageView(String title) {
    super(title);
    setSize(800, 700);
    setLocation(50, 50);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Create a menu bar that contains the menus
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    JMenu fileMenu = new JMenu("File");
    menuBar.add(fileMenu);

    loadImage = new JMenuItem("Open File        Ctrl+O");
    fileMenu.add(loadImage);

    saveImage = new JMenuItem("Save                Ctrl+S");
    fileMenu.add(saveImage);

    JMenu editMenu = new JMenu("Edit");
    menuBar.add(editMenu);

    blurMenuItem =
        new JMenuItem("Blur                                                      Ctrl+Shift+B");
    blurMenuItem.setHorizontalTextPosition(SwingConstants.RIGHT);
    editMenu.add(blurMenuItem);

    sharpenMenuItem =
        new JMenuItem("Sharpen                                               Ctrl+Shift+N");
    editMenu.add(sharpenMenuItem);

    greyscaleMenuItem =
        new JMenuItem("Greyscale                                            Ctrl+Shift+G");
    editMenu.add(greyscaleMenuItem);

    sepiaMenuItem =
        new JMenuItem("Sepia                                                    Ctrl+Shift+A");
    editMenu.add(sepiaMenuItem);

    ditherMenuItem =
        new JMenuItem("Dither                                                   Ctrl+Shift+D");
    editMenu.add(ditherMenuItem);

    mosaicMenuItem =
        new JMenuItem("Mosaic                                                 Ctrl+Shift+M");
    editMenu.add(mosaicMenuItem);

    edgeDetectionMenuItem =
        new JMenuItem("Edge Detection                                  Ctrl+Shift+E");
    editMenu.add(edgeDetectionMenuItem);

    greyscaleEnhancementMenuItem =
        new JMenuItem("Greyscale Enhancement                   Ctrl+Shift+T");
    editMenu.add(greyscaleEnhancementMenuItem);

    imageCroppingMenuItem =
        new JMenuItem("Image Cropping                                  Ctrl+Shift+P");
    editMenu.add(imageCroppingMenuItem);

    JMenu generateMenu = new JMenu("Generate");
    menuBar.add(generateMenu);

    strippedRainbow = new JMenu("Stripped Rainbow    ");
    generateMenu.add(strippedRainbow);

    horizontalStrippedRainbow = new JMenuItem("   Horizontal Stripped Rainbow     Ctrl+Shift+H");
    strippedRainbow.add(horizontalStrippedRainbow);

    verticalStrippedRainbow = new JMenuItem("   Vertical Stripped Rainbow          Ctrl+Shift+V");
    strippedRainbow.add(verticalStrippedRainbow);

    checkBoard = new JMenuItem("Check Board   Ctrl+Shift+K");
    generateMenu.add(checkBoard);

    nationalFlags = new JMenu("National Flags");
    generateMenu.add(nationalFlags);

    norway = new JMenuItem("   Norway          Ctrl+Shift+Y");
    nationalFlags.add(norway);

    greece = new JMenuItem("   Greece          Ctrl+Shift+R");
    nationalFlags.add(greece);

    swizerland = new JMenuItem("   Swizerland    Ctrl+Shift+Z");
    nationalFlags.add(swizerland);

    // Create an overall panel that holds all other components
    JPanel overallPanel = new JPanel();
    overallPanel.setLayout(new BoxLayout(overallPanel, BoxLayout.Y_AXIS));
    add(overallPanel);

    // Create a panel that shows the image
    imageLabel = new JLabel();
    imageScrollPane = new JScrollPane(imageLabel);
    imageScrollPane.setPreferredSize(new Dimension(600, 4000));
    overallPanel.add(imageScrollPane);

    // Create a panel that shows the operation buttons
    JPanel buttonPanel1 = new JPanel();
    buttonPanel1.setLayout(new GridLayout(2, 5));
    overallPanel.add(buttonPanel1);

    blurButton = new JButton("Blur");
    buttonPanel1.add(blurButton);

    sharpenButton = new JButton("Sharpen");
    buttonPanel1.add(sharpenButton);

    greyscaleButton = new JButton("Greyscale");
    buttonPanel1.add(greyscaleButton);

    sepiaButton = new JButton("Sepia");
    buttonPanel1.add(sepiaButton);

    ditherButton = new JButton("Dither");
    buttonPanel1.add(ditherButton);

    mosaicButton = new JButton("Mosaic");
    buttonPanel1.add(mosaicButton);

    edgeDetectionButton = new JButton("Edge Detection");
    buttonPanel1.add(edgeDetectionButton);

    greyscaleEnhancementButton = new JButton("Greyscale Enhancement");
    buttonPanel1.add(greyscaleEnhancementButton);

    imageCroppingButton = new JButton("Cropping");
    buttonPanel1.add(imageCroppingButton);

    exitButton = new JButton("Exit");
    buttonPanel1.add(exitButton);

    // Create a panel that includes a hint label and a text area that allow the user to type in a
    // batch of commands for image operation
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
    overallPanel.add(textPanel);

    JLabel hintLabel =
        new JLabel("Type in commands here to apply (refer to README for instructions)");
    hintLabel.setPreferredSize(new Dimension(300, 200));
    hintLabel.setHorizontalAlignment(SwingConstants.LEFT);
    textPanel.add(hintLabel);

    textArea = new JTextArea(50, 100);
    JScrollPane textScrollPane = new JScrollPane(textArea);
    textScrollPane.setPreferredSize(new Dimension(600, 1200));
    textPanel.add(textScrollPane);

    // Create a panel that shows the "Apply Script" button
    JPanel buttonPanel2 = new JPanel();
    buttonPanel2.setLayout(new FlowLayout());
    overallPanel.add(buttonPanel2);

    applyScriptButton = new JButton("Apply script");
    buttonPanel2.add(applyScriptButton);

    setVisible(true);
  }

  /**
   * Return the x1.
   * 
   * @return the x1
   */
  @Override
  public int getX1() {
    return this.x1;
  }

  /**
   * Return the y1.
   * 
   * @return the y1
   */
  @Override
  public int getY1() {
    return this.y1;
  }

  /**
   * Return the x2.
   * 
   * @return the x2
   */
  @Override
  public int getX2() {
    return this.x2;
  }

  /**
   * Return the y2.
   * 
   * @return the y2
   */
  @Override
  public int getY2() {
    return this.y2;
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    // Ensure that the rectangle will not surpass the borders of the image
    int offsetX = (this.getWidth() - this.getContentPane().getWidth()) / 2;
    int offsetY = (this.getHeight() - this.getContentPane().getHeight())
        - (this.getWidth() - this.getContentPane().getWidth()) / 2;
    if (imageLabel.getIcon() != null) {
      int xa = Math.max(0, Math.min(x1, imageLabel.getIcon().getIconWidth()));
      int ya = Math.max(0, Math.min(y1, imageLabel.getIcon().getIconHeight()));
      int xb = Math.max(0, Math.min(x2, imageLabel.getIcon().getIconWidth()));
      int yb = Math.max(0, Math.min(y2, imageLabel.getIcon().getIconHeight()));
      int x = Math.min(xa, xb);
      int y = Math.min(ya, yb);
      int width = Math.abs(xb - xa);
      int height = Math.abs(yb - ya);

      if (!croppingDone) {
        g.drawRect(x + offsetX, y + offsetY, width, height);
      }
    }
  }

  ;

  /**
   * Add action listeners and key listeners for the buttons, menu items and shortcuts.
   * 
   * @param f an implementation of the ButtonAndMenuFeatures interface
   */
  @Override
  public void setButtonAndMenuFeatures(ButtonAndMenuFeatures f) {
    loadImage.addActionListener(l -> {
      try {
        f.loadImage();
      } catch (IllegalArgumentException e) {
        System.out.println("Error occurred when trying to load image.");
      }
    });

    saveImage.addActionListener(l -> {
      try {
        f.saveImage();
      } catch (IOException e) {
        System.out.println("Error occurred when trying to save image.");
      }
    });

    blurMenuItem.addActionListener(l -> f.applyOperations("blur"));
    sharpenMenuItem.addActionListener(l -> f.applyOperations("sharpen"));
    greyscaleMenuItem.addActionListener(l -> f.applyOperations("greyscale"));
    sepiaMenuItem.addActionListener(l -> f.applyOperations("sepia"));
    ditherMenuItem.addActionListener(l -> f.applyOperations("dither"));
    mosaicMenuItem.addActionListener(l -> f.applyOperations("mosaic"));
    edgeDetectionMenuItem.addActionListener(l -> f.applyOperations("edgeDetection"));
    greyscaleEnhancementMenuItem.addActionListener(l -> f.applyOperations("greyscaleEnhancement"));

    imageCroppingMenuItem.addActionListener(l -> {
      mouseDragged = false;
      croppingDone = false;
      // Add mouse listener to the image label
      imageLabel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // do nothing here
        }

        @Override
        public void mousePressed(MouseEvent e) {
          x1 = e.getX();
          y1 = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          if (mouseDragged == true) {
            x2 = e.getX();
            y2 = e.getY();
            repaint();
          }
          f.applyImageCropping();
          croppingDone = true;
          imageLabel.removeMouseListener(this);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          // do nothing here
        }

        @Override
        public void mouseExited(MouseEvent e) {
          // do nothing here
        }
      });
      // Add mouse motion listener to the image label
      imageLabel.addMouseMotionListener(new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
          mouseDragged = true;
          if (croppingDone) {
            imageLabel.removeMouseMotionListener(this);
          }
          x2 = e.getX();
          y2 = e.getY();
          repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
          // do nothing here
        }
      });
    });
    // Add action listener to buttons and menu items
    horizontalStrippedRainbow.addActionListener(l -> f.applyOperations("rainbowH"));
    verticalStrippedRainbow.addActionListener(l -> f.applyOperations("rainbowV"));
    checkBoard.addActionListener(l -> f.applyOperations("checkboard"));
    norway.addActionListener(l -> f.applyOperations("norway"));
    greece.addActionListener(l -> f.applyOperations("greece"));
    swizerland.addActionListener(l -> f.applyOperations("swizerland"));

    blurButton.addActionListener(l -> f.applyOperations("blur"));
    sharpenButton.addActionListener(l -> f.applyOperations("sharpen"));
    greyscaleButton.addActionListener(l -> f.applyOperations("greyscale"));
    sepiaButton.addActionListener(l -> f.applyOperations("sepia"));
    ditherButton.addActionListener(l -> f.applyOperations("dither"));
    mosaicButton.addActionListener(l -> f.applyOperations("mosaic"));
    edgeDetectionButton.addActionListener(l -> f.applyOperations("edgeDetection"));
    greyscaleEnhancementButton.addActionListener(l -> f.applyOperations("greyscaleEnhancement"));
    exitButton.addActionListener(l -> f.exit());

    imageCroppingButton.addActionListener(l -> {
      mouseDragged = false;
      croppingDone = false;
      // Add mouse listener to the image label
      imageLabel.addMouseListener(new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
          // do nothing here
        }

        @Override
        public void mousePressed(MouseEvent e) {
          x1 = e.getX();
          y1 = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          if (mouseDragged == true) {
            x2 = e.getX();
            y2 = e.getY();
            repaint();
          }
          f.applyImageCropping();
          croppingDone = true;
          imageLabel.removeMouseListener(this);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          // do nothing here
        }

        @Override
        public void mouseExited(MouseEvent e) {
          // do nothing here
        }
      });
      // Add mouse motion listener to the image label
      imageLabel.addMouseMotionListener(new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
          mouseDragged = true;
          if (croppingDone) {
            imageLabel.removeMouseMotionListener(this);
          }
          x2 = e.getX();
          y2 = e.getY();
          repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
          // do nothing here
        }
      });
    });

    // Add shortcuts to this Frame
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // do nothing here
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_S) && (e.isControlDown() == true)) {
          try {
            f.saveImage();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }

        if ((e.getKeyCode() == KeyEvent.VK_O) && (e.isControlDown() == true)) {
          try {
            f.loadImage();
          } catch (IllegalArgumentException e1) {
            e1.printStackTrace();
          }
        }

        if ((e.getKeyCode() == KeyEvent.VK_B) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("blur");
        }

        if ((e.getKeyCode() == KeyEvent.VK_N) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("sharpen");
        }

        if ((e.getKeyCode() == KeyEvent.VK_G) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("greyscale");
        }

        if ((e.getKeyCode() == KeyEvent.VK_A) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("sepia");
        }

        if ((e.getKeyCode() == KeyEvent.VK_D) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("dither");
        }

        if ((e.getKeyCode() == KeyEvent.VK_M) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("mosaic");
        }

        if ((e.getKeyCode() == KeyEvent.VK_E) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("edgeDetection");
        }

        if ((e.getKeyCode() == KeyEvent.VK_T) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          f.applyOperations("greyscaleEnhancement");
        }

        if ((e.getKeyCode() == KeyEvent.VK_P) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          imageCroppingButton.doClick();
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_H) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          horizontalStrippedRainbow.doClick();
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_V) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          verticalStrippedRainbow.doClick();
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_K) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          checkBoard.doClick();
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          norway.doClick();
        }
        
        if ((e.getKeyCode() == KeyEvent.VK_R) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          greece.doClick();
        }

        if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown() == true)
            && (e.isShiftDown() == true)) {
          swizerland.doClick();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // do nothing here
      }
    });
  }

  /**
   * Add action listener for the applyScript button.
   * 
   * @param f an implementation of the ScriptFeatures interface
   */
  @Override
  public void setScriptFeatures(ScriptFeatures f) {
    applyScriptButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        // After clicking this apply script button, the value of the input script will be assigned
        // to a field called "input" in the script controller. The filed will be used in the
        // applyScript method in the next step.
        f.getScriptAsInput();
        f.applyScript();
      }
    });
  }

  /**
   * Make the frame focusable and make request that this frame gets the focus.
   */
  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.toFront();
    this.requestFocus();
  }

  /**
   * Return the script typed in by the user.
   * 
   * @return the script typed in by the user
   */
  @Override
  public String getScript() {
    return textArea.getText();
  }

  /**
   * Provide a dialogue box with a text field that allows the user to input data.
   * 
   * @param message the message shown on the dialogue box
   */
  @Override
  public String getUserInput(String message, String title) {
    return JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Provide a color chooser that allows the user to choose colors.
   * 
   * @param title the title of the dialog
   * @return the chosen colors
   */
  @Override
  public Color getColorFromUser(String title) {
    return JColorChooser.showDialog(this, title, Color.WHITE);
  }

  /**
   * Show the image on the view.
   * 
   * @param image the image to be shown
   */
  @Override
  public void showImage(Image image) {
    imageLabel.setIcon(new ImageIcon(image));
    imageLabel.setHorizontalAlignment(SwingConstants.LEFT);
    imageLabel.setVerticalAlignment(SwingConstants.TOP);
  }

  /**
   * Get the file name and path to be opened by the user.
   */
  @Override
  public String getOpenImageFilePath() {
    JFileChooser imageFilePath = new JFileChooser();
    FileNameExtensionFilter filter =
        new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp");
    imageFilePath.setFileFilter(filter);
    int returnState = imageFilePath.showOpenDialog(this);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      return imageFilePath.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * Get the file name and path to be saved by the user.
   */
  @Override
  public String getSaveImageFilePath() {
    JFileChooser imageFilePath = new JFileChooser();
    int returnState = imageFilePath.showSaveDialog(this);
    if (returnState == JFileChooser.APPROVE_OPTION) {
      return imageFilePath.getSelectedFile().getAbsolutePath();
    }
    return "";
  }

  /**
   * Show messages through a JOptionPane.
   * 
   * @param message the message to be shown
   */
  @Override
  public void showMessage(String title, String message) {
    JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Show confirm dialog through a JOptionPane.
   * 
   * @param message the message to be shown
   */
  @Override
  public int showConfirmDialog(String message) {
    return JOptionPane.showConfirmDialog(this, message);
  }
}
