package com.peterpl.labyrinth.item;

import com.peterpl.labyrinth.*;
import com.peterpl.labyrinth.graphics.*;

public abstract class AnimatedItem extends Item {
    private final int frameTime, frames, totalAnimTime;
    private int time = 0;
    private int frameIndex = 0;

    public AnimatedItem(String name, int frameTime) {
        super(name);
        this.frameTime = frameTime;
        frames = texture.getWidth() / LabyrinthAdventure.SIZE;
        totalAnimTime = frameTime * frames;
    }

    @Override
    public void update() {
        time++;
        int mod = time % totalAnimTime;
        for(int i = 0; i < frames; i++) {
            if(mod <= (i + 1) * frameTime) {
                frameIndex = i;
                break;
            }
        }
    }

    public int getFrameIndex() {
        return frameIndex;
    }
}
