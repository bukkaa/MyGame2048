package my.game.twoOFourEight.keyboard.lwjglmodule;

import my.game.twoOFourEight.keyboard.KeyboardHandleModule;
import my.game.twoOFourEight.main.Direction;

public class LwjglKeyboardHandleModule implements KeyboardHandleModule {
    @Override
    public void update() {

    }

    @Override
    public Direction lastDirectionKeyPressed() {
        return null;
    }

    @Override
    public boolean wasEscPressed() {
        return false;
    }
}
