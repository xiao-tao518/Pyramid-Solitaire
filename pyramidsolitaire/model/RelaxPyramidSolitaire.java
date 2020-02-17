package cs3500.pyramidsolitaire.model;

import java.io.InputStreamReader;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;

/**
 * Represents the Relax mode game.
 */
public class RelaxPyramidSolitaire extends BasicPyramidSolitaire {

  private boolean relaxRemove(int row1, int card1, int row2, int card2) {
    boolean relax = false;
    if (exposed(row1, card1) && !exposed(row2, card2)) {
      if (row1 == row2 + 1) {
        if (card1 == card2) {
          if (getCardAt(row1, card1 + 1) == null) {
            relax = true;
          }
        } else if (card1 == card2 + 1) {
          if (getCardAt(row1, card2) == null) {
            relax = true;
          }
        }
      }
    } else if (!exposed(row1, card1) && exposed(row2, card2)) {
      if (row2 == row1 + 1) {
        if (card2 == card1) {
          if (getCardAt(row2, card2 + 1) == null) {
            relax = true;
          }
        } else if (card2 == card1 + 1) {
          if (getCardAt(row2, card1) == null) {
            relax = true;
          }
        }
      }
    }
    return relax;
  }

  private boolean moreRelaxRemove() {
    boolean more = false;
    for (int i = 0; i < rows - 1; i++) {
      for (int j = 0; j <= i; j++) {
        Card c1 = getCardAt(i, j);
        Card left = getCardAt(i + 1, j);
        Card right = getCardAt(i + 1, j + 1);
        if (c1 != null) {
          if (left != null && exposed(i + 1, j) && right == null) {
            if (c1.getValue() + left.getValue() == 13) {
              more = true;
            }
          } else if (right != null && left == null && exposed(i + 1, j + 1)) {
            if (c1.getValue() + right.getValue() == 13) {
              more = true;
            }
          }
        }
      }
    }
    return more;
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    isStart();
    Card c1 = getCardAt(row1, card1);
    Card c2 = getCardAt(row2, card2);
    if (!exposed(row1, card1) || !exposed(row2, card2) || c1 == null || c2 == null
            || c1.getValue() + c2.getValue() != 13) {
      if (!relaxRemove(row1, card1, row2, card2)) {
        throw new IllegalArgumentException("Invalid Remove");
      } else {
        pyramid[row1][card1] = null;
        pyramid[row2][card2] = null;
      }
    } else {
      pyramid[row1][card1] = null;
      pyramid[row2][card2] = null;
    }
  }


  public boolean isGameOver() throws IllegalStateException {
    isStart();
    return getScore() == 0 || endOfRemove() && !moreRelaxRemove();
  }
}
