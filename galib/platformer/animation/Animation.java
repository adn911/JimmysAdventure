package galib.platformer.animation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

    private ArrayList<AnimFrame> frames;
    private int currentFrame;
    private long animTime;
    private long totalDuration;
    public boolean animComplete;
    private boolean looping;

    public Animation() {

        frames = new ArrayList<AnimFrame>();
        totalDuration = 0;
        animTime = 0;
        currentFrame = 0;

        animComplete = false;

    }

    public synchronized void init() {

        animTime = 0;
        currentFrame = 0;
        animComplete = false;
    }

    public synchronized void addFrame(Image image, long duration) {

        totalDuration += duration;
        frames.add(new AnimFrame(image, totalDuration));
    }

    public synchronized void addFramesFromSpriteSheet(Image spriteSheet,
                                                      int rows, int cols, long duration) {

        BufferedImage bufferedImg = new BufferedImage(
                spriteSheet.getWidth(null), spriteSheet.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) bufferedImg.getGraphics();

        g.drawImage(spriteSheet, 0, 0, null);

        int frameWidth = bufferedImg.getWidth() / cols;
        int frameHeight = bufferedImg.getHeight() / rows;
        long frameDuration = duration / (rows * cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Image frame = bufferedImg.getSubimage(j * frameWidth, i
                        * frameHeight, frameWidth, frameHeight);

                addFrame(frame, frameDuration);
            }
        }

        g.dispose();
    }

    public synchronized void update(long elapsedTime) {

        if (frames.size() > 1) {

            if (looping) {

                animTime += elapsedTime;

            } else {

                if (animTime + elapsedTime < totalDuration)
                    animTime += elapsedTime;

                else {

                    animComplete = true;
                }

            }

            if (animTime >= totalDuration) {
                animTime = animTime % totalDuration;
                currentFrame = 0;

            }

            while (animTime > getFrame(currentFrame).endTime) {
                currentFrame++;

            }

        }
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public synchronized void play() {

    }

    public synchronized void stop() {

    }

    public synchronized Image getImage() {
        if (frames.size() == 0) {
            return null;
        } else {
            return getFrame(currentFrame).image;
        }
    }

    public synchronized Image getMirroredImage(int width, int height) {
        if (frames.size() == 0) {
            return null;

        } else {

            Image img = frames.get(currentFrame).image;

            BufferedImage bufferedImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_ARGB);

            Graphics gb = bufferedImage.getGraphics();
            gb.drawImage(img, 0, 0, null);
            gb.dispose();
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-width, 0);
            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);

            // Read more:
            // http://mrbool.com/how-to-display-flip-and-provide-a-reflection-to-the-image-with-java/25983#ixzz31sf8TseL

            return bufferedImage;

        }
    }

    private AnimFrame getFrame(int i) {
        return frames.get(i);
    }

    private class AnimFrame {

        Image image;
        long endTime;

        public AnimFrame(Image image, long endTime) {
            this.image = image;
            this.endTime = endTime;
        }
    }
}
