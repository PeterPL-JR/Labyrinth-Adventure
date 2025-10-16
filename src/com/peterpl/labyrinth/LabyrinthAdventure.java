package com.peterpl.labyrinth;

import com.peterpl.labyrinth.graphics.*;
import com.peterpl.labyrinth.input.*;
import com.peterpl.labyrinth.level.*;
import com.peterpl.labyrinth.maze.*;
import com.peterpl.labyrinth.player.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class LabyrinthAdventure extends Canvas implements Runnable {
    private static final int WIDTH_BASE = 800;
    public static final int SCALE = 3;
    public static final int WIDTH = WIDTH_BASE / SCALE;
    public static final int HEIGHT = WIDTH * 3 / 4;
    public static final String TITLE = "Labyrinth Adventure";

    private MazeGenerator maze;

    private JFrame frame;
    private Thread thread;
    private boolean running = false;

    private Render render;
    private Keyboard keyboard;
    private Level level;
    private Player player;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public LabyrinthAdventure(int width, int height) {
        maze = new MazeGenerator(width, height);

        level = new Level(maze.arrayWidth, maze.arrayHeight);
        player = new Player(0, Tile.SIZE);

        render = new Render(WIDTH, HEIGHT, level, player);

        for(int x = 0; x < level.width; x++) {
            for(int y = 0; y < level.height; y++) {
                int tile = maze.array[x][y];
                level.tiles[x][y] = tile == MazeGenerator.WALL ? Tile.WALL : Tile.FLOOR;
            }
        }

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
        while(running) {
            update();
            render();
        }
        stop();
    }

    public void update() {
        keyboard.update();
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