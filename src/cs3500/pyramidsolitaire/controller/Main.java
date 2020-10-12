package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.io.InputStreamReader;

public class Main {
  public static void main(String [] args){


    PyramidSolitaireModel model = new BasicPyramidSolitaire(8);

    // create controller with input and outputs as system console
    PyramidSolitaireController controller =
        new PyramidSolitaireTextualController(( new InputStreamReader(System.in)), System.out);

    controller.playGame(model,model.getDeck(),true, 7,3);


  }
}
