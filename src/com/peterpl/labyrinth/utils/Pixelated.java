package com.peterpl.labyrinth.utils;

public abstract class Pixelated {
    protected int width, height;
    protected int[] pixels;

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final int[] getPixels() {
        return pixels;
    }

    public final void setPixel(int x, int y, int value) {
        pixels[x + y * width] = value;
    }

    public final int getPixel(int x, int y) {
        return pixels[x + y * width];
    }

    public final boolean isCorrectX(int x) {
        return x >= 0 && x < width;
    }

    public final boolean isCorrectY(int y) {
        return y >= 0 && y < height;
    }

    public final boolean isCorrect(int x, int y) {
        return isCorrectX(x) && isCorrectY(y);
    }
}
