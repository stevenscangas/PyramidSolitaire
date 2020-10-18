import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import cs3500.pyramidsolitaire.model.hw04.RelaxedPyramidSolitaire;
import org.junit.Test;
import cs3500.pyramidsolitaire.view.PyramidSolitaireTextualView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is used for testing purposes.
 */
public class RelaxedPyramidSolitaireTest {

  static final int randValue = 8;

  PyramidSolitaireModel<Card> defaultSolitaire = new RelaxedPyramidSolitaire();
  PyramidSolitaireModel<Card> notRandomSolitaire = new RelaxedPyramidSolitaire(randValue);

  // create the deck that the getDeck() method should return

  List<Card> standardDeck = Arrays.asList(
      new Card(1, "clubs"),
      new Card(1, "diamonds"),
      new Card(1, "hearts"),
      new Card(1, "spades"),
      new Card(2, "clubs"),
      new Card(2, "diamonds"),
      new Card(2, "hearts"),
      new Card(2, "spades"),
      new Card(3, "clubs"),
      new Card(3, "diamonds"),
      new Card(3, "hearts"),
      new Card(3, "spades"),
      new Card(4, "clubs"),
      new Card(4, "diamonds"),
      new Card(4, "hearts"),
      new Card(4, "spades"),
      new Card(5, "clubs"),
      new Card(5, "diamonds"),
      new Card(5, "hearts"),
      new Card(5, "spades"),
      new Card(6, "clubs"),
      new Card(6, "diamonds"),
      new Card(6, "hearts"),
      new Card(6, "spades"),
      new Card(7, "clubs"),
      new Card(7, "diamonds"),
      new Card(7, "hearts"),
      new Card(7, "spades"),
      new Card(8, "clubs"),
      new Card(8, "diamonds"),
      new Card(8, "hearts"),
      new Card(8, "spades"),
      new Card(9, "clubs"),
      new Card(9, "diamonds"),
      new Card(9, "hearts"),
      new Card(9, "spades"),
      new Card(10, "clubs"),
      new Card(10, "diamonds"),
      new Card(10, "hearts"),
      new Card(10, "spades"),
      new Card(11, "clubs"),
      new Card(11, "diamonds"),
      new Card(11, "hearts"),
      new Card(11, "spades"),
      new Card(12, "clubs"),
      new Card(12, "diamonds"),
      new Card(12, "hearts"),
      new Card(12, "spades"),
      new Card(13, "clubs"),
      new Card(13, "diamonds"),
      new Card(13, "hearts"),
      new Card(13, "spades"));



  // test getDeck method to make sure it returns standard list of cards
  @Test
  public void testGetDeck() {
    assertArrayEquals(standardDeck.toArray(), defaultSolitaire.getDeck().toArray());
  }

  // TESTS FOR STARTGAME EXCEPTIONS

