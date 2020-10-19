package cs3500.pyramidsolitaire.model.hw02;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * The model for playing a game of Pyramid Solitaire: this maintains the state and enforces the
 * rules of gameplay.
 */
public class BasicPyramidSolitaire implements PyramidSolitaireModel<Card> {

  /**
   * This is the main constructor for BasicPyramidSolitaire Class. Returns BasicPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   */
  public BasicPyramidSolitaire() {
    // initialize the game state to not started, this will be started in startGame class
    gameStatus = GameStatus.NOTSTARTED;
    rand = new Random(System.currentTimeMillis());
  }

  /**
   * This is the main constructor for BasicPyramidSolitaire Class. Returns BasicPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   *
   * @param randomValue the randomValue to be used to initialize the rand for testing.
   */
  // constructor to set rand for testing
  public BasicPyramidSolitaire(int randomValue) {
    // initialize the game state to not started, this will be started in startGame class
    gameStatus = GameStatus.NOTSTARTED;
    rand = new Random(randomValue);
  }

  // 2D array to hold all of the Cards in the pyramid
  protected final List<ArrayList<Card>> pyramidArray = new ArrayList<ArrayList<Card>>();

  // ArrayList to hold all of the cards in the stock (left in the deck and not visible)
  protected final List<Card> stockList = new ArrayList<Card>();

  // ArrayList to hold the cards in the draw pile that are visible
  protected Card[] drawArray;

  // status of game, is started? , won or lost?
  public GameStatus gameStatus = GameStatus.NOTSTARTED;

  // rand value to initialize rand method for testing
  protected final Random rand;

  /**
   * Return a valid and complete deck of cards for a game of Pyramid Solitaire. There is no
   * restriction imposed on the ordering of these cards in the deck. The validity of the deck is
   * determined by the rules of the specific game in the classes implementing this interface.
   *
   * @return The deck of cards as a list.
   */
  @Override
  public List<Card> getDeck() {
    List<Card> deckList = new ArrayList<Card>();

    // iterates through numbers 1-13
    for (int i = 1; i <= 13; i++) {

      // adds card to deck with current value (i) and with each suit
      deckList.add(new Card(i, "clubs"));
      deckList.add(new Card(i, "diamonds"));
      deckList.add(new Card(i, "hearts"));
      deckList.add(new Card(i, "spades"));
    }
    return deckList;
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

    // throw exception if deck is null
    if (Objects.isNull(deck)) {
      throw new IllegalArgumentException("DECK IS NULL");
    }

    // throw exception if deck is too big
    if (deck.size() != 52) {
      throw new IllegalArgumentException("Deck must have 52 cards");
    }

    // throw exception if rows is not positive
    if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive.");
    }

    // throw exception if negative cards in draw pile
    if (numDraw < 0) {
      throw new IllegalArgumentException("Number of cards in draw pile cannot be negative.");
    }

    // calculate total amount of cards needed to deal pyramid and draw pile
    int totalCardsNeeded = 0;
    for (int v = 1; v <= numRows; v++) {
      totalCardsNeeded += v;
    }
    totalCardsNeeded += numDraw;

    // throw exception if need more cards for game than cards exist in deck
    if (totalCardsNeeded > 52) {
      throw new IllegalArgumentException("Number of cards in pyramid & draw pile exceeds"
          + "number of cards in deck (52).");
    }

    // test for duplicates by creating a hashset and comparing its size to stock
    // since set only contain one of each identical element
    // throw exception if a duplicate exists
    Set<Card> cardSet = new HashSet<Card>(deck);

    if (cardSet.size() < deck.size()) {
      throw new IllegalArgumentException("Deck contains duplicate of one or more cards.");
    }

