package pong.GameObject;

import java.awt.geom.Rectangle2D;

public interface Collidable {
    boolean isVisible();
    Rectangle2D.Double getBounds2D();
    void update();
    void handleCollision(Collidable obj);
}
