package com.kai.fp.objs;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A class representing an animation, or a
 * collection of image frames underneath a title.
 *
 * @author Kai Tinkess on May 3rd, 2019
 */

public class Animation {

    /**
     * The title/name of the animation.
     */
    private String title;
    /**
     * A collection of every frame in the animation.
     */
    private BufferedImage[] frames;

    /**
     * Creates an animation with a title and passed in array of frames.
     *
     * @param title The title assigned to the anim.
     * @param frames An array of BufferedImages with each being a single frame.
     */
    public Animation(String title, BufferedImage[] frames) {
        this.title = title;
        this.frames = frames;
    }

    /**
     * Creates the animation with a default title and the given frame list.
     *
     * @see Animation#Animation(String, BufferedImage[])
     */
    public Animation(BufferedImage[] frames) {
        this("untitled", frames);
    }

    /**
     * Creates the animation from the given title and collection of frames.
     * The collection is converted to a primitive array internally.
     *
     * @see Animation#Animation(String, BufferedImage[])
     */
    public Animation(String title, Collection<BufferedImage> frames) {
        this(title, (BufferedImage[]) frames.toArray());
    }

    /**
     * Creates the animation with a default title and the given collection of frames.
     *
     * @see Animation#Animation(List)
     */
    public Animation(List<BufferedImage> frames) {
        this("untitled", (BufferedImage[]) frames.toArray());
    }

    /**
     * Creates an animation with the data in the given animation.
     *
     * @param anim The animation that is being copied.
     */
    public Animation(Animation anim) {
        this.title = anim.getTitle();
        this.frames = anim.getFrames();
    }

    /**
     * Creates an animation with a default title and an empty string array.
     *
     * @see Animation#Animation(String, BufferedImage[])
     */
    public Animation() {
        this("untitled", new BufferedImage[] {});
    }

    /**
     * Get a single frame of the frame array.
     *
     * @param index The specific index of a single frame in the array of images.
     * @return The frame listed at that index.
     */
    public BufferedImage getFrame(int index) {
        return frames[index];
    }

    /**
     * Get the title of this animation.
     *
     * @return This animation's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the frames of this animation.
     *
     * @return The animation's frames, held in a primitive array.
     */
    public BufferedImage[] getFrames() {
        return frames;
    }

    /**
     * Set the title of the animation.
     *
     * @param title The new title of the animation.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the frame list of the animation.
     *
     * @param frames The new array of frames.
     */
    public void setFrames(BufferedImage[] frames) {
        this.frames = frames;
    }

    /**
     * Sets a single frame in the animation to a new image.
     *
     * @param newFrame A new frame to replace one in the animation.
     * @param index The index of the frame that is replaced.
     */
    public void setFrame(BufferedImage newFrame, int index) {
        frames[index] = newFrame;
    }

    /**
     * Adds a single frame into a specified index in the frame array.
     * Index can be one greater than the existing array length.
     *
     * @param newFrame The new image to be inserted into the array.
     * @param index The index the new frame will be inserted at.
     * @return A boolean representing if the insertion was successful.
     */
    public boolean addFrame(BufferedImage newFrame, int index) {
        if (index > getFrameCount() || index < 0) return false;

        BufferedImage[] array = new BufferedImage[getFrameCount()+1];
        boolean located = false;
        for (int i = 0; i < frames.length; i++) {
            if (i == index) {
                array[i] = newFrame;
                located = true;
            }
            array[i] = frames[i - ((located) ? 1 : 0)];
        }
        frames = array;

        return true;
    }

    /**
     * Gets the length of the frame array.
     *
     * @return The number of frames in this animation.
     */
    public int getFrameCount() {
        return frames.length;
    }


    @Override
    public String toString() {
        return "Animation{" +
                "title='" + title + '\'' +
                ", frames=" + Arrays.toString(frames) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animation animation = (Animation) o;
        return Objects.equals(title, animation.title) &&
                Arrays.equals(frames, animation.frames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(title);
        result = 31 * result + Arrays.hashCode(frames);
        return result;
    }
}
