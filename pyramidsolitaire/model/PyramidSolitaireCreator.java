package cs3500.pyramidsolitaire.model;


/**
 * Creat a type of game.
 */
public class PyramidSolitaireCreator {

  /**
   * represents a type of game.
   */
  public enum GameType {
    BASIC("basic"), RELAXED("relaxed"), TRIPEAKS("tripeak");

    private String type;

    GameType(String type) {
      this.type = type;
    }
  }

  /**
   * return one of three game type.
   */
  public static PyramidSolitaireModel create(GameType type) {
    if (type == GameType.BASIC) {
      return new BasicPyramidSolitaire();
    } else if (type == GameType.RELAXED) {
      return new RelaxPyramidSolitaire();
    }
    return new TriPeaksPyramidSoliraire();
  }
}
