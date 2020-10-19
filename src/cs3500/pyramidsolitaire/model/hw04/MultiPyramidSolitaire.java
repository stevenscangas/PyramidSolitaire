package cs3500.pyramidsolitaire.model.hw04;

import cs3500.pyramidsolitaire.model.hw02.BasicPyramidSolitaire;
import cs3500.pyramidsolitaire.model.hw02.Card;
import cs3500.pyramidsolitaire.model.hw02.GameStatus;
import cs3500.pyramidsolitaire.model.hw02.PyramidSolitaireModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The model for playing a multiple pyramid game of Pyramid Solitaire: this maintains the state and
 * enforces the rules of gameplay.
 */
public class MultiPyramidSolitaire extends BasicPyramidSolitaire
    implements PyramidSolitaireModel<Card> {

  /**
   * This is the main constructor for MultiPyramidSolitaire Class. Returns MultiPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   */
  public MultiPyramidSolitaire() {
    // initialize the game state to not started, this will be started in startGame class
    super();
  }

  /**
   * This is the main constructor for MultiPyramidSolitaire Class. Returns MultiPyramidSolitaire
   * with not started game status, and creates a random object initialized with the current time to
   * get a seemingly truly random value every run.
   *
   * @param randomValue the randomValue to be used to initialize the rand for testing.
   */
  // constructor to set rand for testing
  public MultiPyramidSolitaire(int randomValue) {
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
    List<Card> deckList = new ArrayList<Card>();

    // iterates through numbers 1-13
    for (int i = 1; i <= 13; i++) {

      // adds card to deck with current value (i) and with each suit
      deckList.add(new Card(i, "clubs"));
      deckList.add(new Card(i, "diamonds"));
      deckList.add(new Card(i, "hearts"));
      deckList.add(new Card(i, "spades"));
    }

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
    if (deck.size() != 104) {
      throw new IllegalArgumentException("Deck must have 104 cards");
    }

    // throw exception if rows is not positive
    if (numRows < 1) {
      throw new IllegalArgumentException("Number of rows must be positive.");
    }

    // throw exception if negative cards in draw pile
    if (numDraw < 0) {
      throw new IllegalArgumentException("Number of cards in draw pile cannot be negative.");
    }

    if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck has one or more null cards. Invalid Deck.");
    }

    // clear stock to reset game
    stockList.clear();
    // add two decks to stocklist
    stockList.addAll(deck);

    // make sure there is exactly 2 of each card in the deck, no less or no more
    for (Card currentCard : stockList) {
      if (Collections.frequency(stockList, currentCard) != 2) {
        throw new IllegalArgumentException("Double-Deck does not contain exactly 1 duplicate "
            + "of each card.");
      }
    }

    // MATH FOR CREATING MULTI PYRAMID GOES HERE

    // number of rows that overlap among the 3 pyramids
    int overlap;

    // number of cards that aren't null in first row of pyramid
    int firstRowCards;

    // number of cards (spaces) that are in first row of pyramid
    int firstRowNull;

    // number of cards that exist in last row of pyramid
    int lastRowCards;

    // number of incomplete rows, not overlapping
    int incompleteRows;

    // case where rows is even
    if (numRows % 2 == 0) {
      overlap = numRows / 2;
      firstRowCards = numRows + 1;

      lastRowCards = numRows * 2;

    }

    // case where rows is odd
    else {
      overlap = numRows / 2 + 1;
      firstRowCards = numRows;
      lastRowCards = (numRows * 2) - 1;

    }
    firstRowNull = (lastRowCards / 2) - 2;
    incompleteRows = numRows - overlap;

    int totalCardsNeeded = 0;

    // iterate through every incomplete row
    for (int row = 0; row < incompleteRows; row++) {

      // how big the groups of nulls that need to be added are
      int nullsNeededToBeAdded = (firstRowNull / 2) - row;
      // how big the groups of cards that need to be added are
      int cardsNeededToBeAdded = row + 1;

      // how big the groups of card+null are per row
      int combinedAdd = nullsNeededToBeAdded + cardsNeededToBeAdded;

      // run each of the card and null loops 3 times per row
      for (int cycle = 0; cycle < 3; cycle++) {

        // loop that builds the cards
        for (int cell = 0; cell < cardsNeededToBeAdded; cell++) {
          totalCardsNeeded++;
        }
      }
    }

    // add the cards that overlap, this is the easy part
    for (int x = incompleteRows; x < numRows; x++) {
      for (int z = 0; z < firstRowCards + x; z++) {
        totalCardsNeeded++;
      }
    }

    // throw exception if need more cards for game than cards exist in deck
    if (totalCardsNeeded > 104) {
      throw new IllegalArgumentException("Number of cards in pyramid & draw pile exceeds"
          + "number of cards in deck: 104");
    }

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

    // iterate through every incomplete row
    for (int row = 0; row < incompleteRows; row++) {

      // how big the groups of nulls that need to be added are
      int nullsNeededToBeAdded = (firstRowNull / 2) - row;
      // how big the groups of cards that need to be added are
      int cardsNeededToBeAdded = row + 1;

      // how big the groups of card+null are per row
      int combinedAdd = nullsNeededToBeAdded + cardsNeededToBeAdded;

      // run each of the card and null loops 3 times per row
      for (int cycle = 0; cycle < 3; cycle++) {

        // loop that builds the cards
        for (int cell = 0; cell < cardsNeededToBeAdded; cell++) {
          pyramidArray.get(row).add(stockList.get(0));
          stockList.remove(0);
        }

        // loop that builds the nulls
        for (int cell = 0; cell < nullsNeededToBeAdded; cell++) {
          pyramidArray.get(row).add(null);
        }

      }


    }

    // add the cards that overlap, this is the easy part
    for (int x = incompleteRows; x < numRows; x++) {
      for (int z = 0; z < firstRowCards + x; z++) {
        pyramidArray.get(x).add(stockList.get(0));
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
    super.remove(row1, card1, row2, card2);
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

    if (gameStatus == GameStatus.NOTSTARTED) {
      throw new IllegalStateException("Game has not been started yet.");
    }
    // if row is greater than total number of rows, or less than 0, throw exception
    if (row >= getNumRows() || row < 0) {
      throw new IllegalArgumentException("Not a valid row");
    }

    // row width can be determined by row number

    // if number of rows is even
    if (getNumRows() % 2 == 0) {
      return row + getNumRows() + 1;
    } else {
      return row + getNumRows();
    }
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
    return super.isGameOver();
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


}
