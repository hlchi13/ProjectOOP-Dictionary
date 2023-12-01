package Game.Entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.*;

public abstract class Entity {
    protected int x;
    protected int y;
    protected Image img;

    public Entity() {}

    public Entity(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public Entity(int xUnit, int yUnit, Image img, int size) {
        this.x = xUnit * size;
        this.y = yUnit* size;
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public abstract void update();

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}
