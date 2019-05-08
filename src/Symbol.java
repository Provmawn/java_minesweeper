public enum Symbol {
    INCORRECT_FLAG,
    CORRECT_FLAG,
    REVEALED_INCORRECT_FLAG,
    INCORRECT_QUESTION_MARK,
    CORRECT_QUESTION_MARK,
    REVEALED,
    BUTTON,
    MINE,
    HIDDEN_MINE,
    MINE_RED,
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
