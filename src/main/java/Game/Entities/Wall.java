package Game.Entities;

import Game.Sprite.Sprite;
import javafx.scene.image.Image;

public class Wall extends Entity {
    @Override
    public void update() {

    }

    public Wall(int x, int y, Image image) {
        super(x, y, image, Sprite.DEFAULT_SIZE);
    }
}
