package cs3500.pyramidsolitaire.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents the state of PyramidSolitaire.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {
  protected Card[][] pyramid;
  protected List<Card> drawPile;
  protected List<Card> stock;
  protected Boolean start;
  protected int rows;
  protected int draw;

  /**
   * Constructs an BasicPyramidSolitaire.
   *
   * @param pyramid  the pyramid of card that are needed to be removed
   * @param drawPile the draw pile which you an draw a card or discard a card
   */
  public BasicPyramidSolitaire(Card[][] pyramid, List<Card> drawPile) {
    this.pyramid = pyramid;
    this.drawPile = drawPile;
    this.start = false;
  }

  /**
   * Constructs an BasicPyramidSolitaire.
   */
  public BasicPyramidSolitaire() {
    this.drawPile = new ArrayList<>();
    this.stock = new ArrayList<>();
    this.start = false;
    this.rows = -1;
    this.draw = -1;
  }

  protected int totalCard(int rows) {
    return (1 + rows) * rows / 2;
  }


  protected boolean exposed(int row, int card) {
    boolean exposed = true;
    if (getCardAt(row, card) == null) {
      exposed = false;
    }
    if (row < rows - 1) {
      if (getCardAt(row + 1, card) != null
              || getCardAt(row + 1, card + 1) != null) {
        exposed = false;
      }
    }
    return exposed;
  }

  protected boolean endOfRemove() {
    boolean output = true;
    List<Card> cur = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j <= i; j++) {
        if (exposed(i, j)) {
          cur.add(pyramid[i][j]);
        }
      }
    }
    if (drawPile.size() != 0) {
      output = false;
    }
    for (int i = 0; i < cur.size(); i++) {
      int num = cur.get(i).getValue();
      if (num == 13) {
        output = false;
      } else {
        for (int j = i + 1; j < cur.size(); j++) {
          if (num + cur.get(j).getValue() == 13) {
            output = false;
          }
        }
      }
    }
    return output;
  }

  protected void validList(List<Card> l) throws IllegalArgumentException {
    if (l == null || (l.size() != 52)) {
      throw new IllegalArgumentException("Invalid List");
    }
    List<Card> copy = getDeck();
    int count = 0;
    for (int i = 0; i < copy.size(); i++) {
      Card temp = copy.get(i);

      for (int j = 0; j < l.size(); j++) {
        Card real = l.get(j);
        if (temp.toString().equals(real.toString())) {
          j = l.size();
          count++;
        }
      }
    }
    if (count != 52) {
      throw new IllegalArgumentException("Duplicate");
    }
  }

  @Override
  public List<Card> getDeck() {
    ArrayList<Card> output = new ArrayList<Card>();
    for (Suits s : Suits.values()) {
      for (Rank r : Rank.values()) {
        output.add(new Card(s, r));
      }
    }
    return output;
  }

  protected Card getDraw(int index) {
    if (index > drawPile.size() || index < 0) {
      throw new IllegalArgumentException("Invalid DrawIndex");
    } else {
      return drawPile.get(index);
    }
  }

  protected void isStart() throws IllegalStateException {
    if (!start) {
      throw new IllegalStateException("Not Start");
    }
  }

  @Override
  public void startGame(List<Card> deck, boolean shouldShuffle, int numRows, int numDraw) {
    validList(deck);
    if (numRows > 9 || numRows < 2 || numDraw > 49 || numDraw < 1
            || totalCard(numRows) + numDraw > 52) {
      throw new IllegalArgumentException("Invalid Input");
    }
    if (shouldShuffle) {
      Collections.shuffle(deck);
    }
    pyramid = new Card[numRows][numRows];
    int c = 0;
    for (int i = 0; i < numRows; i++) {
      pyramid[i] = new Card[numRows];
      for (int j = 0; j <= i; j++) {
        Card cur = deck.get(c);
        pyramid[i][j] = cur;
        c++;
      }
    }
    drawPile.clear();
    for (int i = 0; i < numDraw; i++) {
      Card cur = (Card) deck.get(c);
      drawPile.add(cur);
      c++;
    }
    start = true;
    rows = numRows;
    draw = numDraw;
    stock.addAll(deck.subList(c, 52));
  }

  @Override
  public void remove(int row1, int card1, int row2, int card2) throws IllegalStateException {
    isStart();
    Card c1 = getCardAt(row1, card1);
    Card c2 = getCardAt(row2, card2);
    if (!exposed(row1, card1) || !exposed(row2, card2) || c1 == null || c2 == null
            || c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Invalid Remove");
    } else {
      pyramid[row1][card1] = null;
      pyramid[row2][card2] = null;
    }
  }

  @Override
  public void remove(int row, int card) throws IllegalStateException {
    isStart();
    Card c = getCardAt(row, card);
    if (!exposed(row, card) || c.getValue() != 13 || c == null) {
      throw new IllegalArgumentException("Invalid Remove");
    } else {
      pyramid[row][card] = null;
    }
  }

  private void rem(int index) {
    List<Card> result = new ArrayList<>();
    if (stock.size() == 0) {
      drawPile.remove(index);
    } else {
      drawPile.set(index, stock.remove(0));
    }
  }

  @Override
  public void removeUsingDraw(int drawIndex, int row, int card) throws IllegalStateException {
    isStart();
    Card c1 = getDraw(drawIndex);
    Card c2 = getCardAt(row, card);
    if (c2 == null || !exposed(row, card) || c1.getValue() + c2.getValue() != 13) {
      throw new IllegalArgumentException("Invalid Remove");
    }
    pyramid[row][card] = null;
    rem(drawIndex);
  }

  @Override
  public void discardDraw(int drawIndex) throws IllegalStateException {
    isStart();
    Card c = getDraw(drawIndex);
    rem(drawIndex);
  }

  @Override
  public int getNumRows() {
    if (!start) {
      return -1;
    } else {
      return rows;
    }
  }

  @Override
  public int getNumDraw() {
    if (!start) {
      return -1;
    } else {
      return drawPile.size();
    }
  }

  @Override
  public int getRowWidth(int row) {
    isStart();
    if (row >= 0 && row < rows) {
      return row + 1;
    } else {
      throw new IllegalArgumentException("invalid input");
    }
  }

  @Override
  public boolean isGameOver() throws IllegalStateException {
    isStart();
    return endOfRemove() || getScore() == 0;
  }

  @Override
  public int getScore() throws IllegalStateException {
    isStart();
    int result = 0;
    for (Card[] loc : pyramid) {
      for (Card c : loc) {
        if (c != null) {
          result = result + c.getValue();
        }
      }
    }
    return result;
  }

  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    this.isStart();
    Card result = null;
    if (row >= rows || row < 0 || card < 0 || card >= getRowWidth(row)) {
      throw new IllegalArgumentException("Invalid Input");
    } else {
      if (pyramid[row][card] != null) {
        result = pyramid[row][card];
      }
    }
    return result;
  }

  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    isStart();
    return new ArrayList<>(drawPile);
  }
}

