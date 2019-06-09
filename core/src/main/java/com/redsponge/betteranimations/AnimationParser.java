package com.redsponge.betteranimations;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Arrays;

public class AnimationParser {

    public static AnimationManager parse(String animationDescriptor, TextureAtlas animationAtlas) {
        AnimationManager out = new AnimationManager();
        String[] animations = animationDescriptor.split("!");

        if(animations.length > 1) {
            for (int i = 1; i < animations.length; i++) {
                String animationData = animations[i];
                parseAnimation(animationData, animationAtlas, out);
            }
        }
        return out;
    }

    private static void parseAnimation(String animationData, TextureAtlas animationAtlas, AnimationManager manager) {
        String[] lines = animationData.split("\\n");
        String[] descriptorData = lines[0].split(":");
        String name = descriptorData[0];
        int from = Integer.parseInt(descriptorData[1]);
        int to = Integer.parseInt(descriptorData[2]);
        String inAnim = descriptorData[3];
        String outAnim = descriptorData[4];
        String playMode = descriptorData[5];

        System.out.println("Parsing Animation:" + name);
        System.out.println("From:" + from);
        System.out.println("To:" + to);

        Frame[] frames = new Frame[to - from + 1];

        for (int i = 0; i < frames.length; i++) {
            float dur = Float.parseFloat(lines[i + 1]);
            TextureRegion region = animationAtlas.findRegion(name, i + 1);
            frames[i] = new Frame(region, dur);
        }

        RAnimation animation = new RAnimation(frames);
//        animation.setPlayMode(RPlayMode.valueOf(playMode.toUpperCase()));

        manager.add(name, animation);
        if(!inAnim.trim().isEmpty()) {
            manager.addTweenIn(name, inAnim);
            System.out.println("Found Tween In: " + inAnim);
        }
        if(!outAnim.trim().isEmpty()) {
            manager.addTweenOut(name, outAnim);
            System.out.println("Found Tween Out: " + outAnim);
        }

        animation.setPlayMode(RPlayMode.valueOf(playMode.toUpperCase().trim()));
    }

    public static AnimationManager parse(FileHandle handle, TextureAtlas animationAtlas) {
        return parse(handle.readString(), animationAtlas);
    }

}
