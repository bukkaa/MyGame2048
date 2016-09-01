package my.game.twoOFourEight;


import static my.game.twoOFourEight.Constants.*;

public class Main {
    public static void main(String[] args) {
        initFields();
        createInitialCells();

        while(!endOfGame)
        {
            input();
            logic();

            graphicsModule.draw(gameField);
        }

        graphicsModule.destroy();
    }

    /**
     * создаем на поле начальные ячейки
     */
    public static void createInitialCells() {
        for (int i = 0; i < COUNT_INITITAL_CELLS; i++)
            generateNewCell();
    }

    public static void generateNewCell() {

    }
}