    if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck has one or more null cards. Invalid Deck.");
    }

    // add all of the cards generated in the deck into the stock
    stockList.clear();
    stockList.addAll(deck);

    // SHUFFLE DECK (stock) HERE IF NEEDED
    if (shuffle) {
      for (int a = 0; a < stockList.size(); a++) {
        // swap every card with another random card in the stock
        Card tempCard;

        int randomIndex = rand.nextInt(stockList.size());

        // swaps the cards at current index and random index
        tempCard = stockList.get(randomIndex);
        stockList.set(randomIndex, stockList.get(a));
        stockList.set(a, tempCard);
      }
    }

    pyramidArray.clear();

    // initialize array to be a size where it can hold all of the cards
    // add rows one at a time
    for (int k = 0; k < numRows; k++) {
      pyramidArray.add(new ArrayList<Card>());
    }

    // iterate through each row of pyramid 2D arraylist
    for (int row = 0; row < numRows; row++) {

      // iterate through each individual card in this row
      for (int cardNum = 0; cardNum < row + 1; cardNum++) {

        // set this cell of 2D arraylist to the first card in the stock
        pyramidArray.get(row).add(stockList.get(0));

        // remove the first card in the stock since it was dealt to the pyramid
        stockList.remove(0);
      }
    }

    drawArray = new Card[numDraw];

    // iterate for number of cards to put into the draw pile
    for (int i = 0; i < numDraw; i++) {
      // set the current element of the draw list equal to the first element in the stock
      drawArray[i] = stockList.get(0);
      // remove the first element of the stock since it was added to the draw pile
      stockList.remove(0);
    }
    // start game
    gameStatus = GameStatus.STARTED;
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check bounds of row and card parameters, throw exception if out of bounds of pyramid
    if (row > getNumRows() - 1 || card > getRowWidth(row)) {
      throw new IllegalArgumentException("Either the card or row is not within the bounds"
          + " of the card pyramid.");
    }

    // check if cell is exposed
    // Throws exception if cell is not exposed
    if (!isCellExposed(row, card)) {
      throw new IllegalArgumentException("This card is not exposed.");
    }

    // throw exception if card values don't add up to 13
    if (getCardAt(row, card).getValue() != 13) {
      throw new IllegalArgumentException("Card value is not 13.");
    }

    // if none of the above exceptions are thrown, then remove the card (set it to null)
    else {
      pyramidArray.get(row).set(card, null);
    }
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check valid draw index
    // THROWS exception if not valid
    if (drawIndex < 0 || drawIndex > this.getNumDraw() - 1) {
      throw new IllegalArgumentException("drawIndex is out of bounds of draw pile");
    }

    // check if any cards left in draw array
    // THROWS exception if not
    if (getNumDraw() <= 0) {
      throw new IllegalArgumentException("No cards left in draw pile.");
    }

    // check if draw index is not null
    // THROWS exception if not valid
    if (this.drawArray[drawIndex] == null) {
      throw new IllegalArgumentException("Draw index is null.");
    }

    // check bounds of row and card parameters, throw exception if out of bounds of pyramid
    // THROWS EXCEPTION
    if (row > (getNumRows() - 1) || card > getRowWidth(row)) {

      throw new IllegalArgumentException("The pyramid card is not within the bounds"
          + " of the card pyramid");
    }

    // check if pyramid card is exposed
    // THROWS exception if this pyramid card is not exposed
    if (!isCellExposed(row, card)) {
      throw new IllegalArgumentException("This pyramid card is not exposed.");
    }

    // throw exception if card values don't add up to 13
    if (getCardAt(row, card).getValue() + getDrawCards().get(drawIndex).getValue() != 13) {
      throw new IllegalArgumentException("Card values do not add up to 13.");
    }

    // if none of the above exceptions are thrown, then remove pyramid card & delete draw card
    else {
      // set value at this spot to null since card has been removed
      pyramidArray.get(row).set(card, null);

      // moves a card from the stock into draw if stock has cards left

      discardDraw(drawIndex);
    }
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check valid draw index
    // THROWS exception if not valid
    if (drawIndex < 0 || drawIndex > this.getNumDraw() - 1) {
      throw new IllegalArgumentException("drawIndex is out of bounds of draw pile");
    }

    // throws exception if draw pile is 0
    if (getNumDraw() <= 0) {
      throw new IllegalArgumentException("Draw pile has size 0.");
    }

    // check if draw index is not null
    // THROWS exception if not valid
    if (this.drawArray[drawIndex] == null) {
      throw new IllegalArgumentException("Draw index is null.");
    }

    // take a card from stock and sets the drawIndex to that
    if (stockList.size() > 0) {
      drawArray[drawIndex] = stockList.get(0);
      stockList.remove(0);
    }
    // if stock empty, put a null into draw pile instead
    else {
      drawArray[drawIndex] = null;
    }

  }

  /**
   * Returns the number of rows originally in the pyramid, or -1 if the game hasn't been started.
   *
   * @return the height of the pyramid, or -1
   */
  @Override
  public int getNumRows() {

    if (gameStatus == GameStatus.NOTSTARTED) {
      return -1;
    }

    // length of 2D will output number of rows in it
    return this.pyramidArray.size();
  }

  /**
   * Returns the maximum number of visible cards in the draw pile, or -1 if the game hasn't been
   * started.
   *
   * @return the number of visible cards in the draw pile, or -1
   */
  @Override
  public int getNumDraw() {

    // return -1 if game hasn't started yet
    if (gameStatus == GameStatus.NOTSTARTED) {
      return -1;
    } else {
      return this.drawArray.length;
    }

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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }
    // if row is greater than total number of rows, or less than 0, throw exception
    if (row >= getNumRows() || row < 0) {
      throw new IllegalArgumentException("Not a valid row");
    }
    // row width can be determined by row number
    return row + 1;
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

    // game not over until there are no combinations AND no cards left in stock to use

    if (pyramidMatchExists || drawMatchExists) {
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    int gottenRows = getNumRows();
    int score = 0;

    // iterate from row to row
    for (int row = 0; row < gottenRows; row++) {

      // iterate through each row in the pyramid
      // <= row was changed to getRowWidth(row) in order to allow for abstraction
      // since the multipyramid will extend this class
      for (int card = 0; card <= getRowWidth(row) - 1; card++) {
        // check that the object at this spot is a Card and not null
        if (getCardAt(row, card) != null) {
          // this Card becomes current card
          Card currentCard = getCardAt(row, card);
          // the value of this card in the pyramid is added to the score
          // if card is removed, don't add anything to the score
          score += currentCard.getValue();
        }
      }
    }
    return score;
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check if row/card is negative or if the coordinates are in the array but outside of
    // the pyramid, if either of these are true, throw an exception
    if (row < 0 || row > getNumRows() - 1) {
      throw new IllegalArgumentException("Row number less than 0 or greater than number rows.");
    }

    if (card < 0 || card > getRowWidth(row) - 1) {
      throw new IllegalArgumentException("Card number less than 0 or greater than number of"
          + "cards in that row.");
    }

    // never return private object so that client cannot modify
    Card cardCopy = pyramidArray.get(row).get(card);
    return cardCopy;
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    List<Card> drawCardList = new ArrayList<Card>();

    for (int index = 0; index < drawArray.length; index++) {
      if (drawArray[index] instanceof Card) {
        drawCardList.add(drawArray[index]);
      } else {
        drawCardList.add(null);
      }
    }
    return drawCardList;
  }

  /**
   * Determines whether or not the specified cell in the pyramid is exposed. This means that there
   * are no non-removed cards below it on the left or right.
   *
   * @param row  row of card to check if exposed
   * @param card which card in the row to check if exposed
   * @return whether or not this cell is exposed
   * @throws IllegalStateException    if the game hasn't been started yet
   * @throws IllegalArgumentException if card is not within bounds of pyramid
   */
  // method to check if card at the input position is exposed
  protected boolean isCellExposed(int row, int card) {

    // throw exception if game has not started
    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }

    // check bounds of row and card parameters, throw exception if out of bounds of pyramid
    if (row > getNumRows() - 1 || row < 0 || card > getRowWidth(row) - 1 || card < 0) {
      throw new IllegalArgumentException("Either the card or row is not within the bounds"
          + " of the card pyramid.");
    }

    // case if this cell is in the bottom row of the pyramid, always true
    if (row >= getNumRows() - 1) {
      return true;
    }

    // case if at least one of the cells below this one is not exposed
    if (getCardAt(row + 1, card) != null
        || getCardAt(row + 1, card + 1) != null) {
      return false;
    }

    // case if both cells below this one are exposed
    return (getCardAt(row + 1, card) == null)
        && (getCardAt(row + 1, card + 1) == null);
  }

  /**
   * Determines whether or not this PyramidSolitaire is equal to an input PyramidSolitaire.
   * To be equal, the two PyramidSolitaires must only be of the same class.
   */
  public boolean isEqual(PyramidSolitaireModel<Card> other) {
    return other.getClass() == this.getClass();
  }

}
