package com.redsponge.betteranimations;

import java.util.HashMap;

public class AnimationManager {

    private HashMap<String, RAnimation> animationMap;
    private HashMap<String, String> tweenOuts;  // <From, Animation>
    private HashMap<String, String> tweenIns;   // <To, Animation>

    private String currentName;
    private RAnimation currentAnimation;

    private Tween currentTween;
    private boolean isTweening;

    public AnimationManager() {
        animationMap = new HashMap<String, RAnimation>();
        tweenOuts = new HashMap<String, String>();
        tweenIns = new HashMap<String, String>();

        currentName = null;
        currentAnimation = null;
    }

    public void add(String name, RAnimation animation) {
        add(name, animation, false);
    }

    public void add(String name, RAnimation animation, boolean override) {
        if (!override && this.animationMap.containsKey(name)) {
            throw new RuntimeException("Already has animation with name " + name + "!");
        }

        this.animationMap.put(name, animation);
    }

    public void addTweenIn(String to, String animation) {
        tweenIns.put(to, animation);
    }

    public void addTweenOut(String from, String animation) {
        tweenOuts.put(from, animation);
    }

    public void setCurrentAnimation(String name) {
        if(name.equals(currentName)) {
            return;
        }

        String tweenFrom = tweenOuts.get(currentName);
        String tweenTo = tweenIns.get(name);

        this.currentName = name;
        this.currentAnimation = animationMap.get(name);
        this.currentAnimation.setCurrentFrame(0);

        if(tweenTo != null || tweenFrom != null) {
            RAnimation outAnimation = animationMap.get(tweenFrom);
            RAnimation inAnimation = animationMap.get(tweenTo);

            this.currentTween = new Tween(outAnimation, inAnimation);
            this.isTweening = true;
        }
    }

    public void update(float delta) {
        if(this.isTweening) {
            this.currentTween.update(delta);
            if(this.currentTween.isDone()) {
                this.isTweening = false;
                this.currentTween = null;
            }
        } else {
            this.currentAnimation.update(delta);
        }
    }

    public Frame getCurrentFrame() {
        if(this.isTweening) {
            return this.currentTween.getCurrentFrame();
        }

        return this.currentAnimation.getCurrentFrame();
    }

    public RAnimation getAnimation(String name) {
        return animationMap.get(name);
    }
}
