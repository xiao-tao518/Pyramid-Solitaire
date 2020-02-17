package cs3500.pyramidsolitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents the TriPeaks mode of game.
 */
public class TriPeaksPyramidSoliraire extends BasicPyramidSolitaire {

  /**
   * return the row length.
   *
   * @param row the given row
   */
  private int rowNumCard(int row) {
    int n = this.rows / 2;
    int width = 0;
    for (int i = 0; i <= row; i++) {
      if (i == 0) {
        width = width + 3 + 2 * n - 2;
      } else {
        width += 1;
      }
    }
    return width;
  }

  /**
   * return the number of none null card of the given row.
   *
   * @param row the given row
   */
  private int rowNonNull(int row) {
    int n = this.rows / 2;
    int count = 0;
    for (int i = 0; i < row; i++) {
      if (i < n) {
        count = count + 3;
      } else {
        count += 1;
      }
    }
    return count;
  }

  @Override
  public int getRowWidth(int row) {
    isStart();
    if (row >= 0 && row < rows) {
      int n = this.rows / 2;
      int width = 0;
      for (int i = 0; i <= row; i++) {
        if (i == 0) {
          width = width + 3 + 2 * n - 2;
        } else {
          width += 1;
        }
      }
      return width;
    } else {
      throw new IllegalArgumentException("invalid input");
    }
  }

  @Override
  public int totalCard(int row) {
    int count = 0;
    for (int i = 0; i < row; i++) {
      count = count + rowNonNull(i);
    }
    return count;
  }

  @Override
  public List<Card> getDeck() {
    ArrayList<Card> output = new ArrayList<>();
    for (Suits s : Suits.values()) {
      for (Rank r : Rank.values()) {
        output.add(new Card(s, r));
      }
    }
    for (Suits s : Suits.values()) {
      for (Rank r : Rank.values()) {
        output.add(new Card(s, r));
      }
    }
    return output;
  }

  protected void validList(List<Card> l) throws IllegalArgumentException {
    if (l == null || (l.size() != 104)) {
      throw new IllegalArgumentException("Invalid List");
    }
    List<Card> copy = getDeck();
    int count = 0;
    for (int i = 0; i < l.size(); i++) {
      Card real = l.get(i);

      for (int j = 0; j < copy.size(); j++) {
        Card temp = copy.get(j);
        if (temp.toString().equals(real.toString())) {
          j = copy.size();
          copy.remove(temp);
          count++;
        }
      }
    }
    if (count != 104) {
      throw new IllegalArgumentException("Duplicate");
    }
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {
    validList(deck);
    if (numRows > 8 || numRows < 2 || numDraw > 97 || numDraw < 1
            || totalCard(numRows) + numDraw > 104) {
      throw new IllegalArgumentException("Invalid Input");
    }
    start = true;
    rows = numRows;
    draw = numDraw;
    if (shouldShuffle) {
      Collections.shuffle(deck);
    }
    int c = 0;
    int n = numRows / 2;
    pyramid = new Card[numRows][];
    for (int i = 0; i < n; i++) {
      pyramid[i] = new Card[rowNumCard(i)];
      int count = 0;
      for (int j = 0; j < rowNumCard(i); j++) {
        if (count == i + 1) {
          j = j + n - i - 1;
          Card cur = deck.get(c);
          pyramid[i][j] = cur;
          c++;
          count = 0;
          count++;
        } else {
          Card cur = deck.get(c);
          pyramid[i][j] = cur;
          c++;
          count++;
        }
      }
    }
    for (int i = n; i < numRows; i++) {
      pyramid[i] = new Card[rowNumCard(i)];
      for (int j = 0; j < rowNumCard(i); j++) {
        Card cur = deck.get(c);
        pyramid[i][j] = cur;
        c++;
      }
    }
    drawPile.clear();
    for (int i = 0; i < numDraw; i++) {
      Card cur = deck.get(c);
      drawPile.add(cur);
      c++;
    }
    stock.addAll(deck.subList(c, 104));
  }
}
