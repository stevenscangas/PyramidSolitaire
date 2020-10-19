package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.GameStatus;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 */
public class RelaxedPyramidSolitaire extends BasicPyramidSolitaire
    implements PyramidSolitaireModel<Card> {

  /**
   * This is the main constructor for BasicPyramidSolitaire Class. Returns BasicPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   */
  public RelaxedPyramidSolitaire() {
    // initialize the game state to not started, this will be started in startGame class
    super();
  }

  /**
   * This is the main constructor for BasicPyramidSolitaire Class. Returns BasicPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   *
   * @param randomValue the randomValue to be used to initialize the rand for testing.
   */
  // constructor to set rand for testing
  public RelaxedPyramidSolitaire(int randomValue) {
    // initialize the game state to not started, this will be started in startGame class
    super(randomValue);

  }


  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the specific game in the classes implementing this interface.
   *
   * @return The deck of cards as a list.
   */
  @Override
  public List<Card> getDeck() {
    return super.getDeck();
  }

  /**
   * <p>Deal a new game of Pyramid Solitaire.
   * The cards to be used and their order are specified by the the given deck, unless the {@code
   * shuffle} parameter indicates the order should be ignored.</p>
   *
   * <p>This method first verifies that the deck is valid. It deals cards in rows
   * (left-to-right, top-to-bottom) into the characteristic pyramid shape with the specified number
   * of rows, followed by the specified number of draw cards. When {@code shuffle} is {@code false},
   * the 0th card in {@code deck} is used as the first card dealt.</p>
   *
   * <p>This method should have no other side effects, and should work for any valid arguments.</p>
   *
   * @param deck    the deck to be dealt
   * @param shuffle if {@code false}, use the order as given by {@code deck}, otherwise use a
   *                randomly shuffled order
   * @param numRows number of rows in the pyramid
   * @param numDraw number of draw cards available at a time
   * @throws IllegalArgumentException if the deck is null or invalid, the number of pyramid rows is
   *                                  non-positive, the number of draw cards available at a time is
   *                                  negative, or a full pyramid and draw pile cannot be dealt with
   *                                  the number of given cards in deck
   */
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numRows, int numDraw)
      throws IllegalArgumentException {
    super.startGame(deck, shuffle, numRows, numDraw);
  }

  /**
   * Remove two exposed cards on the pyramid, using the two specified card positions.
   *
   * @param row1  row of first card position, numbered from 0 from the top of the pyramid
   * @param card1 card of first card position, numbered from 0 from left
   * @param row2  row of second card position
   * @param card2 card of second card position
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row1, int card1, int row2, int card2)
      throws IllegalArgumentException, IllegalStateException {

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // if these cards are an exposed pair, remove them
    if (isExposedPair(row1, card1, row2, card2)) {
      pyramidArray.get(row1).set(card1, null);
      pyramidArray.get(row2).set(card2, null);
      return;
    }

    // check bounds of all row and card parameters, throw exception if out of bounds of pyramid
    if (row1 > getNumRows() - 1 || row2 > getNumRows() - 1) {
      throw new IllegalArgumentException("Rows is not within the bounds"
          + " of the card pyramid (vertically)");
    }

    // check bounds of all row and card parameters, throw exception if out of bounds of pyramid
    if (card1 > getRowWidth(row1) - 1 || card2 > getRowWidth(row2)) {
      throw new IllegalArgumentException("One of the cards is not within horizontal bounds.");
    }

    // throw exception both cards picked are the same
    if (getCardAt(row1, card1).equals(getCardAt(row2, card2))) {
      throw new IllegalArgumentException("Cards cannot be the same card");
    }

    // throw exception if card values don't add up to 13
    if (getCardAt(row1, card1).getValue() + getCardAt(row2, card2).getValue() != 13) {
      throw new IllegalArgumentException("Card values do not add up to 13.");
    }

    // see if both cards are exposed, if so, continue
    if (!isCellExposed(row1, card1) || !isCellExposed(row2, card2)) {
      throw new IllegalArgumentException("One or both of these cards is not exposed.");
    }

    // if none of the above exceptions are thrown, then remove both cards
    else {
      pyramidArray.get(row1).set(card1, null);
      pyramidArray.get(row2).set(card2, null);
    }

  }

  /**
   * Remove a single card on the pyramid, using the specified card position.
   *
   * @param row  row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void remove(int row, int card) throws IllegalArgumentException, IllegalStateException {
    super.remove(row, card);
  }

  /**
   * Remove two cards, one from the draw pile and one from the pyramid.
   *
   * @param drawIndex the card from the draw pile, numbered from 0 from left
   * @param row       row of the desired card position, numbered from 0 from the top of the pyramid
   * @param card      card of the desired card position, numbered from 0 from left
   * @throws IllegalArgumentException if the attempted remove is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void removeUsingDraw(int drawIndex, int row, int card)
      throws IllegalArgumentException, IllegalStateException {
    super.removeUsingDraw(drawIndex, row, card);
  }

  /**
   * Discards an individual card from the draw pile.
   *
   * @param drawIndex the card from the draw pile to be discarded
   * @throws IllegalArgumentException if the index is invalid or no card is present there.
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public void discardDraw(int drawIndex) throws IllegalArgumentException, IllegalStateException {

    super.discardDraw(drawIndex);

  }

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   *
   * @return the height of the pyramid, or -1
   */
  @Override
  public int getNumRows() {
    return super.getNumRows();
  }

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  @Override
  public int getNumDraw() {
    return super.getNumDraw();
  }

  /**
   * Returns the width of the requested row, measured from the leftmost card to the rightmost card
   * (inclusive) as the game is initially dealt.
   *
   * @param row the desired row (0-indexed)
   * @return the number of spaces needed to deal out that row
   * @throws IllegalArgumentException if the row is invalid
   * @throws IllegalStateException    if the game has not yet been started
   */
  @Override
  public int getRowWidth(int row) {
    return super.getRowWidth(row);
  }

  /**
   * Signal if the game is over or not. A game is said to be over if there are no possible removes
   * or discards.
   *
   * @return true if game is over, false otherwise
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public boolean isGameOver() throws IllegalStateException {

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    if (pyramidArray.isEmpty()) {
      return true;
    }

    // check if every card in draw list is null
    boolean allDrawNull = true;
    // check if all draw cards are null
    for (int a = 0; a < getNumDraw(); a++) {
      if (getDrawCards().get(a) != null) {
        allDrawNull = false;
      }
    }

    if (!allDrawNull) {
      return false;
    }

    if (getScore() == 0) {
      return true;
    }

    int gottenNumRows = getNumRows();

    List<Card> exposedList = new ArrayList<Card>();

    // check every card in the pyramid to see if its exposed,
    // if it is, add it to the exposedList

    // iterate through each row of pyramid 2D array
    for (int row = 0; row < gottenNumRows; row++) {
      // iterate through each individual card in this row
      for (int cardNum = 0; cardNum <= getRowWidth(row) - 1; cardNum++) {
        // check if this card is exposed and not removed yet
        Card currentCard = getCardAt(row, cardNum);
        if (getCardAt(row, cardNum) != null && isCellExposed(row, cardNum)) {
          exposedList.add(getCardAt(row, cardNum));
        }
      }
    }

    boolean pyramidMatchExists = false;
    boolean drawMatchExists = false;
    boolean exposedPairExists = false;

    // now check every card in the exposed list against every other card
    // in it to see if any add up to 13
    for (int currentCard = 0; currentCard < exposedList.size(); currentCard++) {
      for (Card card : exposedList) {
        // check if sum is 13
        if (exposedList.get(currentCard).getValue() + card.getValue() == 13
            // or if this card is a king
            || exposedList.get(currentCard).getValue() == 13) {
          pyramidMatchExists = true;
        }
      }
    }

    if (getNumDraw() > 0) {
      // now check every card in the exposed list against any draw cards
      // in it to see if any add up to 13
      for (int currentCard = 0; currentCard < exposedList.size(); currentCard++) {
        for (int drawCardIndex = 0; drawCardIndex < getNumDraw(); drawCardIndex++) {
          // check if sum is 13
          Card drawCard;
          if (getDrawCards().get(drawCardIndex) != null) {
            drawCard = getDrawCards().get(drawCardIndex);
            if (exposedList.get(currentCard).getValue() + drawCard.getValue() == 13) {
              drawMatchExists = true;
            }
          }
        }
      }
    }

    // check if any two cards on the pyramid form an exposed pair
    // for every exposed cell we find, check if the card in there forms a pair with
    // either of the two cards above it

    // iterate through each row of pyramid 2D array
    for (int row = 0; row < gottenNumRows; row++) {
      // iterate through each individual card in this row
      for (int cardNum = 0; cardNum <= getRowWidth(row) - 1; cardNum++) {

        // if cell is exposed, check if card in there forms a pair
        if (getCardAt(row, cardNum) != null && isCellExposed(row, cardNum)) {

          if ((row - 1 >= 0 && (isExposedPair(row, cardNum, row - 1, cardNum)))
              || (((row - 1 >= 0) && (cardNum - 1 >= 0))
              && isExposedPair(row, cardNum, row - 1, cardNum - 1))) {
            exposedPairExists = true;
          }
        }
      }
    }

    // game not over until there are no combinations AND no cards left in stock to use

    if (pyramidMatchExists || drawMatchExists || exposedPairExists) {
      return false;
    }
    if (getNumDraw() == 0) {
      return true;
    } else {
      return stockList.size() == 0;
    }
  }

  /**
   * Return the current score, which is the sum of the values of the cards remaining in the
   * pyramid.
   *
   * @return the score
   * @throws IllegalStateException if the game hasn't been started yet
   */

  @Override
  public int getScore() throws IllegalStateException {
    return super.getScore();
  }

  /**
   * Returns the card at the specified coordinates.
   *
   * @param row  row of the desired card (0-indexed from the top)
   * @param card column of the desired card (0-indexed from the left)
   * @return the card at the given position, or <code>null</code> if no card is there
   * @throws IllegalArgumentException if the coordinates are invalid
   * @throws IllegalStateException    if the game hasn't been started yet
   */
  @Override
  public Card getCardAt(int row, int card) throws IllegalStateException {
    return super.getCardAt(row, card);
  }

  /**
   * Returns the currently available draw cards. There should be at most {@link
   * PyramidSolitaireModel#getNumDraw} cards (the number specified when the game started) -- there
   * may be fewer, if cards have been removed.
   *
   * @return the ordered list of available draw cards
   * @throws IllegalStateException if the game hasn't been started yet
   */
  @Override
  public List<Card> getDrawCards() throws IllegalStateException {
    return super.getDrawCards();
  }


  // method to check if two cards on top of each other are in a pair
  private boolean isExposedPair(int row1, int card1, int row2, int card2) {

    // throw exception since game hasn't started
    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check bounds of all row and card parameters, throw exception if out of bounds of pyramid
    if (row1 > getNumRows() - 1 || row2 > getNumRows() - 1) {
      return false;
    }

    // check bounds of all row and card parameters, throw exception if out of bounds of pyramid
    if (card1 > getRowWidth(row1) - 1 || card2 > getRowWidth(row2) - 1) {
      return false;
    }

    // false if cards don't add to 13
    if (getCardAt(row1, card1).getValue() + getCardAt(row2, card2).getValue() != 13) {
      return false;
    }

    // check if row difference is not exactly 1
    if (Math.abs(row1 - row2) != 1) {
      return false;
    }

    // REMOVE TWO CARDS AS A PAIR CASE 2

    // if row1 greater than row2
    if (row1 < row2) {

      // is bottom card exposed?
      if (isCellExposed(row2, card2)) {

        // if card2 is below to the left or below to the right of card1
        if (card1 == card2 || card1 == card2 - 1) {

          return true;
        }
      }
    }

    // REMOVE TWO CARDS AS A PAIR CASE 2

    // if row1 greater than row2
    if (row1 > row2) {

      // is bottom card exposed?
      if (isCellExposed(row1, card1)) {

        // if card2 is below to the left or below to the right of card1
        if (card1 == card2 || card2 == card1 - 1) {
          return true;
        }
      }
    }
    return false;
  }


}
