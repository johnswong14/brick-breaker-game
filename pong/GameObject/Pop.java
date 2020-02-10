package pong.GameObject;

import pong.GameWorld;
import pong.Audio;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Pop extends GameObject {

    private GameWorld gw;
    private Audio audio;
    private double speed = 2;
    private double vx;
    private double vy;
    private int dirX = 1;
    private int dirY = 1;
    private int trajAngle;
    private static final double respawnX = 303;
    private static final double respawnY = 300;
    private static final int RESPAWN_ANGLE = 90;
    private int rotateAngle;
    private static int count;
    private final double resetSpeed = 2;

    public Pop(double x, double y, BufferedImage img, GameWorld gw) {
        super(x, y, img);
        this.gw = gw;
        this.audio = gw.getAudio();
        trajAngle = RESPAWN_ANGLE;
        count += 1;
    }

    private void checkBorder() {
        if ((y + imgHeight) >= GameWorld.SCREEN_HEIGHT-22) {
            count--;
            if (count > 0) {
                isVisible = false;
            }
            else {
                gw.subLives(1);
                count = 1;
                respawn();
                audio.playLost();
            }
        }
    }

    private void reverseVx() {
        dirX = -dirX;
    }

    private void reverseVy() {
        dirY = -dirY;
    }

    public void respawn() {
        gw.setGameStart(false);
        this.x = respawnX;
        this.y = respawnY;
        this.dirX = 1;
        this.dirY = 1;
        this.trajAngle = RESPAWN_ANGLE;
        this.rotateAngle = 0;
    }

    public void resetSpeed() {
        this.speed = resetSpeed;
    }

    public static void setCount(int count) {
        Pop.count = count;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public Rectangle2D.Double getBounds2D() {
        return new Rectangle2D.Double(x, y, imgWidth, imgHeight);
    }

    @Override
    public void update() {
        this.vx = dirX * speed * Math.cos(Math.toRadians(trajAngle));
        this.vy = dirY * speed * Math.sin(Math.toRadians(trajAngle));
        // limit gravitational speed
        if (speed > 4) {
            speed = 4;
        }
        this.x += vx;
        this.y += vy;
        checkBorder();
    }

    @Override
    public void handleCollision(Collidable obj) {
        Rectangle2D.Double popRect = this.getBounds2D();
        Rectangle2D.Double oppRect = obj.getBounds2D();
        Rectangle2D.Double insectRect = (Rectangle2D.Double) popRect.createIntersection(oppRect);

        //System.out.println("pop x: " + this.x + ", pop y: " + this.y);
        //System.out.println("insect x: " + insectRect.x + ", insect y: " + insectRect.y);
        //System.out.println("insect width: " + insectRect.width + ", insect height: " + insectRect.height);

        boolean vertical = false;
        boolean horizontal = false;
        boolean isLeft = false;
        boolean isTop = false;

        // edge detection
        // handles pop getting stuck when hitting precisely between two blocks
        if (insectRect.width != 0 && insectRect.height != 0) {
            // left side or right side
            if (insectRect.width < insectRect.height) {
                // left side
                if (insectRect.x == oppRect.x) {
                    //System.out.println("Left collided");
                    horizontal = true;
                    isLeft = true;
                }
                // right side
                else if (insectRect.x + insectRect.width == oppRect.x + oppRect.width) {
                    //System.out.println("Right collided");
                    horizontal = true;
                }
            }
            // top side or bottom side
            else if (insectRect.width > insectRect.height) {
                // top side
                if (insectRect.y == oppRect.y) {
                    //System.out.println("Top collided");
                    vertical = true;
                    isTop = true;
                }
                // bottom side
                else if (insectRect.y + insectRect.height == oppRect.y + oppRect.height) {
                    //System.out.println("Bottom collided");
                    vertical = true;
                }
            }

            if (horizontal) {
                reverseVx();
                // handles pop getting stuck
                if (isLeft) {
                    this.x = this.x - insectRect.width;
                }
                else {
                    this.x = this.x + insectRect.width;
                }
            }
            else if (vertical) {
                if (isTop) {
                    if (obj instanceof Katch) {
                        Katch katch = (Katch) obj;
                        // calculate angle according to where pop lands on katch
                        trajAngle = (int) (((this.x + this.imgWidth/2 - katch.getX()) * 1.25) + 220);
                        dirX = 1;
                        dirY = 1;
                        speed += 0.01;
                    }
                    else {
                        reverseVy();
                        //System.out.println("Reversing Y.." + dirY);
                    }
                    // handles pop getting stuck
                    //System.out.println("Top, Before: " + this.y);
                    this.y = this.y - insectRect.height;
                    //System.out.println("Top, After: " + this.y + "\n");
                }
                else {
                    reverseVy();
                    // handles pop getting stuck
                    //System.out.println("Bottom, Before: " + this.y);
                    this.y = this.y + insectRect.height;
                    //System.out.println("Bottom, After: " + this.y + "\n");
                    speed += 0.01;
                }
            }
        }
    }

    @Override
    public void drawImage(Graphics2D g2d) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        rotation.rotate(Math.toRadians(this.rotateAngle++), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        g2d.drawImage(this.img, rotation, null);
    }
}
