package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

/**
 * Class that is used with a factory method to create any 3 of the types of solitaire games.
 */
public class PyramidSolitaireCreator {

  /**
   * Default constructor, object only used for factory method.
   */
  public PyramidSolitaireCreator() {
    // This constructor does nothing since an object of this class is only used to call
    // the create method.
  }


  /**
   * Enum that represents the type of game. Basic, Relaxed, or Multipyramid.
   */
  public enum GameType {
    BASIC, RELAXED, MULTIPYRAMID;
  }

  /**
   * Factory method that can create a model of any 3 of the possible gamemodes.
   */
  public static PyramidSolitaireModel<Card> create(GameType type) {

    switch (type) {
      case BASIC:
        return new BasicPyramidSolitaire();
      case RELAXED:
        return new RelaxedPyramidSolitaire();
      case MULTIPYRAMID:
        return new MultiPyramidSolitaire();
      default:
        throw new IllegalArgumentException("GameType does not exist.");
    }
  }


}
