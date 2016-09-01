package my.game.twoOFourEight.graphics.lwjglmodule;

import my.game.twoOFourEight.main.ErrorCatcher;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import my.game.twoOFourEight.graphics.GraphicsModule;
import my.game.twoOFourEight.main.GameField;

import static my.game.twoOFourEight.main.Constants.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glClearColor;

public class LwjglGraphicsModule implements GraphicsModule {

    private LwjglSpriteSystem spriteSystem;

    /**
     * инициализирует графический движок и необходимые поля модуля.
     */
    public LwjglGraphicsModule() {
        initOpengl();
        spriteSystem = new LwjglSpriteSystem();
    }

    private void initOpengl() {
        try {
            // задаем размер будущего окна
            Display.setDisplayMode( new DisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT) );

            // имя будущего окна
            Display.setTitle(SCREEN_NAME);

            // создаем окно
            Display.create();
        } catch (LWJGLException e) {
            ErrorCatcher.graphicsFailure(e);
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, SCREEN_WIDTH, 0, SCREEN_HEIGHT, 1, -1);
        glMatrixMode(GL_MODELVIEW);

        // поддержка текстур
        glEnable(GL_TEXTURE_2D);

        // альфа канал, прозрачность
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_CONSTANT_ALPHA);

        // белый фон
        glClearColor(1,1,1,1);
    }

    /**
     * отрисовывает переданное игровое поле
     *
     * @param field игровое поле, которое необходимо отрисовать
     */
    @Override
    public void draw(GameField field) {
        glClear(GL_COLOR_BUFFER_BIT);

        for (int i = 0; i < COUNT_CELLS_X; i++)
            for (int j = 0; j < COUNT_CELLS_Y; j++)
                drawCell(CELL_SIZE*i, CELL_SIZE*j, field.getState(i,j) );

        Display.update();
        Display.sync(60);
    }

    /**
     * отрисовывает отдельную ячейку
     *
     * @param x координата Х
     * @param y Координата Y
     * @param state состояние ячейки
     */
    private void drawCell(int x, int y, int state) {
        spriteSystem.getSpriteByNumber(state).getTexture().bind();

        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y + CELL_SIZE);
        glTexCoord2f(1, 0);
        glVertex2f(x + CELL_SIZE, y + CELL_SIZE);
        glTexCoord2f(1, 1);
        glVertex2f(x + CELL_SIZE, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y);
        glEnd();
    }

    /**
     * @return возвращает true, если в окне нажат "крестик"
     */
    @Override
    public boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    /**
     * заключительные действия.
     * принудительное уничтожение окна.
     */
    @Override
    public void destroy() {
        Display.destroy();
    }
}