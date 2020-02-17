package cs3500.pyramidsolitaire.view;

import java.io.IOException;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;


/**
 * display the game state from PyramidSolitaireModel.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {
  private PyramidSolitaireModel<?> model;
  private Appendable ap;

  /**
   * Constructs a PyramidSolitaireTextualView with a model.
   *
   * @param model the state of the game
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  /**
   * Constructs a PyramidSolitaireTextualView.
   *
   * @param model the state of the game
   * @param ap    the output of the game
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable ap) {
    this.model = model;
    this.ap = ap;
  }

  @Override
  public String toString() {
    if (model.getNumRows() == -1) {
      return "";
    } else if (model.getScore() == 0) {
      return "You win!";
    } else if (model.isGameOver()) {
      return "Game over. Score: " + model.getScore();
    } else {
      String output = "";
      for (int i = 0; i < model.getNumRows(); i++) {
        for (int j = 0; j < model.getRowWidth(i); j++) {
          if (j == 0) {
            for (int k = 0; k < 2 * (model.getNumRows() - 1 - i); k++) {
              output = output + " ";
            }
            if (model.getCardAt(i, j) == null) {
              output = output + "   ";
            } else if (i > 0) {
              if (model.getCardAt(i, j).toString().length() < 3) {
                output = output + model.getCardAt(i, j).toString() + " ";
              } else {
                output = output + model.getCardAt(i, j).toString();
              }
            } else {
              output = output + model.getCardAt(i, j).toString();
            }
          } else if (i == 0 && j == 1) {
            if (model.getCardAt(i, j) == null) {
              output = output + "     ";
            } else {
              output = output + " " + model.getCardAt(i, j).toString();
            }
          } else if (j == model.getRowWidth(i) - 1) {
            if (model.getCardAt(i, j) == null) {
              output = output + "   ";
            } else {
              output = output + " " + model.getCardAt(i, j).toString();
            }
          } else {
            if (model.getCardAt(i, j) == null) {
              output = output + "    ";
            } else if (model.getCardAt(i, j).toString().length() < 3) {
              output = output + " " + model.getCardAt(i, j).toString() + " ";
            } else {
              output = output + " " + model.getCardAt(i, j).toString();
            }
          }
        }
        output = output + '\n';
      }
      output = output + "Draw:";
      for (int i = 0; i < model.getDrawCards().size(); i++) {
        if (i == 0) {
          output = output + " " + model.getDrawCards().get(i).toString();
        } else {
          output = output + ", " + model.getDrawCards().get(i).toString();
        }
      }
      return output;
    }
  }

  @Override
  public void render() throws IOException {
    ap.append(this.toString());
  }
}
