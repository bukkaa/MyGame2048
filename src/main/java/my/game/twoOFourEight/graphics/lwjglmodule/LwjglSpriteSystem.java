package my.game.twoOFourEight.graphics.lwjglmodule;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * система загрузки и хранения спрайтов для LWJGL
 */
public class LwjglSpriteSystem {

    /**
     * загружает и хранит все доступные в игре текстуры
     */
    enum LwjglSprite {
        CELL2("2"), CELL4("4"), CELL8("8"), CELL16("16"), CELL32("32"), CELL64("64"),
        CELL128("128"), CELL256("256"), CELL512("512"), CELL1024("1024"), CELL2048("2048"), EMPTY("empty");

        private Texture texture;

        LwjglSprite (String textureName) {
            try {
                this.texture = TextureLoader.getTexture("PNG", new FileInputStream( new File("res/" + textureName + ".png") ) );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Texture getTexture() {
            return this.texture;
        }
    }

    /**
     * хранит в себе ссылки на все доступные в игре текстуры с ключом, равным изображенной на текстуре цифре
     */
    private HashMap<Integer, LwjglSprite> spriteByNumber;

    /**
     * инициализирует HashMap spriteByNumber и записывает в него ссылки
     * на все доступные текстуры
     */
    LwjglSpriteSystem() {
        spriteByNumber = new HashMap<>();
                spriteByNumber.put(2, LwjglSprite.CELL2);
                spriteByNumber.put(4, LwjglSprite.CELL4);
                spriteByNumber.put(8, LwjglSprite.CELL8);
                spriteByNumber.put(16, LwjglSprite.CELL16);
                spriteByNumber.put(32, LwjglSprite.CELL32);
                spriteByNumber.put(64, LwjglSprite.CELL64);
                spriteByNumber.put(128, LwjglSprite.CELL128);
                spriteByNumber.put(256, LwjglSprite.CELL256);
                spriteByNumber.put(512, LwjglSprite.CELL512);
                spriteByNumber.put(1024, LwjglSprite.CELL1024);
                spriteByNumber.put(2048, LwjglSprite.CELL2048);
    }

    /**
     * @param n число, которое должна изображать текстура
     * @return Текстура, изображающая запрашиваемое число. Если такого нет, возвращает EMPTY
     */
    public LwjglSprite getSpriteByNumber(int n) {
        if (spriteByNumber.containsKey(n)) return spriteByNumber.get(n);

        return LwjglSprite.EMPTY;
    }
}
