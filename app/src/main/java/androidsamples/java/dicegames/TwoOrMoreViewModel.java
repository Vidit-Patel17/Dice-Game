package androidsamples.java.dicegames;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} for the gambling game that allows the user to choose a game type, set a wager, and then play.
 */
public class TwoOrMoreViewModel extends ViewModel {
    private int balance;
    private int wager;
    private GameType gameType;
    private List<Die> dieList;
    private boolean wagerSet;

    /**
     * No argument constructor.
     */
    public TwoOrMoreViewModel() {
        dieList = new ArrayList<>();
        wager = -1;
        wagerSet = false;
    }

    /**
     * Reports the current balance.
     *
     * @return the balance
     */
    public int balance() {
        return balance;
    }

    /**
     * Sets the balance to the given amount.
     *
     * @param balance the given amount
     */
    public void setBalance(int balance) {
        this.balance = balance;
    }

    /**
     * Reports the current game type as one of the values of the {@code enum} {@link GameType}.
     *
     * @return the current game type
     */
    public GameType gameType() {
        return gameType;
    }

    /**
     * Sets the current game type to the given value.
     *
     * @param gameType the game type to be set
     */
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     * Reports the wager amount.
     *
     * @return the wager amount
     */
    public int wager() {
        return wager;
    }

    /**
     * Sets the wager to the given amount.
     *
     * @param wager the amount to be set
     */
    public void setWager(int wager) {
        this.wager = wager;
        wagerSet = true;
    }

    /**
     * Reports whether the wager amount is valid for the given game type and current balance.
     * For {@link GameType#TWO_ALIKE}, the balance must be at least twice as much, for {@link GameType#THREE_ALIKE}, at least thrice as much, and for {@link GameType#FOUR_ALIKE}, at least four times as much.
     * The wager must also be more than 0.
     *
     * @return {@code true} iff the wager set is valid
     */
    public boolean isValidWager() {
        if((gameType == null) || (wager <= 0) || (!wagerSet))
            return false;
        return balance >= wager * gameTypeToInt();
    }

    /**
     * Returns the current values of all the dice.
     *
     * @return the values of dice
     */
    public List<Integer> diceValues() {
        List<Integer> die_value = new ArrayList<>();
        for(Die i : dieList)
            die_value.add(i.value());
        return die_value;
    }

    /**
     * Adds the given {@link Die} to the game.
     *
     * @param d the Die to be added
     */
    public void addDie(Die d) {
        if(dieList.size() < 4)
            dieList.add(d);
    }

    /**
     * Simulates playing the game based on the type and the wager and reports the result as one of the values of the {@code enum} {@link GameResult}.
     *
     * @return result of the current game
     * @throws IllegalStateException if the wager or the game type was not set to a proper value.
     */
    public GameResult play() throws IllegalStateException {
        if(gameType == null)
            throw new IllegalStateException("Game Type not set, can't play!");
        else if(!isValidWager())
            throw new IllegalStateException("Wager not set, can't play!");
        else if(dieList.size() < 4)
            throw new IllegalStateException("Not enough dice, can't play!");

        rollDies();
        int multiplier = gameTypeToInt();
        int maxSimilar = maxSimilar();

        if(maxSimilar >= multiplier) {
            balance += multiplier * wager;
            return GameResult.WIN;
        }
        else {
            balance -= multiplier * wager;
            return GameResult.LOSS;
        }
    }

    public int gameTypeToInt() {
        if(gameType == null)
            return 0;
        else if(gameType == GameType.TWO_ALIKE)
            return 2;
        else if(gameType == GameType.THREE_ALIKE)
            return 3;
        else
            return 4;
    }

    private void rollDies() {
        for(Die d : dieList)
            d.roll();
    }

    private int maxSimilar() {
        int currSimilar, maxSimilar=1;
        List<Integer> dieVals = diceValues();

        for(int i=0; i<3; i++) {
            currSimilar = 1;
            for(int j=i+1; j<4; j++) {
                if (dieVals.get(i).equals(dieVals.get(j)))
                    currSimilar++;
            }
            if(currSimilar > maxSimilar)
                maxSimilar = currSimilar;
        }

        return maxSimilar;
    }
}
