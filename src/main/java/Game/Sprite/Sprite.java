package Game.Sprite;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Lưu trữ thông tin các pixel của 1 sprite (hình ảnh game)
 */
public class Sprite {
    public static final int DEFAULT_SIZE = 50;
    private static final int TRANSPARENT_COLOR = 0xffffff;
    public final int SIZE;
    private int x, y;
    public int[] pixels;
    private SpriteSheet sheet;

    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        pixels = new int[SIZE * SIZE];
        this.x = x * SIZE;
        this.y = y * SIZE;
        this.sheet = sheet;
        load();
    }

    private void load() {
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                pixels[x + y * SIZE] = sheet._pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
            }
        }
    }

    public static Sprite grass = new Sprite(DEFAULT_SIZE, 0, 2, SpriteSheet.tiles);
    public static Sprite wall = new Sprite(DEFAULT_SIZE, 1, 2, SpriteSheet.tiles);
    public static Sprite correct_mark = new Sprite(DEFAULT_SIZE, 1, 1, SpriteSheet.tiles);
    public static Sprite question_mark = new Sprite(DEFAULT_SIZE, 0, 1, SpriteSheet.tiles);

    public static Sprite cat1 = new Sprite(DEFAULT_SIZE, 0, 0, SpriteSheet.tiles);
    public static Sprite cat2 = new Sprite(DEFAULT_SIZE, 1, 0, SpriteSheet.tiles);
    public static Sprite[] cat = {cat1, cat2};

    public static Sprite movingSprite(Sprite[] sprites, int animate, int time, int totalFrame) {
        int n = sprites.length;
        for (int i = 0; i < n; i++) {
            if (animate % time < (i + 1) * time / totalFrame) return sprites[i];
        }
        return sprites[n-1];
    }


    public Image getFxImage() {
        WritableImage wr = new WritableImage(SIZE, SIZE);
        PixelWriter pw = wr.getPixelWriter();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (pixels[x + y * SIZE] == TRANSPARENT_COLOR) {
                    pw.setArgb(x, y, 0);
                } else {
                    pw.setArgb(x, y, pixels[x + y * SIZE]);
                }
            }
        }
        Image input = new ImageView(wr).getImage();
        return input;
    }

}
