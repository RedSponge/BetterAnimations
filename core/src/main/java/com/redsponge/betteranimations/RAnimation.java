package com.redsponge.betteranimations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RAnimation {

    private Frame[] frames;
    private float timeUntilSwitch;
    private int currentFrame;
    private float totalDuration;

    private Holder<Boolean> done;
    private Holder<Integer> playModeExtra;

    private RPlayMode playMode;

    /**
     * Is all the duration the same (like in regular {@link com.badlogic.gdx.graphics.g2d.Animation}s
     */
    private boolean isUnifiedDuration;

    public RAnimation(Frame... frames) {
        this.frames = frames;

        for (Frame frame : frames) {
            totalDuration += frame.getDuration();
        }

        currentFrame = -1;
        playModeExtra = new Holder<Integer>(1);
        playMode = RPlayMode.LOOP;
        done = new Holder<Boolean>(false);
    }

    public RAnimation(float duration, TextureRegion... regions) {
        this.frames = new Frame[regions.length];

        for (int i = 0; i < regions.length; i++) {
            this.frames[i] = new Frame(regions[i], duration);
        }

        isUnifiedDuration = true;
        totalDuration = duration * regions.length;

        currentFrame = -1;
        playModeExtra = new Holder<Integer>(1);
        playMode = RPlayMode.LOOP;
        done = new Holder<Boolean>(false);
    }

    /**
     * Progresses the animation
     * @param delta The delta time
     */
    public void update(float delta) {
        timeUntilSwitch -= delta;
        if(timeUntilSwitch <= 0) {
            currentFrame = playMode.getNext(currentFrame, frames.length, playModeExtra, done);
            timeUntilSwitch = frames[currentFrame].getDuration();
        }
    }

    /**
     * Finds the index of the correct frame by time assuming the duration is the same for all
     * @param time The time of the frame
     * @return The index of the frame.
     */
    private int getFrameIndexForTimeUnified(float time) {
        int frame = (int) (totalDuration / time) - 1;

        if(frame >= frames.length) {
            frame = frames.length - 1;
        }

        return frame;
    }

    /**
     * Finds the index of the correct frame by time
     * Note: This method is of efficiency O(n) and shouldn't be used a lot!
     * You should prefer using {@link RAnimation#getCurrentFrame()}, {@link RAnimation#setCurrentFrame(int)} and {@link RAnimation#update(float)} instead
     *
     * @param time The time of the frame
     * @return The index of the frame, -1 if the time is negative.
     */
    private int getFrameIndexForTime(float time) {
        int frame = -1;

        while(time > 0) {
            frame++;
            time -= frames[frame].getDuration();
        }

        return frame;
    }

    /**
     * Finds the index of the correct frame by time
     * Note: if the time is not unified this method won't be very efficient!
     * You should prefer using {@link RAnimation#getCurrentFrame()}, {@link RAnimation#setCurrentFrame(int)} and {@link RAnimation#update(float)} instead
     *
     * @param time The time (in seconds)
     * @return The correct frame
     */
    public Frame getFrameForTime(float time) {
        int frame = isUnifiedDuration ? getFrameIndexForTimeUnified(time) : getFrameIndexForTime(time);
        try {
            return frames[getFrameIndexForTime(time)];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Invalid time! Couldn't get frame for time [" + time + "]!", e);
        }
    }

    public Frame getCurrentFrame() {
        return frames[currentFrame];
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
        this.timeUntilSwitch = frames[currentFrame].getDuration();
        this.done.value = false;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public Frame[] getFrames() {
        return frames;
    }

    public boolean isDone() {
        return done.value;
    }

    public void setPlayMode(RPlayMode playMode) {
        this.playMode = playMode;
    }

    public RPlayMode getPlayMode() {
        return playMode;
    }
}
