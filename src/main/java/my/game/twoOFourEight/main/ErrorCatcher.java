package my.game.twoOFourEight.main;

public class ErrorCatcher {

    /**
     * Ошибка создания новой ячейки
     */
    public static void cellCreationFailure() {
        System.err.println("Main class failed to create new cell.");
        System.exit(-1);
    }

    /**
     * передача неверного параметра Direction в метод сдвига.
     */
    public static void shiftFailureWrongParam() {
        System.err.println("Main class failed to shift cells on field. Wrong parameter.");
        System.exit(-2);
    }

    /**
     * внутренняя ошибка графического модуля
     *
     * @param e выброшенное исключение
     */
    public static void graphicsFailure (Exception e) {
        System.err.println("GraphicsModule failed.");
        e.printStackTrace();
        System.exit(-3);
    }
}
