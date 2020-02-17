package cs3500.pyramidsolitaire.model;

/**
 * A card value from 1 to 13.
 */
public enum Rank {
  one(1), two(2), three(3), four(4), Five(5), Six(6), Seven(7),
  Eight(8), Nine(9), Ten(10), eleven(11), twelve(12), thirteen(13);

  private final int rank;

  /**
   * Constructs suit.
   *
   * @param rank the card value
   */
  Rank(int rank) {
    this.rank = rank;
  }

  public int getRank() {
    return this.rank;
  }

  public boolean rankEqual(Rank r) {
    return this.rank == r.rank;
  }
}
