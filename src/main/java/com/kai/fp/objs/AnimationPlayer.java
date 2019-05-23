package com.kai.fp.objs;

import java.awt.image.BufferedImage;
import java.util.*;

/**
 * An AnimationPlayer meant to be used in your base level "Entity" class.
 * In the update/render loop for the containing class, drawing nextFrame(), on
 * the condition that it is not null will allow freedom over animations.
 * Allows the user to set a repeating animation, useful for running or consistent animations.
 * Allows the user to queue animations in order to have animations play in a certain order.
 * Allows the user to force play animations that bypass the queue.
 *
 * @see AnimationPlayer#setRepeatingAnim(String)
 * @see AnimationPlayer#queueAnim(String)
 * @see AnimationPlayer#playAnim(String)
 *
 * @author Kai Tinkess on May 3rd, 2019
 */

public class AnimationPlayer {

    /**
     * Represents the number of frames displayed per second.
     * If playing an anim with 5 frames,
     * and while being called 10 times per second, each frame is returned twice.
     */
    private int framesPerSecond = 5;

    /**
     * A map of all anims currently in the animation player.
     * The key is the same title as its matching animation's title.
     */
    private HashMap<String, Animation> animations = new HashMap<>();

    /**
     * The last time the frame given by nextFrame() changed.
     * Always set to a value produced by System.currentTimeMillis();
     *
     * @see System#currentTimeMillis()
     */
    private long lastFrameChange = -1;

    /**
     * The current frame that is being returned inside of the current animation.
     */
    private int currentFrame = 0;

    /**
     * The animation that is currently being played.
     */
    private Animation currentAnim;

    /**
     * Usually, but not always, synonymous with whether or not currentAnim is null.
     * Represents whether or not an anim (besides repeatingAnim) is being played.
     */
    private boolean playing = false;

    /**
     * The current queue of animations to be played after currentAnim ends.
     * All animations in the queue are played before any repeatingAnim.
     */
    private Queue<Animation> animQueue = new LinkedList<>();

    /**
     * Null by default.
     * If set, plays infinitely whenever no other animations are queued or playing.
     */
    private Animation repeatingAnim;

    /**
     * Creates an AnimationPlayer from a pre-existing collection of animations.
     *
     * @param animations The collection of animations that is copied into this object.
     */
    public AnimationPlayer(Collection<Animation> animations) {
        animations.forEach((anim) -> addAnim(anim));
    }

    /**
     * Creates an empty AnimationPlayer.
     */
    public AnimationPlayer() {}

    /**
     * Returns the current frame of the current animation.
     * If it has been "long enough" (1000.0/framesPerSecond), it updates the current frame.
     * If there are no more frames, the next animation in the queue or the repeating animation are played.
     *
     * @return The current frame of the current animation.
     */
    public BufferedImage nextFrame() {

        long currentTime = System.currentTimeMillis();
        if ((1000.0 / getFramesPerSecond()) <= (currentTime - lastFrameChange) && playing) {
            currentFrame++;
            lastFrameChange = currentTime;
            //System.out.println("switching frames in the " + currentAnim.getTitle() + " anim");
        }
        //System.out.println("last frame change was " + (currentTime - lastFrameChange) + " ms ago");

        if (currentAnim == null || currentFrame >= (currentAnim.getFrameCount()) || !playing) {
            if (isAnimQueueEmpty()) {
                playing = false;
            } else {
                resetFieldsForNextAnim();
                currentAnim = animQueue.poll();
            }
        }
        if (!playing) {
            if (!(repeatingAnim == null)) {
                queueAnim(repeatingAnim);
                currentAnim = animQueue.poll();
                resetFieldsForNextAnim();
            } else {
                return null;
            }
        }

        return currentAnim.getFrame(currentFrame);
    }

    /**
     * "Force" plays an animation, playing immediately and before anything in the queue.
     * Optionally clears the current queue.
     *
     * @param anim The animation to play.
     * @param clear A flag signalling whether or not to clear the current queue.
     */
    public void playAnim(Animation anim, boolean clear) {
        if (clear) clearAnimQueue();
        resetFieldsForNextAnim();

        currentAnim = new Animation(anim);
    }

    /**
     * Clears the queue and plays an animation from the HashMap animations.
     *
     * @see AnimationPlayer#playAnim(Animation, boolean)
     */
    public void playAnim(String title, boolean clear) {
        playAnim(animations.get(title), clear);
    }

    /**
     * @see AnimationPlayer#playAnim(Animation, boolean)
     */
    public void playAnim(String title) {
        playAnim(animations.get(title), true);
    }

    /**
     * @see AnimationPlayer#playAnim(Animation, boolean)
     */
    public void playAnim(Animation anim) {
        playAnim(anim, true);
    }

    /**
     * Adds an animation to the queue, which is played after all existing queued animations are played.
     *
     * @param anim The animation to add to the queue.
     */
    public void queueAnim(Animation anim) {
        animQueue.add(anim);
    }

