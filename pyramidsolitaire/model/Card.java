package cs3500.pyramidsolitaire.model;

/**
 * Represents the unit card of game.
 */
public class Card {
  private final Suits suit;
  private final Rank num;

  /**
   * Constructs a Card.
   *
   * @param suit the suit of a card
   * @param num  the value of a card
   */
  public Card(Suits suit, Rank num) {
    this.suit = suit;
    this.num = num;
  }

  public boolean cardsEqual(Card other) {
    return this.num.rankEqual(other.num) && this.suit.suitsEquals(other.suit);
  }

  public int getValue() {
    return this.num.getRank();
  }

  public String getSuit() {
    return this.suit.getSuit();
  }


  @Override
  public String toString() {
    if (this == null) {
      return "  ";
    } else if (num.getRank() == 1) {
      return "A" + suit.getSuit();
    } else if (num.getRank() == 10) {
      return "10" + suit.getSuit();
    } else if (num.getRank() == 11) {
      return "J" + suit.getSuit();
    } else if (num.getRank() == 12) {
      return "Q" + suit.getSuit();
    } else if (num.getRank() == 13) {
      return "K" + suit.getSuit();
    } else {
      return Integer.toString(num.getRank()) + suit.getSuit();
    }
  }
}
