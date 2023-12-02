package Game.Entities;

import Game.Sprite.Sprite;
import javafx.scene.image.Image;

public class QuestionMark extends Entity {
    @Override
    public void update() {

    }

    public QuestionMark(int x, int y, Image image) {
        super(x, y, image, Sprite.DEFAULT_SIZE);
    }
}
