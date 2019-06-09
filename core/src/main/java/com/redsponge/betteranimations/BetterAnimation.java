package com.redsponge.betteranimations;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BetterAnimation extends ApplicationAdapter {


    private TextureAtlas atlas;
    private SpriteBatch batch;
    private AnimationManager animation;

    private int x;
    private boolean flipped;
    private String state;

    @Override
    public void create() {
        batch = new SpriteBatch();
        atlas = new TextureAtlas(Gdx.files.internal("unpowered.atlas"));
        animation = AnimationParser.parse(Gdx.files.internal("player.ranim"), atlas);
        animation.setCurrentAnimation("idle");
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();

        /**
         * If-s for jumping etc...
         */
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            x+=2;
            state = "run";
            flipped = false;
        } else if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            state = "run";
            x-=2;
            flipped = true;
        } else {
            state = "idle";
        }

        animation.setCurrentAnimation(state);
        animation.update(delta);

        batch.begin();
        TextureRegion frame = animation.getCurrentFrame().getTextureRegion();

        if(flipped) {
            frame.flip(true, false);
        }

        batch.draw(animation.getCurrentFrame().getTextureRegion(), x, 100);

        if(flipped) {
            frame.flip(true, false);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }
}