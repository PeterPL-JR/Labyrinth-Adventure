package com.peterpl.labyrinth.maze;

import com.peterpl.labyrinth.*;

import java.util.Stack;

public class MazeGenerator {
    public final int[][] array;

    public final int width, height;
    public final int arrayWidth, arrayHeight;
    private final boolean[][] completed;

    public static final int FLOOR = 0;
    public static final int WALL = 1;

    private static final Point[] DIRS = {
            new Point(1, 0),
            new Point(-1, 0),
            new Point(0, 1),
            new Point(0, -1)
    };

    public MazeGenerator(int width, int height) {
        if(width % 2 != 1) {
            width += 1;
        }
        if(height % 2 != 1) {
            height += 1;
        }

        this.width = width;
        this.height = height;

        int arrayWidth = width * 2 + 1;
        int arrayHeight = height * 2 + 1;

        this.arrayWidth = arrayWidth;
        this.arrayHeight = arrayHeight;

        array = new int[arrayWidth][arrayHeight];
        for(int x = 0; x < arrayWidth; x++) {
            array[x] = new int[arrayHeight];
            for(int y = 0; y < arrayHeight; y++) {
                array[x][y] = x % 2 == 1 && y % 2 == 1 ? FLOOR : WALL;
            }
        }

        completed = new boolean[width][height];
        for(int x = 0; x < width; x++) {
            completed[x] = new boolean[height];
        }

        generate();

        array[0][1] = FLOOR;
        array[arrayWidth - 1][arrayHeight - 2] = FLOOR;
    }

    private void generate() {
        generate(createStart());
    }

    private void generate(Point start) {
        Stack<Point> pointsStack = new Stack<>();
        Point point = start;
        while((point = randomCorridor(point)) != null) {
            pointsStack.push(point);
        }
        for(Point p : pointsStack) {
            generate(p);
        }
    }

    private Point createStart() {
        Point start, corridor;

        start = new Point(0, 0);
        corridor = Rand.bool() ? new Point(1, 0) : new Point(0, 1);

        int x = start.x();
        int y = start.y();

        completed[x][y] = true;
        completed[corridor.x()][corridor.y()] = true;

        createConnection(start, corridor);

        return corridor;
    }

    private Point randomCorridor(Point start) {
        int randomIndex = Rand.number(0, DIRS.length - 1);
        for(int i = 0; i < DIRS.length; i++) {
            Point dir = DIRS[(randomIndex + i) % DIRS.length];
            Point newPoint = new Point(start.x() + dir.x(), start.y() + dir.y());

            int x = newPoint.x();;
            int y = newPoint.y();
            if(x < 0 || y < 0 || x >= width || y >= height) {
                continue;
            }

            if(!completed[x][y]) {
                createConnection(start, newPoint);
                completed[x][y] = true;
                return newPoint;
            }
        }
        return null;
    }

    private int fromFloor(int n) {
        return n * 2 + 1;
    }

    private int toFloor(int n) {
        return (n - 1) / 2;
    }

    private void createConnection(Point p1, Point p2) {
        int x1 = p1.x();
        int y1 = p1.y();

        int x2 = p2.x();
        int y2 = p2.y();

        if(x1 == x2 && Math.abs(y1 - y2) == 1) {
            int minY = Math.min(y1, y2);
            array[fromFloor(x1)][fromFloor(minY) + 1] = FLOOR;
        } else if(y1 == y2 && Math.abs(x1 - x2) == 1) {
            int minX = Math.min(x1, x2);
            array[fromFloor(minX) + 1][fromFloor(y1)] = FLOOR;
        }
    }
}
