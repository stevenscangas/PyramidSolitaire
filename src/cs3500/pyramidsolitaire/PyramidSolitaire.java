package cs3500.pyramidsolitaire;

import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator;
import cs3500.pyramidsolitaire.model.hw04.PyramidSolitaireCreator.GameType;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.InputStreamReader;

/**
 * Main class for pyramidsolitaire game. Can run any 3 of the gamemodes with main function.
 */
public final class PyramidSolitaire {

  /**
   * This is the main method to run the game.
   */
  public static void main(String[] args) {

    // default values
    int rowNum = 7;
    int drawNum = 3;
    GameType gameMode = GameType.BASIC;

    int numArgs = args.length;

    if (numArgs != 1 && numArgs != 3) {
      throw new IllegalArgumentException("Please enter 1 or 3 args.");
    }

    // switch statement to set gamemode equal to user's first argument
    switch (args[0]) {
      case "basic":
        gameMode = GameType.BASIC;
        break;
      case "relaxed":
        gameMode = GameType.RELAXED;
        break;
      case "multipyramid":
        gameMode = GameType.MULTIPYRAMID;
        break;
      default:
        throw new IllegalArgumentException("Argument was not any one of the 3 gamemode types. "
            + "Types are cAsE sEnSitive.");
    }

    // change rows and drawnum if 3 arguments
    if (numArgs == 3) {
      rowNum = Integer.parseInt(args[1]);
      drawNum = Integer.parseInt(args[2]);
    }

    PyramidSolitaireCreator creator = new PyramidSolitaireCreator();

    PyramidSolitaireModel model = creator.create(gameMode);

    // create controller with input and outputs as system console
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController((new InputStreamReader(System.in)), System.out);

    controller.playGame(model, model.getDeck(), true, rowNum, drawNum);

  }


}




