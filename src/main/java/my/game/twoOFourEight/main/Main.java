package my.game.twoOFourEight.main;


import my.game.twoOFourEight.graphics.GraphicsModule;
import my.game.twoOFourEight.graphics.lwjglmodule.LwjglGraphicsModule;
import my.game.twoOFourEight.keyboard.KeyboardHandleModule;

import java.util.Random;

public class Main {

    /**
     * результат работы метода сдвига shiftRow().
     * содержит измененную строку и информацию о том, эквивалентна ли она начальной.
     */
    private static class ShiftRowResult {
        boolean didAnythingMove;
        int[] shiftedRow;
    }

    private static int score; // очки - сумма всех чисел на поле
    private static boolean endOfGame; // флаг для завершения основного цикла программы
    private static boolean isThere2048; // хранит информацию о том, удалось ли игроку собрать 2048 (флаг победы)
    private static Direction direction; // направление сдвига поля
    private static GameField gameField; //игровое поле
    private static GraphicsModule graphicsModule; // модуль графики
    private static KeyboardHandleModule keyboardModule; // обработчик клавиатуры

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
     * задает значения полей для начала игры
     */
    private static void initFields() {
        score = 0;
        endOfGame = false;
        isThere2048 = false;
        direction = Direction.AWAITING;
        graphicsModule = new LwjglGraphicsModule();
        // TODO keyboardModule = new
        gameField = new GameField();
    }

    /**
     * создаем на поле начальные ячейки
     */
    private static void createInitialCells() {
        for (int i = 0; i < Constants.COUNT_INITITAL_CELLS; i++)
            generateNewCell();
    }

    /**
     *
     * создание в случайной пустой клетке игрового поля плитку с ненулевым состоянием
     */
    private static void generateNewCell() {
        // инициализируем состояние новой клетки (учитываем шанс появления клетки на 4, а не на 2)
        int state = (new Random().nextInt(100) <= Constants.CHANCE_OF_LUCKY_SPAWN)
                ? Constants.LUCKY_INITIAL_CELL_STATE
                : Constants.INITIAL_CELL_STATE;

        int randX, randY;

        // рандомим координаты новой клетки
        randX = new Random().nextInt(Constants.COUNT_CELLS_X);
        int currentX = randX;

        randY = new Random().nextInt(Constants.COUNT_CELLS_Y);
        int currentY = randY;

        boolean placed = false;

        // проверяем, существует ли уже клетка в полученных координатах
        while (!placed) {
            if (gameField.getState(currentX, currentY) == 0) {
                gameField.setState(currentX, currentY, state);
                placed = true;
            } else {
                if (currentX+1 < Constants.COUNT_CELLS_X) currentX++;
                else {
                    currentX = 0;
                    if(currentY+1 < Constants.COUNT_CELLS_Y) currentY++;
                    else currentY = 0;
                }

                if ( (currentX == randX) && (currentY == randY) ) // Нет места -> что-то пошло не так
                    // TODO Создать класс ErrorCatcher для отлова ошибок
                    ErrorCatcher.cellCreationFailure();
            }
        }

        score += state;


    }

    /**
     * описывает действия в случае победы пользователя (если пользователь создал плитку 2048).
     *
     * сейчас: устанавливает флаг победы на true, завершает игру.
     */
    private static void merged2048() {
        endOfGame = true;
        isThere2048 = true;
    }

    /**
     * считывает пользовательский ввод.
     * изменяет состояние Main.direction и endOfGame
     */
    private static void input() {
        keyboardModule.update();

        // Определяем направление, в котором нужно произвести сдвиг
        direction = keyboardModule.lastDirectionKeyPressed();

        endOfGame = endOfGame || graphicsModule.isCloseRequested() || keyboardModule.wasEscPressed();
    }

    /**
     * основная логика игры.
     *
     * если пользователь определил направление, вызывает метод сдвига.
     * если сдвиг удался, создает новую плитку.
     */
    private static void logic() {
        if (direction != Direction.AWAITING) {
            if (shift(direction)) generateNewCell();

            direction = Direction.AWAITING;
        }
    }

