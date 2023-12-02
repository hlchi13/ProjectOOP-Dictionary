package Game.Entities;

import Game.Sprite.Sprite;
import javafx.scene.image.Image;

public class CorrectMark extends Entity {

    @Override
    public void update() {

    }

    public CorrectMark(int x, int y, Image image) {
        super(x, y, image, Sprite.DEFAULT_SIZE);
    }
}
