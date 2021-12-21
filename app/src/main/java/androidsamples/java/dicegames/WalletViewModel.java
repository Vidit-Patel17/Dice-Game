package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {
  private int balance;
  private int increment;
  private int winValue;
  private Die die;

  /**
   * The no argument constructor.
   */
  public WalletViewModel() {
    this.balance = 0;
    this.increment = 5;
    this.winValue = 6;
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
   * @param amount the new balance
   */
  public void setBalance(int amount) {
    this.balance = amount;
  }

  /**
   * Rolls the {@link Die} in the wallet.
   */
  public void rollDie() {
    try {
      die.roll();
      if (die.value() == winValue)
        balance += increment;
    }
    catch (Exception e) {
      throw new IllegalStateException();
    }
  }

  /**
   * Reports the current value of the {@link Die}.
   *
   * @return current value of the die
   */
  public int dieValue() {
    try {
      return die.value();
    }
    catch (Exception e) {
      throw new IllegalStateException();
    }
  }

  /**
   * Sets the increment value for earning in the wallet.
   *
   * @param increment amount to add to the balance
   */
  public void setIncrement(int increment) {
    this.increment = increment;
  }

  /**
   * Sets the value which when rolled earns the increment.
   *
   * @param winValue value to be set
   */
  public void setWinValue(int winValue) {
    this.winValue = winValue;
  }

  /**
   * Sets the {@link Die} to be used in this wallet.
   *
   * @param d the Die to use
   */
  public void setDie(Die d) {
    this.die = d;
  }
}
