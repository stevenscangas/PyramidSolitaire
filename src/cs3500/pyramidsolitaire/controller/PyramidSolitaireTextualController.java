package cs3500.pyramidsolitaire.controller;

import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;

import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import cs3500.pyramidsolitaire.view.PyramidSolitaireView;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Provides a controller for interacting with a game of Pyramid Solitaire.
 */
public class PyramidSolitaireTextualController implements PyramidSolitaireController {

  private final Readable rd;
  private final Appendable ap;



  /**
   * Primary constructor for a PyramidSolitaireController, takes in abstract input & output.
   *
   * @param rd An abstract appendable output.
   * @param ap An abstract readable input.
   * @throws IllegalArgumentException if rd or ap are null
   */
  public PyramidSolitaireTextualController(Readable rd, Appendable ap)
      throws IllegalArgumentException {

    this.rd = rd;
    this.ap = ap;

    // throw exception if either input rd or ap are null
    if (this.rd == null || this.ap == null) {
      throw new IllegalArgumentException("Input rd or ap is null");
    }

  }

  /**
   * The primary method for beginning and playing a game.
   *
   * @param <K>     the type of cards used by the model.
   * @param model   The game of solitaire to be played
   * @param deck    The deck of cards to be used
   * @param shuffle Whether to shuffle the deck or not
   * @param numRows How many rows should be in the pyramid
   * @param numDraw How many draw cards should be visible
   * @throws IllegalArgumentException if the model or deck is null
   * @throws IllegalStateException    if the game cannot be started, or if the controller cannot
   *                                  interact with the player.
   */
  @Override
  public <K> void playGame(PyramidSolitaireModel<K> model, List<K> deck,
      boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException, IllegalStateException {

    // throw exception if model is null
    if (Objects.isNull(model)) {
      throw new IllegalArgumentException("Input model is NULL.");
    }

    // throw exception if deck is null
    if(Objects.isNull(deck)){
      throw new IllegalArgumentException("Deck is null/empty");
    }


    // throw exception here if controller is unable to successfully read input
    // this.rd.read();

    // OR if the game cannot be started
    // starts the model game and inputs the given fields to playgame
    try{
      model.startGame(deck, shuffle, numRows, numDraw);
    } catch (IllegalArgumentException e) {
      throw new IllegalStateException("Game cannot be started with these fields.");
    }


    // create textual view of the model
    PyramidSolitaireView view = new PyramidSolitaireTextualView(model,ap);


    // boolean to represent if the user has quit the game or not
    boolean hasQuit = false;

    // create scanner object to read inputs
    Scanner scanner = new Scanner(rd);

    // while loop that runs until break is called to exit when the game has ended
    while (!model.isGameOver() && model.getScore() != 0) {


      // throw exception here if controller is unable to successfully transmit output
      try{
        this.ap.append("");
      } catch (IOException e) {
        e.printStackTrace();
        throw new IllegalStateException("Cannot transmit output");
      }

      // throw exception if no input left to read from scanner
      if(!scanner.hasNext()){
        throw new IllegalStateException("Cannot receive any input from scanner");
      }



      // render the game state

        try {
          view.render();
          ap.append('\n');
          ap.append("Score: " + model.getScore());
          ap.append('\n');
        } catch (IOException e) {
          e.printStackTrace();
        }

      // start input string as empty string
      String inputString = "";

      // string to output to the screen
      String transmitString = "";

      // if scanner can get a next value then read it
      if (scanner.hasNext()) {
        inputString = scanner.next();
      }
      // but if not then the program needs to end
      else {
        throw new IllegalStateException("Readable object unable to provide more inputs");
      }

      // if input is upper or lowercase Q then end program (exit while loop)
      if (inputString.equalsIgnoreCase("q")) {
        hasQuit = true;
        break;
      }

      // switch to take in the first command that then prompts individual fields
      switch (inputString) {

        // Remove 1 card from pyramid (King)
        // INPUTS: 2
        case "rm1":

          // run helper function that looks for rm1 inputs
          if(remove1Command(scanner,model)){
            hasQuit = true;
          }

          break;
        case "rm2":

          // run helper function that looks for rm2 inputs
          if(remove2Command(scanner,model)){
            hasQuit = true;
          }

          break;

        // Remove a card from pyramid with one from draw (add to 13)
        // INPUTS: 3
        case "rmwd":

          // run helper function that looks for rm2 inputs
          if(removeDrawCommand(scanner,model)){
            hasQuit = true;
          }

          break;

        // Discard 1 card from the draw pile
        // INPUTS: 1
        case "dd":


          // run helper function that looks for rm2 inputs
          if(discardDrawCommand(scanner,model)){
            hasQuit = true;
          }

          break;
      }

      // exit while loop before rendering if game has been quit
      if (hasQuit || model.isGameOver() || model.getScore() == 0) {
        break;
      }



    } // end of while loop

    // append quit messages to transmit string
    if(hasQuit){
      try {
        ap.append("Game quit!");
        ap.append('\n');
        ap.append("State of game when quit:");
        ap.append('\n');
        view.render();
        ap.append('\n');
        ap.append("Score: " + model.getScore());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // if not quit then just show the you win or game over message
    else {
      try {
        view.render();
        ap.append('\n');
      } catch (IOException e) {
        e.printStackTrace();
      }
    }



  }


  // keeps scanning values until the current input is q or an integer
  // returns "quit" if upper/lower case q is next
  // returns integer as string if integer is next
  private String ignoreInvalidInputOrQuit(Scanner scanner) {

    boolean inputValid = false;
    // keeps running until valid input
    while (true) {

      if(!scanner.hasNext()){
          throw new IllegalStateException("Readable object unable to provide more inputs");
      }

      // upper or lowercase Q is valid
      if (scanner.hasNext("q") || scanner.hasNext("Q")) {
        return "quit";
      }

      // Integer is valid
      if (scanner.hasNextInt()) {
        return Integer.toString(scanner.nextInt());
      }

      // move on to next item since input is invalid
      else {
        try {
          ap.append("Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*");
          ap.append('\n');
        } catch (IOException e) {
          e.printStackTrace();
        }
        scanner.next();
      }
    }

  }

  private boolean remove1Command(Scanner scanner,PyramidSolitaireModel model) {

    int row;
    int card;

    String fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      row = Integer.parseInt(fieldInput);
    }

    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      card = Integer.parseInt(fieldInput);
    }

    // try to run the code on the model with the input row and card indexed to 1
    try {
      model.remove(row - 1, card - 1);
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        ap.append("Invalid move. Play again. *Pyramid card is "
            + "out of bounds or value is not 13*");
        ap.append('\n');
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      e.printStackTrace();
    }

    return false;
  }

  private boolean remove2Command(Scanner scanner,PyramidSolitaireModel model){

    // Remove 2 cards from pyramid (add to 13)
    // INPUTS: 4


    int row1;
    int row2;
    int card1;
    int card2;

    // skip bogus inputs

    String fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      row1 = Integer.parseInt(fieldInput);
    }

    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      card1 = Integer.parseInt(fieldInput);
    }


    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      row2 = Integer.parseInt(fieldInput);
    }

    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      card2 = Integer.parseInt(fieldInput);
    }

    // try to run the code on the model with the input row and card indexed to 1
    try {
      model.remove(row1 - 1, card1 - 1,row2 - 1, card2 - 1);

    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        ap.append("Invalid move. Play again. *One or more pyramid cards are out of bounds or "
            + "the two values do not add to 13*");
        ap.append('\n');
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      e.printStackTrace();
    }
    return false;
  }

  private boolean removeDrawCommand(Scanner scanner,PyramidSolitaireModel model) {

    int drawIndex3;
    int row3;
    int card3;

    String fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      drawIndex3 = Integer.parseInt(fieldInput);
    }


    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      row3 = Integer.parseInt(fieldInput);
    }

    fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      card3 = Integer.parseInt(fieldInput);
    }


    // try to run the code on the model with the input drawIndex/row/card indexed to 1
    try {
      model.removeUsingDraw(drawIndex3 - 1,row3 - 1, card3 - 1);

    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        ap.append("Invalid move. Play again. *Pyramid or draw card is out of bounds or"
            + "values of the two cards do not add to 13*");
        ap.append('\n');
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      e.printStackTrace();
    }
    return false;
  }

  private boolean discardDrawCommand(Scanner scanner,PyramidSolitaireModel model) {

    int drawIndex;

    String fieldInput = ignoreInvalidInputOrQuit(scanner);
    if(fieldInput.equals("quit")){
      return true;
    } else {
      // otherwise the row input is the next int
      drawIndex = Integer.parseInt(fieldInput);
    }


    // try to run the code on the model with the input drawIndex indexed to 1
    try {
      model.discardDraw(drawIndex - 1);
    } catch (IllegalArgumentException | IllegalStateException e) {
      try {
        ap.append("Invalid move. Play again. *Draw index is out of bounds*");
        ap.append('\n');
      } catch (IOException ioException) {
        ioException.printStackTrace();
      }
      e.printStackTrace();
    }



    return false;
  }

}



