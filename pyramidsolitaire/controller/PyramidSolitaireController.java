package cs3500.pyramidsolitaire.controller;

import java.util.List;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;


/**
 * Controller Interface, control game by reading the input from user and returning output to the
 * console.
 */
public interface PyramidSolitaireController {
  /**
   * <p>initialize the game and play the game with the given parameters.</p>
   *
   * @param model   the game type to be played
   * @param deck    the deck to be dealt
   * @param shuffle if {@code false}, use the order as given by {@code deck}, otherwise shuffle the
   *                cards
   * @param numRows number of rows in the pyramid
   * @param numDraw number of open piles
   * @throws IllegalArgumentException if the provided model is null
   * @throws IllegalArgumentException if the controller is unable to successfully receive input or
   *                                  transmit output, or if the game cannot be started
   */
  <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                    boolean shuffle, int numRows, int numDraw);
}
