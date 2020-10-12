import cs3500.pyramidsolitaire.controller.PyramidSolitaireController;
import cs3500.pyramidsolitaire.controller.PyramidSolitaireTextualController;
import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.FakeModel;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class PyramidSolitaireTextualControllerTest {

  PyramidSolitaireModel model;
  PyramidSolitaireController controller;
  StringReader in;
  StringBuilder out;

  @Before
  public void setUp() throws Exception {
    //  model = new FakeModel(out);
  }

  ////////////////////////////////////////////////////////////////////////////

  // TESTS FOR CONSTRUCTOR EXCEPTIONS


  // IllegalArgumentException when constructor has null for RD
  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorRDNull() {
    out = new StringBuilder("");

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(null, out);
  }

  // IllegalArgumentException when constructor has null for AP
  @Test(expected = IllegalArgumentException.class)
  public void testControllerConstructorAPNull() {
    out = new StringBuilder("");

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(in, null);
  }

  // TESTS FOR PlayGame Exceptions

  // make sure playGame throws the IllegalStateException when given bad inputs
  // bad inputs will cause model to throw Argument exception which should be caught
  // by the controller and then the controller throws the IllegalStateException
  @Test(expected = IllegalStateException.class)
  public void testPlayGameBadRow() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), System.out);

    controller.playGame(model, model.getDeck(), true, -1, 5);
  }

  // make sure playGame throws the IllegalStateException when given bad inputs
  // bad inputs will cause model to throw Argument exception which should be caught
  // by the controller and then the controller throws the IllegalStateException
  @Test(expected = IllegalStateException.class)
  public void testPlayGameBadDraw() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), System.out);

    controller.playGame(model, model.getDeck(), true, 1, -1);
  }

  // model argument is null
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameModelNull() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), System.out);

    controller.playGame(null, model.getDeck(), true, 1, -1);
  }

  // deck is null
  @Test(expected = IllegalArgumentException.class)
  public void testPlayGameDeckNull() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), System.out);

    controller.playGame(model, null, true, 1, -1);
  }

  // test for playGame method called twice, should just reset the game
  @Test
  public void testPlayGameTwice() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("dd 1 q"), System.out);

    controller.playGame(model, model.getDeck(), false, 7, 3);

    controller = new PyramidSolitaireTextualController(
        new StringReader("rm2 7 5 7 1 q"), System.out);
    controller.playGame(model, model.getDeck(), false, 7, 3);

  }

  // TESTS WITH FAKE APPENDABLE TO MAKE SURE EXCEPTION THROWN WHEN CONTROLLER CANNOT APPEND

  // controller cannot append to appendable object (cannot transmit output)
  // causese IO exception which is caught in controller as illegalStateException
  @Test(expected = IllegalStateException.class)
  public void testPlayGameFakeAppendable() {
    Appendable corruptedAppendable = new FakeAppendable();
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), corruptedAppendable);

    controller.playGame(model, model.getDeck(), true, 7, 3);
  }

  // TESTS FOR BAD CONTROLLER CONSTRUCTOR EXCEPTIONS

  // appendable is null
  @Test(expected = IllegalArgumentException.class)
  public void testControllerApNull() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        new StringReader("rm1 7 2"), null);
  }

  // readable is null
  @Test(expected = IllegalArgumentException.class)
  public void testControllerRdNull() {
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        null, System.out);
  }

  // TESTS GENERAL CONTROLLER INPUTS

  // if stringreader has only empty string then nothing can be read from it, throw exception
  @Test(expected = IllegalStateException.class)
  public void testControllerRDArgumentEmptyString() {
    out = new StringBuilder("something");
    in = new StringReader("");
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        in, out);
    controller.playGame(model, model.getDeck(),
        false, 1, 3);
  }

  // if stringreader has only a null then it cannot be constructed, javalib will not allow
  @Test(expected = NullPointerException.class)
  public void testControllerRDArgumentNull() {
    out = new StringBuilder("something");
    in = new StringReader(null);
  }

  // if stringbuilder has only empty string then throw exception because there is no hasNext
  @Test(expected = IllegalStateException.class)
  public void testControllerAPArgumentEmptyString() {
    out = new StringBuilder("");
    in = new StringReader("something");
    PyramidSolitaireModel model = new BasicPyramidSolitaire();
    PyramidSolitaireController controller = new PyramidSolitaireTextualController(
        in, out);
    controller.playGame(model, model.getDeck(),
        false, 1, 3);
  }

  // if stringbuilder has only a null then it cannot be constructed, javalib will not allow
  @Test(expected = NullPointerException.class)
  public void testControllerAPArgumentNull() {
    out = new StringBuilder(null);
    in = new StringReader("something");
  }


  // see if game quits correctly when q by itself
  @Test
  public void testControllerLowerQByItself() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // same but capital Q
  @Test
  public void testControllerUpperQByItself() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("Q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if controller ignores qqqqqqq input since not single Q
  @Test
  public void testControllerLotsOfQ() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("qqqqqqq q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // controller should throw an exception because there is nothing for it to read
  @Test(expected = IllegalStateException.class)
  public void testEmptyReadable() {
    out = new StringBuilder("");

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader(""), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // RM1 TESTS WITH CONTROLLER INPUTS

  // see if controller receives correct input with rows and card -1 for index
  @Test
  public void testRm1ControllerInput() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 7 2 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 6 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller returns values of row and card -1 even if they are bad for the model
  @Test
  public void testRm1ControllerBadRowAndColumn() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 100 -2 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 99 -3\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller skips bad inputs and still runs rm1 after
  @Test
  public void testRm1ControllerNoInteger() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("rm1 xx five 1 5 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "rm1 0 4\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if game quits correctly when q in the middle of input
  @Test
  public void testRm1ControllerQInMiddle() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 1 q 2"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if game quits correctly when q in the middle of input
  @Test
  public void testRm1ControllerQInBetween2() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 1 2 Q rm1 2 4"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // controller should throw an exception because there is nothing left for it to read
  // from the readable and cant finish its command
  @Test(expected = IllegalStateException.class)
  public void testRm1ControllerIncomplete() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 1"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // TESTS FOR RM2 WITH CONTROLLER


  // see if controller returns values of row and card -1 even if they are bad for the model
  @Test
  public void testRm2ControllerBadRowAndColumn() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(
        new StringReader("rm2 100 -2 -1 -1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        // the reason that the A of clubs isnt on a new line is because
        // if this werent the mock model, an error message would be shown which would
        // creat the correct line spacing
        + "rm2 99 -3 -2 -2A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller skips bad inputs and still runs rm2 after
  @Test
  public void testRm2ControllerNoInteger() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("rm2 xx five 1 5 1 7 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "rm2 0 4 0 6A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if game quits correctly when q in the middle of input
  @Test
  public void testRm2ControllerQInMiddle() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 2 1 2 q 2"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if game quits correctly when q in the middle of input
  @Test
  public void testRm2ControllerQInBetween2() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader(
        "rm2 1 2 3 3 Q rm2 2 4 3 3"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        // A not being on new line is because of mock model
        + "rm2 0 1 2 2A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // controller should throw an exception because there is nothing left for it to read
  // from the readable and cant finish its command
  @Test(expected = IllegalStateException.class)
  public void testRm2ControllerIncomplete() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 1 1 3"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller receives correct input with rows and card -1 for index
  @Test
  public void testRm2ControllerInput() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm2 7 2 7 5 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm2 6 1 6 4A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // TESTS RMWD WITH CONTROLLER

  // see if controller receives correct input with rows and card -1 for index
  @Test
  public void testRmwdControllerInput() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 7 2 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rmwd 0 6 1A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if controller returns values of row and card -1 even if they are bad for the model
  @Test
  public void testRmwdControllerBadRowAndColumn() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rmwd 1 100 -2 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rmwd 0 99 -3A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller skips bad inputs and still runs rm1 after
  @Test
  public void testRmwdControllerNoInteger() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("rmwd xx five 1 1 5 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "rmwd 0 0 4A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if game quits correctly when q in the middle of input
  @Test
  public void testRmwdControllerQInMiddle() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(
        new StringReader("rmwd 1 1 q 2"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if game quits correctly when q in the middle of input
  @Test
  public void testRmwdControllerQInBetween2() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(
        new StringReader("rmwd 1 1 2 Q rmwd 12 4"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rmwd 0 0 1A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // controller should throw an exception because there is nothing left for it to read
  // from the readable and cant finish its command
  @Test(expected = IllegalStateException.class)
  public void testRmwdControllerIncomplete() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("rm1 1 1"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // TESTS FOR DD WITH CONTROLLER

  // see if controller receives correct input
  @Test
  public void testDDControllerInput() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("dd 1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "dd 0A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if controller returns values of row and card -1 even if they are bad for the model
  @Test
  public void testDDControllerBadRowAndColumn() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("dd -1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "dd -2A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if controller skips bad inputs and still runs rm1 after
  @Test
  public void testDDControllerNoInteger() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("dd xx five 1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "dd 0A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // see if game quits correctly when q in the middle of input
  @Test
  public void testDDControllerQInMiddle() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("dd q 2"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // see if game quits correctly when q in the middle of input
  @Test
  public void testDDControllerQInBetween2() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("dd 1 Q d 4"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "dd 0A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // controller should throw an exception because there is nothing left for it to read
  // from the readable and cant finish its command
  @Test(expected = IllegalStateException.class)
  public void testDDControllerIncomplete() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader("dd "), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "rm1 0 1\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // test controller input to model with combination of other commands as parameters and gibberish
  @Test
  public void testCombination1() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("dd rm1 rm2 rmwd dd dknf sdjksdjks wq wq 1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "dd 0"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }

  // test controller input to model with combination of spaces and new lines
  @Test
  public void testDDNewLinesAndSpace() {
    out = new StringBuilder();

    model = new FakeModel(out);
    controller = new PyramidSolitaireTextualController(new StringReader
        ("dd \n \n  n \n dd \n \n dd     1 q"), out);
    controller.playGame(model, model.getDeck(),
        false, 1, 0);
    assertEquals("A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "Invalid move. Play again. *Input invalid: please enter q, Q, or an integer*\n"
        + "dd 0"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1\n"
        + "Game quit!\n"
        + "State of game when quit:\n"
        + "A♣\n"
        + "Draw:\n"
        + "Score: 1", out.toString());
  }


  // no illegal state is thrown since game doesnt need to be quit to end reading if game won
  @Test
  public void testNoQNeededSinceGameWon() {
    out = new StringBuilder();

    model = new BasicPyramidSolitaire(1);

    controller = new PyramidSolitaireTextualController(new StringReader
        ("rmwd 6 1 1"), out);

    controller.playGame(model, model.getDeck(),
        true, 1, 20);

    assertEquals("A♦\n"
        + "Draw: 5♠, 9♦, 4♣, 6♣, 7♣, Q♥, 6♥, A♥, 10♣, A♣, K♦,"
        + " 10♠, 8♠, 2♣, 6♠, 5♣, 4♠, 7♠, 5♦, 5♥\n"
        + "Score: 1\n"
        + "You win!\n", out.toString());
  }

  // no illegal state is thrown since game doesnt need to be quit to end reading if game lost
  @Test
  public void testNoQNeededSinceGameLost() {
    out = new StringBuilder();

    model = new BasicPyramidSolitaire(1);

    controller = new PyramidSolitaireTextualController(new StringReader
        ("rm1 1 1"), out);

    controller.playGame(model, model.getDeck(),
        true, 1, 0);

    assertEquals("Game over. Score: 1\n", out.toString());
  }


}
