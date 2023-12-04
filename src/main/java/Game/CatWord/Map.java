package Game.CatWord;

import Game.Entities.*;
import Game.Sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    public static List<List<Entity>> map;
    protected int[][] itemList;
    public static final int HEIGHT = 8;
    public static final int WIDTH = 8;
    private Cat cat;
    private Crown crown;

    public Map() {
        map = new ArrayList<>();
        cat = new Cat(0, 0, Sprite.cat1.getFxImage());
        crown = new Crown(7, 7, Sprite.crown.getFxImage());
        itemList = new int[HEIGHT][WIDTH];
        try {
            String fileName = "./src/main/resources/Game/map.txt";
            FileInputStream fileInputStream = new FileInputStream(fileName);
            Scanner sc = new Scanner(fileInputStream);
            while (sc.hasNextLine()) {
                for (int i = 0; i < HEIGHT; i++) {
                    String line = sc.nextLine();
                    List<Entity> list = new ArrayList<>();
                    for (int j = 0; j < WIDTH; j++) {
                        switch(line.charAt(j)) {
                            case '#':
                                list.add(new Wall(j, i, Sprite.wall.getFxImage()));
                                break;
                            case '?':
                                list.add(new QuestionMark(j, i, Sprite.question_mark.getFxImage()));
                                break;
                            case '.':
                                list.add(new Grass(j, i, Sprite.grass.getFxImage()));
                                break;
                            default:
                                break;
                        }
                    }
                    map.add(list);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Cat getCat() {
        return cat;
    }

    public void updateMap(int x, int y, Entity entity) {
        map.get(y).set(x, entity);
    }

    public void update(KeyEvent event) {
        cat.update(event);
    }

    public void render(GraphicsContext gc) {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity entity = map.get(i).get(j);
                entity.render(gc);
            }
        }
        cat.render(gc);
        crown.render(gc);
    }

}