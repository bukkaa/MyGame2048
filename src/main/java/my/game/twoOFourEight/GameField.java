package my.game.twoOFourEight;

import static my.game.twoOFourEight.Constants.*;

public class GameField {

    /**
     * состояние всех ячеек поля
     */
    private int[][] theField;

    /**
     * инициализирует поле и зануляет его
     */
    public GameField(){
        theField = new int[COUNT_CELLS_X][COUNT_CELLS_Y];

        for (int i = 0; i<theField.length; i++) {
            for (int j = 0; j<theField[i].length; j++)
                theField[i][j] = 0;
        }
    }

    /**
     * возвращает состояние ячейки поля по координатам
     *
     * @param x Координата ячейки X
     * @param y Координата ячейки Y
     * @return Состояние выбранной ячейки
     */
    public int getState(int x, int y) {
        return theField[x][y];
    }

    /**
     * изменяет состояние ячейки по координатам
     *
     * @param x Координата ячейки X
     * @param y Координата ячейки Y
     * @param state Новое состояние для этой ячейки
     */
    public void setState(int x, int y, int state) {
        // TODO Check input maybe?

        theField[x][y] = state;
    }

    /**
     * изменяет состояние i-ого столбца
     *
     * @param i Номер столбца
     * @param newColumn Массив новых состояний ячеек столбца
     */
    public void setColumn(int i, int[] newColumn) {
        theField[i] = newColumn;
    }

    /**
     * возвращает массив состояний ячеек столбца i
     *
     * @param i Номер запрашиваемого столбца
     * @return Массив состояний ячеек столбца
     */
    public int[] getColumn(int i) {
        return theField[i];
    }

    /**
     * изменяет состояние i-ой строки
     *
     * @param i Номер строки
     * @param newLine Массив новых состояний ячеек строки
     */
    public void setLine(int i, int[] newLine) {
        for (int j = 0; j < COUNT_CELLS_X; j++)
            theField[j][i] = newLine[j];
    }

    /**
     * возвращает массив состояний ячеек строки i
     *
     * @param i Номер запрашиваемой строки
     * @return Массив состояний ячеек строки
     */
    public int[] getLine(int i){
        int[] res = new int[COUNT_CELLS_X];

        for (int j = 0; j < COUNT_CELLS_X; j++)
            res[j] = theField[j][i];

        return res;
    }
}
