package cs3500.pyramidsolitaire.model;

/**
 * Represents the suit of a card.
 */
public enum Suits {
  club("♣"), heart("♥"), spade("♠"), diamond("♦");

  private final String suits;

  Suits(String suits) {
    this.suits = suits;
  }

  public String getSuit() {
    return this.suits;
  }

  public boolean suitsEquals(Suits suit) {
    return this.equals(suit);
  }
}