  @Test(expected = IllegalArgumentException.class)
  public void testNullDeck() {
    defaultSolitaire.startGame(
        null, false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRowsNotPositive() {
    defaultSolitaire.startGame(
        standardDeck, false, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeDrawPile() {
    defaultSolitaire.startGame(
        standardDeck, false, 7, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotEnoughCards() {
    defaultSolitaire.startGame(
        standardDeck, false, 10, 3);
  }


  Card extraCard = new Card(10, "clubs");

  // create extra card deck

  /**
   * Creates a deck with 53 cards to be used with testing.
   *
   * @return Deck with 53 cards.
   */
  public List<Card> getExtraCardDeck() {
    List<Card> deckList = new ArrayList<Card>();

    // iterates through numbers 1-13
    for (int i = 1; i <= 13; i++) {

      // adds card to deck with current value (i) and with each suit
      deckList.add(new Card(i, "clubs"));
      deckList.add(new Card(i, "diamonds"));
      deckList.add(new Card(i, "hearts"));
      deckList.add(new Card(i, "spades"));
    }
    deckList.add(extraCard);
    return deckList;
  }

  /**
   * Creates a deck with one null card used for testing.
   *
   * @return Deck with 52 cards where one is null
   */
  public List<Card> getDeckWithNull() {
    List<Card> deckList = new ArrayList<Card>();

    // iterates through numbers 1-13
    for (int i = 1; i <= 13; i++) {

      // adds card to deck with current value (i) and with each suit
      deckList.add(new Card(i, "clubs"));
      deckList.add(new Card(i, "diamonds"));
      deckList.add(new Card(i, "hearts"));
      deckList.add(new Card(i, "spades"));
    }
    deckList.add(null);
    return deckList;
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullInDeck() {
    defaultSolitaire.startGame(
        getDeckWithNull(), false, 10, 3);
  }


  /**
   * Creates a deck with two identical cards in it to be used for testing.
   *
   * @return Deck the contains 52 cards, but two are identical.
   */
  // create extra card deck
  public List<Card> getDuplicateDeck() {
    List<Card> deckList = new ArrayList<Card>();

    // iterates through numbers 1-13
    for (int i = 1; i <= 13; i++) {

      // adds card to deck with current value (i) and with each suit
      deckList.add(new Card(i, "clubs"));
      deckList.add(new Card(i, "diamonds"));
      deckList.add(new Card(i, "hearts"));
      deckList.add(new Card(i, "spades"));
    }
    deckList.add(extraCard);
    deckList.remove(0);
    return deckList;
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDeckTooBig() {
    defaultSolitaire.startGame(
        getExtraCardDeck(), false, 7, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDuplicateDeck() {
    defaultSolitaire.startGame(
        getDuplicateDeck(), false, 7, 3);
  }

  // TESTS FOR REMOVE METHOD

  // Exceptions

  // throw exception if game hasn't started yet
  @Test(expected = IllegalStateException.class)
  public void testRemoveState() {
    defaultSolitaire.remove(6, 1, 6, 4);
  }

  // throw exception if trying to remove from row that is greater tha total rows
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidRow1Greater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(8, 1, 6, 4);
  }

  // throw exception trying to remove from a negative row
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidRow1Negative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(-1, 1, 6, 4);
  }

  // throw exception if trying to remove from row that is greater tha total rows
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidRow2Greater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 1, 8, 4);
  }

  // throw exception trying to remove from a negative row
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidRow2Negative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 1, -1, 4);
  }

  // throw exception trying to remove card that is negative index in row
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidCard1Negative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, -1, 6, 4);
  }

  // throw exception trying to remove card that is negative index in row
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidCard2Negative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 5, 0, -1);
  }