    /**
     * Изменяет gameField, сдвигая все ячейки в указанном направлении,
     * выывая shiftRow() для каждой строки/столбца (в зависимости от направления)
     *
     * @param direction Направление сдвига
     * @return возвращает true, если сдвиг прошел успешно (поле изменилось)
     */
    private static boolean shift(Direction direction) {
        boolean ret = false;

        switch (direction) {
            case UP:
            case DOWN:
                // по очереди сдвигаем числа всех столбцов в нужном направлении
                for (int i = 0; i < Constants.COUNT_CELLS_X; i++) {
                    // запрашиваем очередной столбец
                    int[] arg = gameField.getColumn(i);

                    // в зависимости от направления сдвига, меняем или не меняем порядок чисел на противоположный
                    if (direction == Direction.UP) {
                        int[] tmp = new int[arg.length];
                        for (int e = 0; e < tmp.length; e++)
                            tmp[e] = arg[tmp.length-e-1];
                        arg = tmp;
                    }

                    // пытаемся сдвинуть числа в этом столбце
                    ShiftRowResult result = shiftRow(arg);

                    // возвращаем линию в исходный порядок
                    if (direction == Direction.UP) {
                        int[] tmp = new int[result.shiftedRow.length];
                        for (int e = 0; e < tmp.length; e++)
                            tmp[e] = result.shiftedRow[tmp.length-e-1];
                        result.shiftedRow = tmp;
                    }

                    // записываем измененный столбец
                    gameField.setColumn(i, result.shiftedRow);

                    // если хоть одна линия была изменена, значит было изменено все поле
                    ret = ret || result.didAnythingMove;
                }
                break;
            case LEFT:
            case RIGHT:
            default:
                ErrorCatcher.shiftFailureWrongParam();
                break;
        }

        return ret;
    }

    /**
     * Сдвигает и совмещает числа в линии по следующим парвилам:
     * 1) если в ряде есть нули, они из ряда удаляются;
     * 2) если любые два соседних числа равны, то вместо них записывается одно число - их сумма;
     * 3) если число получено через п.2, оно не складывается с другими числами;
     * 4) првоерка чисел на равенство и их совмещение происходит слева направо, т.е. от 0-ого элемента к последнему.
     *
     * если в результате сдвига получилось 2048 вызывается метод merged2048().
     *
     * @param oldRow линия, члены которой необходимо сдвинуть и сложить.
     * @return возвращает true, если сдвиг прошел успешно (поле изменилось).
     */
    private static ShiftRowResult shiftRow (int[] oldRow) {
        ShiftRowResult ret = new ShiftRowResult();

        int[] oldRowWithoutZeroes = new int[oldRow.length];
        {
            int q = 0;

            for (int i = 0; i < oldRow.length; i++) {
                if (oldRow[i] != 0) {
                    if (q != i)
                        /*
                         * это значит, что мы передвинули ячейку
                         * на место какого-то нуля (пустой плитки)
                         */
                        ret.didAnythingMove = true;

                    oldRowWithoutZeroes[q] = oldRow[i];
                    q++;
                }
            }

            // чтобы избежать null в конце массива
            for (int i = q; i < oldRowWithoutZeroes.length; i++)
                oldRowWithoutZeroes[i] = 0;
        }

        ret.shiftedRow = new int[oldRowWithoutZeroes.length];

        {
            int q = 0;
            {
                int i = 0;

                while (i < oldRowWithoutZeroes.length) {
                    if ( (i+1 < oldRowWithoutZeroes.length)
                            && (oldRowWithoutZeroes[i] == oldRowWithoutZeroes[i+1])
                            && oldRowWithoutZeroes[i] != 0 ) {
                        ret.didAnythingMove = true;
                        ret.shiftedRow[q] = oldRowWithoutZeroes[i] * 2;
                        if (ret.shiftedRow[q] == 2048)
                            merged2048();
                        i++;
                    }
                    else
                        ret.shiftedRow[q] = oldRowWithoutZeroes[i];

                    q++;
                    i++;
                }
            }
            // чтобы избежать null в конце массива
            for (int j = q; j < ret.shiftedRow.length; j++)
                ret.shiftedRow[j] = 0;
        }

        return ret;
    }

    /**
     * выводит на экран результат игры пользователя -- победа или поражения, очки
     */
    private static void printGameResult() {
        System.out.println("You " + (isThere2048 ? "won :)" : " lost :(") );
        System.out.println("Your score is " + score);
    }
}














