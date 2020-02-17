package cs3500.pyramidsolitaire.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.pyramidsolitaire.model.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

/**
 * Controller of the game, read the input from user and return output to the console.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {
  private Readable input;
  private Appendable output;
  private boolean start;

  /**
   * construct a controller with a Readable and Appendable.
   *
   * @param input  Readable
   * @param output Appendable
   * @throws IllegalArgumentException when gives null input or output
   */
  public PyramidSolitaireTextualController(Readable input, Appendable output)
          throws IllegalArgumentException {
    if (input == null || output == null) {
      throw new IllegalArgumentException("Unexpect null");
    } else {
      this.input = input;
      this.output = output;
      this.start = false;
    }
  }

  private <K> void quit(PyramidSolitaireModel<K> model, PyramidSolitaireTextualView view) {
    try {
      this.output.append("Game quit!\nState of game when quit:\n");
      view.render();
      this.output.append(String.format("\n" + "Score: %d\n", model.getScore()));
    } catch (IOException e) {
      throw new IllegalArgumentException("IOException");
    }
  }

  private <K> void play(PyramidSolitaireModel<K> model, Object[] typing) {
    try {
      try {
        Object command = typing[0];
        if (command.equals("rm1")) {
          model.remove((Integer) typing[1] - 1, (Integer) typing[2] - 1);
        } else if (command.equals("rm2")) {
          model.remove((Integer) typing[1] - 1, (Integer) typing[2] - 1,
                  (Integer) typing[3] - 1, (Integer) typing[4] - 1);
        } else if (command.equals("rmwd")) {
          model.removeUsingDraw((Integer) typing[1] - 1, (Integer) typing[2] - 1,
                  (Integer) typing[3] - 1);
        } else if (command.equals("dd")) {
          model.discardDraw((Integer) typing[1] - 1);
        } else {
          this.output.append("Invalid command try again.\n");
        }
      } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
        this.output.append("Invalid move. Play again. ").append(e.getMessage()).append("\n");
      }
    } catch (IOException e) {
      throw new IllegalStateException("IOException");
    }
  }

  private Object[] regularCommand(Scanner s) {
    Object[] output = new Object[5];
    int count = 0;
    try {
      while (s.hasNext() && count < output.length - 1) {
        if (output[0] == null) {
          String command = s.next();
          switch (command) {
            case "rm1":
              output = new Object[3];
              output[0] = command;
              break;
            case "rm2":
              output[0] = command;
              break;
            case "rmwd":
              output = new Object[4];
              output[0] = command;
              break;
            case "dd":
              output = new Object[2];
              output[0] = command;
              break;
            case "q":
            case "Q":
              output[0] = command;
              return output;
            default:
              this.output.append("invalid command\n");
          }
        } else {
          if (s.hasNextInt()) {
            count++;
            output[count] = s.nextInt();
            if (count == output.length - 1) {
              break;
            }
          } else {
            String next = s.next();
            switch (next) {
              case "q":
              case "Q":
              output = new Object[1];
              output[0] = "q";
              return output;
              default:
              this.output.append("invalid command\n");
            }
          }
        }
      }
      return output;
    } catch (IOException e) {
      throw new IllegalStateException("IOException");
    }
  }

  /**
   * Override the playGame in the abstract class, start game if the game has not started, then
   * play the game by model and render the game.
   *
   * @param model   game state
   * @param deck    a deck of cards to be used
   * @param shuffle Whether need to shuffle the cards
   * @param numRows number of rows of pyramid
   * @param numDraw number of visible cards
   * @param <K>     type of thing that game uses
   */
  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
                           boolean shuffle, int numRows, int numDraw) {
    PyramidSolitaireTextualView view = new PyramidSolitaireTextualView(model, output);
    boolean output = false;
    if (model == null) {
      throw new IllegalArgumentException("Unexpect null model");
    }
    try {
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalStateException | IllegalArgumentException e) {
      throw new IllegalStateException("failed to start");
    }
    if (!start) {
      try {
        view.render();
        this.output.append("\n" + String.format("Score: %d\n", model.getScore()));
      } catch (IOException e) {
        throw new IllegalArgumentException("IOException");
      }
      this.start = true;
    }
    try {
      Scanner scan = new Scanner(this.input);
      while (scan.hasNext()) {
        Object[] nextCommand = this.regularCommand(scan);
        for (Object s : nextCommand) {
          if (s.equals("q") || s.equals("Q")) {
            this.quit(model, view);
            output = true;
            break;
          }
        }
        if (output) {
          break;
        }
        this.play(model, nextCommand);
        if (!model.isGameOver()) {
          view.render();
          this.output.append(String.format("\n" + "Score: %d\n", model.getScore()));
        } else {
          view.render();
          break;
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("IOException");
    } catch (IllegalArgumentException | IllegalStateException | IndexOutOfBoundsException e) {
      throw new IllegalArgumentException("invalid input");
    }
  }
}