  // throw exception trying to remove card that is greater than row index
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidCard1Greater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 7, 6, 5);
  }

  // throw exception trying to remove card that is bigger than row size
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveInvalidCard2Greater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 5, 0, 1);
  }

  // throw exception trying to remove card that is not exposed
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCard1NotExposed() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 6, 6, 5);
  }

  // throw exception trying to remove card that is not exposed
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveCard2NotExposed() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 5, 6, 6);
  }

  // throw exception trying to remove card that is same card as 2nd card
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveSameCard() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 6, 6, 6);
  }

  // throw exception cards dont add to 13
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNotAdd13() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 6, 6, 5);
  }

  // see if remove method actually removes two cards correctly
  @Test
  public void testRemoveTwo() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);

    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    notRandomSolitaire.remove(6, 0, 6, 3);
    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getCardAt(6, 0), null);
    assertEquals(notRandomSolitaire.getCardAt(6, 3), null);
  }

  // TESTS FOR REMOVE CALLED WITH ONLY 1 CARD (KING)


  @Test
  // remove 1 king since value is already 13
  public void testRemove1King() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);

    System.out.println("Before remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    notRandomSolitaire.remove(6, 1);
    System.out.println("After remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getCardAt(6, 1), null);
  }

  // THEN THE EXCEPTIONS

  // throw exception if game hasn't started yet
  @Test(expected = IllegalStateException.class)
  public void testRemove1State() {
    defaultSolitaire.remove(6, 1);
  }

  // see if row is greater than row index bounds
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1InvalidRowGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(7, 6);
  }

  // see if row is less than row index bounds
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1InvalidRowNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(-1, 6);
  }

  // see if card is out of bounds for particular row
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1InvalidCardGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(3, 4);
  }

  // see if card is out of bounds for particular row (negative)
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1InvalidCardNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(3, -1);
  }

  // check if card is not exposed
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1NotExposed() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(3, 3);
  }

  // check if card is not 13 value (not king)
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemove1Not13() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.remove(6, 0);
  }

  //////////////////////////////////////////////
  // TESTS FOR REMOVEUSINGDRAW METHOD

  // throw exception if game hasn't started yet
  @Test(expected = IllegalStateException.class)
  public void testRemoveDrawState() {
    defaultSolitaire.removeUsingDraw(0, 6, 1);
  }

  // see if row is greater than row index bounds
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawInvalidRowGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, 7, 6);
  }

  // see if row is less than row index bounds
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawInvalidRowNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, -1, 6);
  }

  // see if card is out of bounds for particular row
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawInvalidCardGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, 3, 4);
  }

  // see if card is out of bounds for particular row (negative)
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawInvalidCardNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, 3, -1);
  }

  // check if card is not exposed
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawNotExposed() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, 3, 3);
  }


  // check if card is not 13 value (not king)
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveDrawNot13() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(0, 6, 0);
  }

  // check if draw index is negative
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDrawIndexNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(-1, 6, 0);
  }

  // check if draw index is greater than bounds of draw pile
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDrawIndexGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.removeUsingDraw(3, 4, 0);
  }

  @Test
  // make sure remove draw actually works and is shown on the pyramid
  public void testRemoveDraw() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);

    System.out.println("Before remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    notRandomSolitaire.removeUsingDraw(1, 6, 3);
    System.out.println("After remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getCardAt(6, 3), null);
    assertEquals(notRandomSolitaire.getDrawCards().get(2),
        new Card(8, "diamonds"));
  }

  ///////////////////////////
  /////// TEST METHODS FOR DISCARDDRAW

  // throw exception if game hasn't started yet
  @Test(expected = IllegalStateException.class)
  public void testDiscardDrawState() {
    defaultSolitaire.discardDraw(0);
  }

  // check if draw index is negative
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardInvalidDrawIndexNegative() {
    defaultSolitaire.startGame(standardDeck, false, 7, 1);
    defaultSolitaire.discardDraw(-1);
  }

  // check if draw index is greater than bounds of draw pile
  // throw exception if so
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardInvalidDrawIndexGreater() {
    defaultSolitaire.startGame(standardDeck, false, 7, 3);
    defaultSolitaire.discardDraw(3);
  }

  // check if drawIndex is null & use test after this one to see if it throws exception
  @Test
  public void testDiscardNullPart1() {
    defaultSolitaire.startGame(standardDeck, false, 9, 3);
    for (int i = 0; i < 5; i++) {
      defaultSolitaire.discardDraw(0);

    }
    assertEquals(defaultSolitaire.getDrawCards().get(0), null);
  }

  // check if discardDraw throw exception if index card is null
  @Test(expected = IllegalArgumentException.class)
  public void testDiscardNullPart2() {
    defaultSolitaire.startGame(standardDeck, false, 9, 3);
    for (int i = 0; i < 6; i++) {
      defaultSolitaire.discardDraw(0);

    }

  }


  @Test
  // make sure remove discardDraw actually works and is shown on the pyramid
  public void testDiscardDraw() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    System.out.println("Before remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    notRandomSolitaire.discardDraw(0);
    System.out.println("After remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getDrawCards().get(2),
        new Card(8, "diamonds"));
  }

  ///////////////
  ///// TEST METHODS FOR ISGAMEOVER


  @Test
  // test method isGameOver on game that just started
  public void testIsGameOver() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);

    System.out.println("Before remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);

    System.out.println("After remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertFalse(notRandomSolitaire.isGameOver());
  }

  // TESTS FOR GETNUMROWS

  @Test
  // make sure remove draw actually works and is shown on the pyramid
  public void testGetNumRows1() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 1, 3);
    assertEquals(notRandomSolitaire.getNumRows(), 1);
  }

  @Test
  // make sure remove draw actually works and is shown on the pyramid
  public void testGetNumRows9() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);
    assertEquals(notRandomSolitaire.getNumRows(), 9);
  }

  @Test
  // make sure remove draw actually works and is shown on the pyramid
  public void testGetNumRowsNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    assertEquals(notRandomSolitaire.getNumRows(), -1);
  }

  // TESTS FOR GETROWWIDTH


  @Test
  // test the row width on row 4 out of 9
  public void testRowWidth4() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);
    assertEquals(notRandomSolitaire.getRowWidth(4), 5);
  }

  @Test
  // test row width on 1 element pyramid
  public void testRowWidth1() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 1, 3);
    assertEquals(notRandomSolitaire.getRowWidth(0), 1);
  }

  @Test(expected = IllegalStateException.class)
  // test row width before game starts, test for exception
  public void testRowWidthNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    assertEquals(notRandomSolitaire.getRowWidth(4), -1);
  }

  @Test(expected = IllegalArgumentException.class)
  // throw exception since row width input row is negative
  public void testRowWidthInvalidIndexGreater() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    assertEquals(notRandomSolitaire.getRowWidth(5), -1);
  }

  @Test(expected = IllegalArgumentException.class)
  // throw exception since row width input row is greater than rows in pyramid
  public void testRowWidthInvalidIndexNegative() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    assertEquals(notRandomSolitaire.getRowWidth(-1), -1);
  }

  // TESTS FOR GETNUMDRAW

  @Test
  // test get num draw on draw size of 3
  public void testGetNumDraw3() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    assertEquals(notRandomSolitaire.getNumDraw(), 3);
  }

  @Test
  // test get num draw for size of 1
  public void testGetNumDraw1() {
    notRandomSolitaire.startGame(standardDeck, true, 5, 1);
    assertEquals(notRandomSolitaire.getNumDraw(), 1);
  }

  @Test
  // test get num draw when game not started
  public void testGetNumDrawGameNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    assertEquals(notRandomSolitaire.getNumDraw(), -1);
  }

  // TESTS FOR METHOD getScore()

  @Test(expected = IllegalStateException.class)
  // throw exception when game hasnt been started yet
  public void testGetScoreGameNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    assertEquals(notRandomSolitaire.getScore(), 3);
  }

  @Test
  // test get score on pyramid of 3 rows
  public void testGetScore3() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 3, 3);
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getScore(), 44);
  }

  @Test
  // test get score on pyramid of 1 singular row
  public void testGetScore1() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 1, 3);
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getScore(), 9);
  }


  @Test
  // test score before and after a card is removed
  public void testGetScore3Removed() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 3, 3);

    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getScore(), 44);
    notRandomSolitaire.removeUsingDraw(1, 2, 1);


    assertEquals(notRandomSolitaire.getScore(), 33);
  }

  //  tests for getCardAt() method

  @Test(expected = IllegalStateException.class)
  // throw state exception since game hasnt started yet
  public void testGetCardAtNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    assertEquals(notRandomSolitaire.getCardAt(3, 3), 3);
  }

  @Test
  // test method to see if it returns the correct card
  public void testGetCardAt() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.getCardAt(3, 3), new Card(1, "clubs"));
  }

  @Test(expected = IllegalArgumentException.class)
  // throw illegalargumentexception since row is greater than max row for index
  public void testGetCardInvalidIndexRowGreater() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    assertEquals(notRandomSolitaire.getCardAt(7, 3), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  // throw illegalargumentexception since row is negative
  public void testGetCardInvalidIndexRowNegative() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    assertEquals(notRandomSolitaire.getCardAt(-1, 3), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  // throw illegalargumentexception since card is negative
  public void testGetCardInvalidIndexCardNegative() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    assertEquals(notRandomSolitaire.getCardAt(3, -1), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  // throw illegalargumentexception since card index is greater than the max per that row
  public void testGetCardInvalidIndexCardGreater() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 7, 3);
    assertEquals(notRandomSolitaire.getCardAt(3, 5), 3);
  }

  //////////////////////
  ////// TESTS FOR GETDRAWCARDS


  @Test(expected = IllegalStateException.class)
  // make sure illegalstateexception is thrown when game not started
  public void testGetDrawCardsNotStarted() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.getDrawCards();

  }

  @Test
  // get the draw cards when all 3 are there
  public void testGetDrawCards3There() {
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    System.out.println("After remove: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    Card queenHeartTest = new Card(12, "hearts");
    Card kingHeartTest = new Card(13, "hearts");
    Card fiveHeartTest = new Card(5, "hearts");
    Card[] drawArray = new Card[]{queenHeartTest, kingHeartTest, fiveHeartTest};

    assertArrayEquals(notRandomSolitaire.getDrawCards().toArray(), drawArray);
  }

  @Test
  // get the draw cards when only 2 cards are left in drawArray
  public void testGetDrawCardsOnly2Left() {
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    Card queenHeartTest = new Card(12, "hearts");
    Card twoDiamondTest = new Card(2, "diamonds");
    Card fiveHeartTest = new Card(5, "hearts");
    Card[] drawArray2 = new Card[]{queenHeartTest, twoDiamondTest, fiveHeartTest};

    assertArrayEquals(notRandomSolitaire.getDrawCards().toArray(), drawArray2);

    //  notRandomSolitaire.discardDraw(1);
  }

  /////////////////////////
  ////// TEST FOR ISGAMEOVER() method

  @Test
  // game should not be over here since there are still pairs left to make
  public void testIsGameOverPyramidPairsLeft() {
    notRandomSolitaire.startGame(standardDeck, true, 9, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(1);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.isGameOver(), false);
  }

  @Test
  // game should be over since there are no more moves left
  public void testIsGameOverGameWon() {
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    // PLAY GAME BELOW THIS LINE
    notRandomSolitaire.removeUsingDraw(0, 4, 3);
    notRandomSolitaire.removeUsingDraw(0, 4, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.remove(4, 2, 4, 4);
    notRandomSolitaire.remove(4, 1, 3, 3);
    notRandomSolitaire.removeUsingDraw(0, 3, 2);
    notRandomSolitaire.removeUsingDraw(0, 3, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.removeUsingDraw(0, 3, 1);
    notRandomSolitaire.removeUsingDraw(1, 2, 0);
    notRandomSolitaire.removeUsingDraw(2, 2, 1);
    notRandomSolitaire.removeUsingDraw(1, 1, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.removeUsingDraw(0, 2, 2);
    notRandomSolitaire.removeUsingDraw(1, 1, 1);
    notRandomSolitaire.removeUsingDraw(1, 0, 0);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    assertEquals(notRandomSolitaire.isGameOver(), false);
  }

  @Test
  // game shouldn't be over since there is one draw match left
  public void testIsGameOverStockEmptyButMatchExistsDraw() {
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    // PLAY GAME BELOW THIS LINE
    notRandomSolitaire.removeUsingDraw(0, 4, 3);
    notRandomSolitaire.removeUsingDraw(0, 4, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.remove(4, 2, 4, 4);
    notRandomSolitaire.remove(4, 1, 3, 3);
    notRandomSolitaire.removeUsingDraw(0, 3, 2);
    notRandomSolitaire.removeUsingDraw(0, 3, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.removeUsingDraw(0, 3, 1);
    notRandomSolitaire.removeUsingDraw(1, 2, 0);
    notRandomSolitaire.removeUsingDraw(2, 2, 1);
    notRandomSolitaire.removeUsingDraw(1, 1, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.removeUsingDraw(0, 2, 2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());
    System.out.println(notRandomSolitaire.isGameOver());
    assertEquals(notRandomSolitaire.isGameOver(), false);

  }


  @Test
  // game should not be over since there is at least one card left in the stock
  public void testIsGameOverNoMatchesButStock1() {
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    // PLAY GAME BELOW THIS LINE
    notRandomSolitaire.removeUsingDraw(0, 4, 3);
    notRandomSolitaire.removeUsingDraw(0, 4, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.remove(4, 2, 4, 4);
    notRandomSolitaire.remove(4, 1, 3, 3);
    notRandomSolitaire.removeUsingDraw(0, 3, 2);
    notRandomSolitaire.removeUsingDraw(0, 3, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.removeUsingDraw(0, 3, 1);
    notRandomSolitaire.removeUsingDraw(1, 2, 0);
    notRandomSolitaire.removeUsingDraw(2, 2, 1);
    notRandomSolitaire.removeUsingDraw(1, 1, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.removeUsingDraw(0, 2, 2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.discardDraw(2);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.removeUsingDraw(1, 1, 1);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    assertEquals(notRandomSolitaire.isGameOver(), false);
  }

  @Test
  // game should not be over since there is at least one card left in the stock
  // THEN last match is removed and game over is true!
  public void testIsGameOverNoCardsInStockOrDrawButPyramidMatchExists() {
    notRandomSolitaire.startGame(standardDeck, true, 5, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    // PLAY GAME BELOW THIS LINE
    notRandomSolitaire.removeUsingDraw(0, 4, 3);
    notRandomSolitaire.removeUsingDraw(0, 4, 0);
    notRandomSolitaire.discardDraw(0);
    notRandomSolitaire.remove(4, 2, 4, 4);

    for (int i = 0; i < 32; i++) {
      notRandomSolitaire.discardDraw(0);
    }
    notRandomSolitaire.discardDraw(1);
    notRandomSolitaire.discardDraw(2);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    assertEquals(notRandomSolitaire.isGameOver(), false);
    notRandomSolitaire.remove(4, 1, 3, 3);
    assertEquals(notRandomSolitaire.isGameOver(), true);
  }

  @Test
  // there are no pyramid matches and no cards in the stock but draw card match still exists
  // THEN make this draw card match and then the game becomes over
  public void testIsGameOverNoPyramidMatchesButDrawMatchExists() {
    notRandomSolitaire.startGame(standardDeck, true, 1, 3);
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    // PLAY GAME BELOW THIS LINE

    for (int i = 0; i < 34; i++) {
      notRandomSolitaire.discardDraw(0);
    }
    for (int i = 0; i < 15; i++) {
      notRandomSolitaire.discardDraw(1);
    }
    notRandomSolitaire.discardDraw(2);

    System.out.println("Test Pyramid: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    assertEquals(notRandomSolitaire.isGameOver(), false);
    notRandomSolitaire.removeUsingDraw(0, 0, 0);
    assertEquals(notRandomSolitaire.isGameOver(), true);

  }


  @Test
  public void testStartGameTwice() {
    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);

    notRandomSolitaire.startGame(
        standardDeck, false, 7, 3);

    notRandomSolitaire.discardDraw(1);

    System.out.println("First Startgame & discard: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    notRandomSolitaire.startGame(
        standardDeck, false, 7, 3);

    System.out.println("Second startgame without discard: \n");
    System.out.println(notRandomSolitaireTextualView.toString());

    // test only makes it here if there are no exceptions
    assertEquals(notRandomSolitaire.getDrawCards().get(1), new Card(8, "diamonds"));

  }

  // checks if cards in game are shuffled by examining the draw pile
  // also shows a view which shows the pyramid cards are shuffled
  @Test
  public void testStartGameShuffles() {

    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);

    notRandomSolitaire.startGame(
        standardDeck, true, 7, 3);

    // if draw pile cards are what we expect for the fixed RAND value
    // then we know the cards were shuffled

    Card[] drawArray = {
        new Card(1, "spades"),
        new Card(6, "spades"),
        new Card(8, "diamonds")};

    System.out.println(notRandomSolitaireTextualView.toString());

    assertArrayEquals(notRandomSolitaire.getDrawCards().toArray(), drawArray);
  }

  // test that pyramidsolitairetextual view tostring method is correct for normal pyramid
  @Test
  public void testTextualToString() {

    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);

    notRandomSolitaire.startGame(
        standardDeck, false, 7, 3);

    assertEquals(notRandomSolitaireTextualView.toString(),
        "            A♣\n"
            + "          A♦  A♥\n"
            + "        A♠  2♣  2♦\n"
            + "      2♥  2♠  3♣  3♦\n"
            + "    3♥  3♠  4♣  4♦  4♥\n"
            + "  4♠  5♣  5♦  5♥  5♠  6♣\n"
            + "6♦  6♥  6♠  7♣  7♦  7♥  7♠\n"
            + "Draw: 8♣, 8♦, 8♥");

  }

  // test that pyramidsolitairetextual view tostring method is correct for when game hasnt started
  // should output empty string ""
  @Test
  public void testTextualToStringGameNotStarted() {


    PyramidSolitaireTextualView notRandomSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);

    assertEquals(notRandomSolitaireTextualView.toString(), "");

  }

  // see if remove method can actually remove a pair of cards correctly
  @Test
  public void testRemoveTwoPair() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 8, 3);

    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    notRandomSolitaire.remove(7, 4, 7, 6);
    notRandomSolitaire.remove(7, 3, 6, 3);

    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    assertEquals(notRandomSolitaire.getCardAt(6, 3), null);

  }

  // see if remove method can actually remove a pair of cards correctly
  @Test(expected = IllegalArgumentException.class)
  public void testNot13RemoveTwoPair() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 8, 3);



    notRandomSolitaire.remove(7, 4, 7, 6);
    notRandomSolitaire.remove(7, 3, 6, 3);
    notRandomSolitaire.remove(7,5,7,7);


    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    // do not add to 13

    notRandomSolitaire.remove(7,2,6,2);

    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    assertEquals(notRandomSolitaire.getCardAt(6, 2),new Card(8,"clubs"));

  }

  // see if remove method can actually remove a pair of cards correctly
  @Test(expected = IllegalArgumentException.class)
  public void testTopCardNotExposedRowRemoveTwoPair() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 6, 3);


    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());



    notRandomSolitaire.remove(3,2,4,3);

    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());


  }

  // see if remove method can actually remove a pair of cards correctly
  @Test(expected = IllegalArgumentException.class)
  public void testTwoPairNot1RowApart() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 6, 3);


    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    notRandomSolitaire.remove(5,1,2,0);

    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());


  }


  // see if remove method can actually remove a pair of cards correctly
  @Test(expected = IllegalArgumentException.class)
  public void testTwoPairNot1CardApart() {
    PyramidSolitaireTextualView pyramidSolitaireTextualView =
        new PyramidSolitaireTextualView(notRandomSolitaire);
    notRandomSolitaire.startGame(standardDeck, true, 6, 3);


    System.out.println("Before remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());

    notRandomSolitaire.remove(5,0,4,3);

    System.out.println("After remove: \n");
    System.out.println(pyramidSolitaireTextualView.toString());


  }

}




