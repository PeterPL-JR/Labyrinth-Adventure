package com.peterpl.labyrinth;

import com.peterpl.labyrinth.maze.MazeGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LabyrinthAdventure {
    private final int width, height;
    private int playerX, playerY;
    private MazeGenerator maze;
    private JFrame frame;
    private boolean left, right, up, down;
    private JLabel[][] labels;

    private boolean[] keys = new boolean[65536];

    public LabyrinthAdventure(int width, int height) {
        maze = new MazeGenerator(width, height);
        this.width = width;
        this.height = height;
        playerX = 0;
        playerY = 1;

        initFrame();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keys[e.getKeyCode()] = true;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keys[e.getKeyCode()] = false;
            }
        });

        new Thread(() -> {
            try {
                while(true) {
                    Thread.sleep(100);

                    left = keys[KeyEvent.VK_A];
                    right = keys[KeyEvent.VK_D];
                    up = keys[KeyEvent.VK_W];
                    down = keys[KeyEvent.VK_S];

                    if(left) move(-1, 0);
                    if(right) move(1, 0);
                    if(up) move(0, -1);
                    if(down) move(0, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }).start();

        move(0, 0);
    }

    private void move(int xDir, int yDir) {
        int newX = playerX + xDir;
        int newY = playerY + yDir;
        if(newX < 0 || newY < 0 || newX >= maze.arrayWidth || newY >= maze.arrayHeight) {
            return;
        }
        int cell = maze.array[newX][newY];
        if(cell == MazeGenerator.FLOOR) {
            labels[playerX][playerY].setBackground(getCellColour(playerX, playerY));
            labels[newX][newY].setBackground(Color.RED);
            playerX = newX;
            playerY = newY;
        }
    }

    private Color getCellColour(int x, int y) {
        return maze.array[x][y] == MazeGenerator.FLOOR ? Color.white : Color.black;
    }

    private void initFrame() {
        final int CELL_SIZE = 10;

        frame = new JFrame();
        frame.setTitle("Labyrinth Adventure");
        frame.setSize(maze.arrayWidth * CELL_SIZE, maze.arrayHeight * CELL_SIZE + 32);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        labels = new JLabel[maze.arrayWidth][maze.arrayHeight];
        for(int x = 0; x < maze.arrayWidth; x++) {
            labels[x] = new JLabel[maze.arrayHeight];
            for(int y = 0; y < maze.arrayHeight; y++) {
                JLabel label = new JLabel();
                label.setSize(CELL_SIZE, CELL_SIZE);
                label.setLocation(CELL_SIZE * x, CELL_SIZE * y);
                label.setOpaque(true);
                label.setBackground(getCellColour(x, y));
                frame.getContentPane().add(label);
                labels[x][y] = label;
            }
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LabyrinthAdventure(41, 31);
    }
}