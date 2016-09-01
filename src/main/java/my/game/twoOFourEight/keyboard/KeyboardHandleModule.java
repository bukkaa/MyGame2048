package my.game.twoOFourEight.keyboard;

import my.game.twoOFourEight.main.Direction;

public interface KeyboardHandleModule {

    /**
     * считаывает последние данные из стека событий
     */
    void update();

    /**
     * @return возвращает направление нажатой "стрелочки",
     * либо AWAITING, если не было нажато ни одной
     */
    Direction lastDirectionKeyPressed();

    /**
     * @return возвращает информацию о том, была ли нажата клавиша ESCAPE
     */
    boolean wasEscPressed();
}
