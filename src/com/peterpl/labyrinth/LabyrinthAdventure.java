package com.peterpl.labyrinth;

import com.peterpl.labyrinth.maze.*;

import javax.swing.*;
import java.awt.*;

public class LabyrinthAdventure {
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(11, 7);

        final int CELL_SIZE = 20;

        JFrame frame = new JFrame();
        frame.setTitle("Labyrinth Adventure");
        frame.setSize(maze.arrayWidth * CELL_SIZE, maze.arrayHeight * CELL_SIZE + 32);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        for(int x = 0; x < maze.arrayWidth; x++) {
            for(int y = 0; y < maze.arrayHeight; y++) {
                JLabel label = new JLabel();
                label.setSize(CELL_SIZE, CELL_SIZE);
                label.setLocation(CELL_SIZE * x, CELL_SIZE * y);
                label.setOpaque(true);
                label.setBackground(maze.array[x][y] == MazeGenerator.FLOOR ? Color.white : Color.black);
                frame.getContentPane().add(label);
            }
        }

        frame.setVisible(true);
    }
}