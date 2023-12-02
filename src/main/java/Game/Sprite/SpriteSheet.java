package Game.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {
    private String path;
    public final int SIZE;
    public int[] _pixels;
    public BufferedImage image;

    public static SpriteSheet tiles = new SpriteSheet("/Game/img/gamesheet.png", 200);

    public SpriteSheet(String path, int size) {
        this.path = path;
        SIZE = size;
        _pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            URL url = SpriteSheet.class.getResource(path);
            image = ImageIO.read(url);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, _pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

