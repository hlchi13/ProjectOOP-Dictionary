package Game.Entities;

import Game.CatWord.Map;
import Game.Sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Cat extends AnimationEntity {
    private int xUnit;
    private int yUnit;
    private int preX;
    private int preY;
    private boolean isQuestion;


    public Cat(int xUnit, int yUnit, Image image) {
        super(xUnit, yUnit, image, Sprite.DEFAULT_SIZE);
        this.xUnit = xUnit;
        this.yUnit = yUnit;
        preX = 0;
        preY = 0;
        isQuestion = false;
    }

    public int getxUnit() {
        return xUnit;
    }

    public int getyUnit() {
        return yUnit;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    @Override
    public Sprite chooseSprite() {
        animate();
        sprite = Sprite.movingSprite(Sprite.cat, animate, 100, 2);
        return sprite;
    }

    public void update(KeyEvent event) {
        if (event.getCode().equals(KeyCode.UP)) {
            if (canMove(xUnit, yUnit, DIRECTION.UP)) {
                preY = yUnit;
                preX = xUnit;
                yUnit--;
                y = yUnit * Sprite.DEFAULT_SIZE;
            }
        } else if (event.getCode().equals(KeyCode.DOWN)) {
            if (canMove(xUnit, yUnit, DIRECTION.DOWN)) {
                preY = yUnit;
                preX = xUnit;
                yUnit++;
                y = yUnit * Sprite.DEFAULT_SIZE;
            }
        } else if (event.getCode().equals(KeyCode.LEFT)) {
            if (canMove(xUnit, yUnit, DIRECTION.LEFT)) {
                preX = xUnit;
                preY = yUnit;
                xUnit--;
                x = xUnit * Sprite.DEFAULT_SIZE;
            }
        } else if (event.getCode().equals(KeyCode.RIGHT)) {
            if (canMove(xUnit, yUnit, DIRECTION.RIGHT)) {
                preX = xUnit;
                preY = yUnit;
                xUnit++;
                x = xUnit * Sprite.DEFAULT_SIZE;
            }
        }
    }

    public boolean canMove(int x, int y, DIRECTION direction) {
        if (direction == DIRECTION.UP) {
            int yUp = y - 1;
            if (yUp < 0 || yUp >= Map.HEIGHT) {
                return false;
            }
            Entity entityUp = Map.map.get(yUp).get(x);
            if (entityUp instanceof Wall) {
                return false;
            } else if (entityUp instanceof QuestionMark) {
                isQuestion = true;
                return true;
            }
            return true;
        } else if (direction == DIRECTION.DOWN) {
            int yDown = y + 1;
            if (yDown < 0 || yDown >= Map.HEIGHT) {
                return false;
            }
            Entity entityDown = Map.map.get(yDown).get(x);
            if (entityDown instanceof Wall) {
                return false;
            } else if (entityDown instanceof QuestionMark) {
                isQuestion = true;
                return true;
            }
            return true;
        } else if (direction == DIRECTION.LEFT) {
            int xLeft = x - 1;
            if (xLeft < 0 || xLeft >= Map.WIDTH) {
                return false;
            }
            Entity entityLeft = Map.map.get(y).get(xLeft);
            if (entityLeft instanceof Wall) {
                return false;
            } else if (entityLeft instanceof QuestionMark) {
                isQuestion = true;
                return true;
            }
            return true;
        } else {
            int xRight = x + 1;
            if (xRight < 0 || xRight >= Map.WIDTH) {
                return false;
            }
            Entity entityRight = Map.map.get(y).get(xRight);
            if (entityRight instanceof Wall) {
                return false;
            } else if (entityRight instanceof QuestionMark) {
                isQuestion = true;
                return true;
            }
        }
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        img = chooseSprite().getFxImage();
        gc.drawImage(img, x, y);
    }

    public void back() {
        xUnit = preX;
        yUnit = preY;
        x = xUnit * Sprite.DEFAULT_SIZE;
        y = yUnit * Sprite.DEFAULT_SIZE;
    }
}
