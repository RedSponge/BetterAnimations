package com.redsponge.betteranimations;


public class Tween {

    private RAnimation out;
    private RAnimation in;
    private boolean done;

    public Tween(RAnimation out, RAnimation in) {
        this.in = in;
        this.out = out;

        if(out != null) {
            out.setCurrentFrame(0);
        }
        if(in != null) {
            in.setCurrentFrame(0);
        }
    }

    public void update(float delta) {
        if(out != null && !out.isDone()) {
            out.update(delta);
        }
        else if(in != null && !in.isDone()) {
            in.update(delta);
        }
        boolean outDone = out == null || out.isDone();
        boolean inDone = in == null || in.isDone();
        done = outDone && inDone;
    }

    public Frame getCurrentFrame() {
        if(out != null && !out.isDone()) {
            return out.getCurrentFrame();
        }
        else if(in != null && !in.isDone()) {
            return in.getCurrentFrame();
        }
        return null;
    }

    public boolean isDone() {
        return done;
    }


}
