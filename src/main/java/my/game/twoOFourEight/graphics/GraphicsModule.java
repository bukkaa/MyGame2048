package my.game.twoOFourEight.graphics;

import my.game.twoOFourEight.main.GameField;

public interface GraphicsModule {

    /**
     * отрисовывает переданное игровое поле
     * @param field
     */
    void draw(GameField field);

    /**
     * @return возвращает true, если в окне нажать "крестик"
     */
    boolean isCloseRequested();

    /**
     * заключительные действия на случай, если модулю нужно подчистить за собой
     */
    void destroy();
}
