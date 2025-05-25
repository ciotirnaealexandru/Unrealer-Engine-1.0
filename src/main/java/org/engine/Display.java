package org.engine;

import org.engine.graphics.Render;
import org.engine.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Display extends Canvas implements Runnable {

    // set a width
    public static final int WIDTH = 1280;
    // set a height
    public static final int HEIGHT = 720;
    // set a title for the game
    public static final String TITLE = "Unrealer Engine 1.0";

    // declare a thread
    private Thread thread;
    // initialize the game as not running
    private boolean running = false;
    // declare a screen
    private Screen screen;
    // declare an image
    private BufferedImage img;
    // declare a pixel array
    private int[] pixels;

    // class constructor
    public Display() {
        screen = new Screen(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        // only start the game if it's not already running
        if (running) return;

        // if not, make it run
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        // only stop the game if it's running
        if (!running) return;

        // if running, make it stop
        running = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void run() {
        while (running) {
            tick();
            render();
        }
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            // 3-dimensional game
            createBufferStrategy(3);
            return;
        }

        screen.render();

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Display game = new Display(); // game is the canvas
        JFrame frame = new JFrame(); // frame is the window in which i have a canvas

        // add the game to the frame
        frame.add(game);

        // frame settings
        // make sure to terminate the project if i hit X in the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set the size of the frame
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // center the frame
        frame.setLocationRelativeTo(null);
        // make it so it cannot be resized
        frame.setResizable(false);
        // make it visible
        frame.setVisible(true);
        // add a title to the frame
        frame.setTitle(TITLE);

        frame.pack();

        System.out.println("Running...");

        game.start();
    }
}
