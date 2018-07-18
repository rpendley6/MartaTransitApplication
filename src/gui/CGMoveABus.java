package gui;

import information.*;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT's event classes and listener interface
import javax.swing.*;    // Using Swing's components and containers

/**
 * Custom Graphics Example: Using key/button to move a line left or right.
 */

/*
 CS 2340 M7 Graphics Example: This code was adapted from the following site:
 https://www.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
 Saturday, July 7, 2018
 */

@SuppressWarnings("serial")
public class CGMoveABus extends JFrame {
    // Define constants for the various dimensions
    public static final int CANVAS_WIDTH = 1000;
    public static final int CANVAS_HEIGHT = 500;
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color CANVAS_BACKGROUND = new Color(156, 197, 161);

    // The moving bus is initially positioned in the upper left corner
    private int x1 = 10;
    private int y1 = 10;

    private DrawCanvas canvas; // The custom drawing canvas (an innder class extends JPanel)

    // Constructor to set up the GUI components and event handlers

    /*
    In this example, the application requests for a repaint() in the KeyEvent and MouseEvent
    handlers, which triggers the paintComponent() with an appropriate Graphics object as the
    argument. To be precise, when you invoke the repaint() method to repaint a JComponent,
    the Windowing subsystem calls-back paint() method. The paint() method then calls-back
    three methods: paintComponent(), paintBorder() and paintChilden().
     */

    public CGMoveABus() {
        // Set up a panel for the buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnReset = new JButton("Reset <-");
        btnPanel.add(btnReset);
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                x1 = 10;
                y1 = 10;
                canvas.repaint();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });
        JButton btnNextStop = new JButton("Next Stop ->");
        btnPanel.add(btnNextStop);
        btnNextStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (y1 == 10) { x1 += 50; } else { x1 -= 50; }
                if (x1 >= 300) { y1 = 60; }
                if (x1 <= 50) { y1 = 10; }
                canvas.repaint();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });

        // Set up a custom drawing JPanel
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Add both panels to this JFrame's content-pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(btnPanel, BorderLayout.SOUTH);

        // "super" JFrame fires KeyEvent
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch(evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        x1 = 10;
                        y1 = 10;
                        repaint();
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (y1 == 10) { x1 += 50; } else { x1 -= 50; }
                        if (x1 >= 300) { y1 = 60; }
                        if (x1 <= 50) { y1 = 10; }
                        repaint();
                        break;
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE button
        setTitle("Move a Bus");
        pack();           // pack all the components in the JFrame
        setVisible(true); // show it
        requestFocus();   // set the focus to JFrame to receive KeyEvent
        mapInitialize();
    }

    public void mapInitialize() {

        // Prepare an ImageIcon
        ImageIcon icon = null;
        String imgFilename = "gui/bus.png";
        java.net.URL imgURL = getClass().getClassLoader().getResource(imgFilename);
        if (imgURL != null) {
            icon =  new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + imgFilename);
        }

        // Prepare an Image object to be used by drawImage()
        final Image img = icon.getImage();

        //draw route maps
        for (marta.Route r : MainList.sim.getData().routeList) {
            for (marta.Stop s : r.getPath()) {
                //Rectangle rect = new Rectangle((int) s.getLatitude(), (int) s.getLongitude(), 20, 20);
                canvas.paintComponent(canvas.getGraphics());
                //canvas.getGraphics().drawImage(img, (int) s.getLatitude(), (int) s.getLongitude(), null);

            }
        }
    }

    /**
     * Define inner class DrawCanvas, which is a JPanel used for custom drawing.
     */

    class DrawCanvas extends JPanel {
        /*
        paintComponent() is a so-called "call-back" method. The Windowing subsystem
        invokes this method and provides a pre-configured Graphics object to represent
        its state (e.g., current color, font, clip area and etc). There are two kinds
        of painting: system-triggered painting and application-triggered painting. In
        a system-trigger painting, the system request a component to render its content
        when the component is first made visible on the screen, or the component is
        resized, or the component is damaged that needs to be repaint. In an application
        -triggered painting, the application invokes a repaint() request. Under both
        cases, the Windowing subsystem will call-back the paintComponent() to render the
        contents of the component with a proper Graphics object as argument.
         */

        @Override
        public void paintComponent(Graphics g) {
            // Prepare an ImageIcon
            ImageIcon icon = null;
            String imgFilename = "gui/side_bus.png";
            java.net.URL imgURL = getClass().getClassLoader().getResource(imgFilename);
            if (imgURL != null) {
                icon =  new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + imgFilename);
            }

            // Prepare an Image object to be used by drawImage()
            final Image img = icon.getImage();

            Font myFont1 = new Font(Font.MONOSPACED, Font.PLAIN, 12);

            super.paintComponent(g);
            setBackground(CANVAS_BACKGROUND);
            g.setColor(LINE_COLOR);
            g.drawImage(img, x1, y1, 50, 50, null);
            g.drawString("bus: #0", x1 + 50, y1 + 25);     // in default font
        }
    }

    // The entry main() method
    public static void main(String[] args) {
        // Run GUI codes on the Event-Dispatcher Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CGMoveABus(); // Let the constructor do the job
            }
        });
    }
}

