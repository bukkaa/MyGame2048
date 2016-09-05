package my.game.twoOFourEight.keyboard.lwjglmodule;

import my.game.twoOFourEight.keyboard.KeyboardHandleModule;
import my.game.twoOFourEight.main.Direction;
import org.lwjgl.input.Keyboard;

public class LwjglKeyboardHandleModule implements KeyboardHandleModule {

    // данные о вводе за последнюю итерацию
    private boolean wasEscPressed;
    private Direction lastDirectionKeyPressed;

    /**
     * считаывает последние данные из стека событий
     */
    @Override
    public void update() {
        resetValues();
        lastDirectionKeyPressed = Direction.AWAITING;

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_ESCAPE:
                        wasEscPressed = true;
                        break;
                    case Keyboard.KEY_UP:
                        lastDirectionKeyPressed = Direction.UP;
                        break;
                    case Keyboard.KEY_RIGHT:
                        lastDirectionKeyPressed = Direction.RIGHT;
                        break;
                    case Keyboard.KEY_DOWN:
                        lastDirectionKeyPressed = Direction.DOWN;
                        break;
                    case Keyboard.KEY_LEFT:
                        lastDirectionKeyPressed = Direction.LEFT;
                        break;
                }
            }
        }
    }

    /**
     * обнуление данных о предыдущих нажатиях
     */
    private void resetValues() {
        lastDirectionKeyPressed = Direction.AWAITING;
        wasEscPressed = false;
    }

    /**
     * @return возвращает направление нажатой "стрелочки",
     * либо AWAITING, если не было нажато ни одной
     */
    @Override
    public Direction lastDirectionKeyPressed() {
        return lastDirectionKeyPressed;
    }

    /**
     * @return возвращает информацию о том, была ли нажата клавиша ESCAPE
     */
    @Override
    public boolean wasEscPressed() {
        return wasEscPressed;
    }

}
