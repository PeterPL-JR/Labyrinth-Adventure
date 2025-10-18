package com.peterpl.labyrinth;

import com.peterpl.labyrinth.graphics.*;
import com.peterpl.labyrinth.input.*;
import com.peterpl.labyrinth.item.*;
import com.peterpl.labyrinth.level.*;
import com.peterpl.labyrinth.maze.*;
import com.peterpl.labyrinth.player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class LabyrinthAdventure extends Canvas implements Runnable {
    private static final int WIDTH_BASE = 800;
    public static final int SCALE = 3;
    public static final int WIDTH = WIDTH_BASE / SCALE / LabyrinthAdventure.SIZE * LabyrinthAdventure.SIZE;
    public static final int HEIGHT = WIDTH * 3 / 4;
    public static final String TITLE = "Labyrinth Adventure";

    public static final int SIZE = 16;

    private JFrame frame;
    private Thread thread;
    private boolean running = false;

    private Render render;
    private Keyboard keyboard;
    private Level level;
    private Player player;

    private static final int UPS_LIMIT = 60;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public LabyrinthAdventure(int width, int height) {
        level = new Level(width, height, Tile.WALL, Tile.FLOOR);
        player = new Player(0, SIZE, level);

        render = new Render(WIDTH, HEIGHT, level, player);

        keyboard = new Keyboard();
        addKeyListener(keyboard);

        initFrame();
        start();
    }

    private void initFrame() {
        setSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame();
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long prevTime = System.nanoTime();
        final double nanoSeconds = 1_000_000_000.0 / UPS_LIMIT;

        requestFocus();

        try {
            while(running) {
                long time = System.nanoTime();
                long delta = time - prevTime;

                if(delta < nanoSeconds) {
                    long sleepTime = (long) ((nanoSeconds - delta) / 1_000_000);
                    Thread.sleep(sleepTime);
                }

                update();
                render();

                prevTime = time;
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        stop();
    }

    boolean moving = false;
    int moveDirX = 0, moveDirY = 0;

    public void update() {
        keyboard.update();
        level.update();
        player.update();

        if(moving) {
            player.move(moveDirX, moveDirY);
            if(player.x % SIZE == 0 && player.y % LabyrinthAdventure.SIZE == 0) {
                moving = false;
                moveDirX = 0;
                moveDirY = 0;
            }
        } else {
            boolean left = keyboard.left;
            boolean right = keyboard.right;
            boolean up = keyboard.up;
            boolean down = keyboard.down;

            if(left) {
                moveDirX = -1;
            }
            else if(right) {
                moveDirX = 1;
            }
            else if(up) {
                moveDirY = -1;
            }
            else if(down) {
                moveDirY = 1;
            }

            player.moving = left || right || up || down;

            if(moveDirX != 0 || moveDirY != 0) {
                moving = true;
            }
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(2);
            return;
        }

        render.clear();
        render.render();

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = render.getPixels()[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        new LabyrinthAdventure(41, 31);
    }
}