    /**
     * Adds an animation to the queue from the animations HashMap.
     *
     * @see AnimationPlayer#queueAnim(Animation)
     */
    public void queueAnim(String title) {
        queueAnim(animations.get(title));
    }

    /**
     * Returns if the animation queue is empty or not.
     *
     * @return A boolean signaling whether or not the queue is empty.
     */
    public boolean isAnimQueueEmpty() {
        return animQueue.isEmpty();
    }

    /**
     * Clears the current queue of animations.
     * Optionally stops playing the current animation.
     *
     * @param stopPlaying Signals whether or not to stop playing the current animation.
     */
    public void clearAnimQueue(boolean stopPlaying) {
        animQueue.clear();
        if (stopPlaying) {
            playing = false;
            currentAnim = null;
        }
    }

    /**
     * Clears the animation queue.
     *
     * @see AnimationPlayer#clearAnimQueue(boolean)
     */
    public void clearAnimQueue() {
        clearAnimQueue(false);
    }

    /**
     * Completely stops the player from playing.
     * Clears the animation queue and removes the repeating animation.
     */
    public void stop() {
        clearAnimQueue();
        playing = false;
        currentAnim = null;
        removeRepeatingAnim();
    }

    /**
     * Stops the player from playing.
     * Clears the animation map.
     *
     * @see AnimationPlayer#stop()
     */
    public void reset() {
        stop();
        animations.clear();
    }

    /**
     * A helper method used to reset any animation-specific fields to a default state.
     */
    private void resetFieldsForNextAnim() {
        currentFrame = 0;
        playing = true;
    }

    /**
     * Adds an animation to the existing list of animations - duplicate or empty animations are not permitted.
     * Sets the key for that animation to it's title.
     * Returns a value indicating whether or not it was successful.
     *
     * @param anim The animation to be added.
     * @return A boolean signaling if the animation was added successfully.
     */
    public boolean addAnim(Animation anim) {
        //Duplicate anims or anims of 0 length are not permitted.
        if (containsAnim(anim.getTitle())) return false;
        if (anim.getFrameCount() == 0) return false;

        animations.put(anim.getTitle(), anim);
        return true;
    }

    /**
     * Creates an animation and adds it to the animation list.
     *
     * @see AnimationPlayer#addAnim(Animation)
     */
    public boolean addAnim(String title, BufferedImage[] frames) {
        return addAnim(new Animation(title, frames));
    }

    /**
     * Creates an animation and adds it to the animation list.
     *
     * @see AnimationPlayer#addAnim(Animation)
     */
    public boolean addAnim(String title, List<BufferedImage> frames) {
        return addAnim(new Animation(title, frames));
    }

    /**
     * Adds a collection of animations to the animation map by calling addAnim() on each.
     * Returns a value indicating if any of the add calls failed.
     *
     * @see AnimationPlayer#addAnim(Animation)
     *
     * @param animList The collection of animations to be added.
     * @return A boolean signaling if any of the animations could not be added.
     */
    public boolean addAnims(Collection<Animation> animList) {
        for (Animation anim: animList) {
            boolean added = addAnim(anim);
            if (!added) return false;
        }
        return true;
    }

    /**
     * Returns whether or not the passed in title is a key in the animation map.
     *
     * @param title The title to search for in the animation map.
     */
    public boolean containsAnim(String title) {
        return animations.keySet().contains(title);
    }

    /**
     * Gets the frames per second of this AnimationPlayer.
     *
     * @return The frames per second.
     */
    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    /**
     * Sets the frames of the AnimationPlayer.
     *
     * @param framesPerSecond The new frames per second.
     */
    public void setFramesPerSecond(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Sets the repeating animation of the player - the animation played when nothing is queued.
     * Interrupts the current repeating anim, but not a queued or played one.
     *
     * @param anim The animation to be repeated.
     */
    public void setRepeatingAnim(Animation anim) {
        if (anim == repeatingAnim) return;
        Animation newRepeatingAnim = new Animation(anim);
        if (currentAnim == repeatingAnim) {
            currentAnim = newRepeatingAnim;
        }

        repeatingAnim = newRepeatingAnim;
    }

    /**
     * Sets the repeating animation with an animation in the player's animation map.
     *
     * @see AnimationPlayer#setRepeatingAnim(Animation)
     */
    public void setRepeatingAnim(String title) {
        setRepeatingAnim(animations.get(title));
    }

    /**
     * Removes the repeating animation.
     */
    public void removeRepeatingAnim() {
        repeatingAnim = null;
    }

    /**
     * Returns a specific animation from the animation map.
     *
     * @param title The title of the desired animation.
     * @return The value of the animation map at that key.
     */
    public Animation getAnim(String title) {
        return animations.get(title);
    }

    /**
     * Returns the values of the animation hash map.
     *
     * @return A collection of all animations added to the player.
     */
    public Collection<Animation> getAnims() {
        return animations.values();
    }

    /**
     * Returns the current repeating anim.
     *
     * @return The repeating animation.
     */
    public Animation getRepeatingAnim() {
        return repeatingAnim;
    }

}
