package cs3500.pyramidsolitaire.view;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.IOException;


/**
 * A class that represents the textual view of the game model.
 */
public class PyramidSolitaireTextualView implements PyramidSolitaireView {

  private final PyramidSolitaireModel<?> model;
  private Appendable out;

  /**
   * A constructor that takes in a game model and creates a textual view with it.
   *
   * @param model Represents the inner-workings of the solitaire game. Returns a
   *              PyramidSolitaireTextualView corresponding to the game model.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model) {
    this.model = model;
  }

  /**
   * A constructor that takes in a both a model and an Appendable and creates a textual view.
   *
   * @param model Represents the inner-workings of the solitaire game. Returns a
   *              PyramidSolitaireTextualView corresponding to the game model.
   * @param out Appendable that represents the visual representation of the board.
   */
  public PyramidSolitaireTextualView(PyramidSolitaireModel<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  /**
   * This method creates a string that represents the textual view of the game.
   *
   * @return the visual of the the pyramid game-board, shown as multiple lines of text
   */
  @Override
  public String toString() {

    // if getNumDraw is less than zero, then we know the game has not started yet since no card
    // have been added to the draw pile yet
    if (model.getNumDraw() < 0) {
      return "";
    }

    // check if game is won by checking if score is 0, if so, print "You Win"
    if (model.getScore() == 0) {
      return "You win!";
    }

    //check if game is over and therefore lost and if so print score and "Game Over"
    if (model.isGameOver()) {
      return "Game over. Score: " + this.model.getScore();
    } else {

      // if none of the above conditions are true,
      // print the pyramid to the screen so that it looks like the actual solitaire game
      StringBuilder gameBoardAsText = new StringBuilder();
      for (int i = 0; i < this.model.getNumRows(); i++) {

        // number of spaces per row equal to (getNumRows() * 2) - 2*(current row) - 2
        String rowPadding = "";

        // the math for how many spaces need to be added per row

        rowPadding += " ".repeat((this.model.getNumRows() * 2) - (2 * (i)) - 2);

        gameBoardAsText.append(rowPadding);

        for (int j = 0; j < i + 1; j++) {
          String cardString;
          if (model.getCardAt(i, j) == null) {
            cardString = ".";
          } else {
            cardString = this.model.getCardAt(i, j).toString();
          }
          // add spaces to pad this string to 3 characters
          // if not last string in row
          for (int x = cardString.length(); x < 3; x++) {
            if (model.getRowWidth(i) != j + 1) {
              cardString += " ";
            }
          }

          if (!(model.getRowWidth(i) == j || 0 == j)) {
            gameBoardAsText.append(" ");
          }
          gameBoardAsText.append(cardString);

        }
        // move to new line since current row is done
        gameBoardAsText.append("\n");
      }

      // check if every card in draw list is null
      boolean allDrawNull = true;
      // check if all draw cards are null
      for (int a = 0; a < model.getNumDraw(); a++) {
        if (model.getDrawCards().get(a) != null) {
          allDrawNull = false;
        }
      }

      // now add the draw pile for the final row:
      if (this.model.getNumDraw() == 0 || allDrawNull) {
        gameBoardAsText.append("Draw:");
      } else {
        gameBoardAsText.append("Draw: ");
      }

      String drawString;

      // check if every element in draw pile is null

      boolean drawOnlyNull = true;
      for (int d = 0; d < model.getDrawCards().size(); d++) {
        if (model.getDrawCards().get(d) != null) {
          drawOnlyNull = false;
        }
      }
      // if this is true, then don't put anything after Draw:
      if (drawOnlyNull) {
        return gameBoardAsText.toString();
      }

      for (int drawNum = 0; drawNum < this.model.getDrawCards().size(); drawNum++) {

        if (model.getDrawCards().get(drawNum) == null) {
          drawString = ".  ";
        } else {
          drawString = model.getDrawCards().get(drawNum).toString();
        }

        // add comma after if there is a card to the right
        if (drawNum < this.model.getDrawCards().size() - 1) {
          drawString += ", ";
        }
        gameBoardAsText.append(drawString);
      }

      return gameBoardAsText.toString();
    }

  }

  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  @Override
  public void render() throws IOException {
    out.append(this.toString());
  }
}