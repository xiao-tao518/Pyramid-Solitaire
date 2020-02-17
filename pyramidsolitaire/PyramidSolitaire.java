package cs3500.pyramidsolitaire;

import java.io.InputStreamReader;
import java.util.Scanner;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.RelaxPyramidSolitaire;
import cs3500.pyramidsolitaire.model.TriPeaksPyramidSoliraire;

/**
 * Entry of the game, start a game to play.
 */
public class PyramidSolitaire {

  /**
   * Entry of the game.
   *
   * @param args the input command of the game
   */
  public static void main(String[] args) {
    PyramidSolitaireModel p;
    if (args[0].equals("basic")) {
      p = new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.BASIC);
    }
    else if (args[0].equals("relaxed")) {
      p = new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.RELAXED);
    }
    else if (args[0].equals("tripeak")) {
      p = new PyramidSolitaireCreator().create(PyramidSolitaireCreator.GameType.TRIPEAKS);
    }
    else {
      throw new IllegalArgumentException("invalid mode");
    }
    new PyramidSolitaireTextualController(new InputStreamReader(System.in),
            System.out).playGame(p, p.getDeck(), false, Integer.parseInt(args[1]),
            Integer.parseInt(args[2]));
    }
}

