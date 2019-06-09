package com.redsponge.betteranimations;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Represents a single frame in a {@link RAnimation}
 */
public class Frame {

    private TextureRegion textureRegion;
    private float duration;

    public Frame(TextureRegion textureRegion, float duration) {
        this.textureRegion = textureRegion;
        this.duration = duration;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return String.format("%s - %f", textureRegion, duration);
    }
}
