package Game.Entities;

import Game.Sprite.Sprite;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimationEntity extends Entity {
    protected DIRECTION direction;
    public static enum DIRECTION {
        LEFT, RIGHT, UP, DOWN, CENTER
    }
    protected int animate;
    protected final int MAX_ANIMATE = 3000;
    protected Sprite sprite;
    //protected boolean isMoving;

    public AnimationEntity(int x, int y, Image image) {
        super(x, y, image);
        direction = DIRECTION.CENTER;
        animate = 0;
    }

    public AnimationEntity(int x, int y, Image image, int size) {
        super(x, y, image, size);
        direction = DIRECTION.CENTER;
        animate = 0;
        //isMoving = false;
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public void setDirection(DIRECTION direction) {
        this.direction = direction;
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }

    public void update() {

    }

    public Sprite chooseSprite() {
        return null;
    }


}