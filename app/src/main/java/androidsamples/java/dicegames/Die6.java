package androidsamples.java.dicegames;

import java.util.Random;

/**
 * An implementation of a six-faced {@link Die} using {@link Random}.
 */
public class Die6 implements Die {
    private int topNum;
    private Random rad = new Random();
    public Die6() {
        // TODO implement method
        topNum = 1 + rad.nextInt(6);
    }

    @Override
    public void roll() {
        // TODO implement method
        topNum = 1 + rad.nextInt(6);
    }

    @Override
    public int value() {
        // TODO implement method
        return topNum;
    }
}
