package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

public class PyramidSolitaireCreator {


  public PyramidSolitaireCreator() {
  }


  public enum GameType {
    BASIC,RELAXED,MULTIPYRAMID;
  }


  public static PyramidSolitaireModel<Card> create(GameType type){

    switch(type){
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
