package cs3500.pyramidsolitaire.model.hw02;

import java.util.Objects;

// class to represent a card in the cs3500.pyramidsolitaire.PyramidSolitaire game

/**
 * A class to represent a Card in the game. value: Integer to represent value of card. 1-13 if
 * playing cards suit: String to represent suit of card. Clubs, Hearts, Diamonds, or Spades
 */
public class Card {

  // private final since card values and suit should never change
  private final Integer value; // value of card 1-13
  private final String suit; // suit of card: spades, clubs, diamonds, or hearts

  // main constructor takes in value and suit
  // throws exception if card value is not between 1-13
  // throw exception if suit is not one of the 4 card suits

  /**
   * A constructor that returns a card given its value and suit.
   *
   * @param value Integer to represent value of card. 1-13 if playing cards
   * @param suit  String to represent suit of card. Clubs, Hearts, Diamonds, or Spades
   */

  public Card(int value, String suit) {
    if (value < 1 || value > 13) {
      throw new IllegalArgumentException("Value of card must be between 1 and 13.");
    }
    if (!suit.equals("clubs") && !suit.equals("spades") && !suit.equals("diamonds") && !suit.equals(
        "hearts")) {
      throw new IllegalArgumentException("Suit must be one of: "
          + "spades, diamonds, hearts, or clubs");
    }

    this.value = value;
    this.suit = suit;
  }

  // this method returns a string with the suit of the card shown as a character representing it
  // and with the value showing as a letter if value is face card value (Ace, Jack, Queen, King)
  // A (1), 2, 3, 4, 5, 6, 7, 8, 9, 10, J (11), Q (12), K (13)

  /**
   * This method converts the card into a String. The display value becomes an 'A' 'J' 'Q' 'K' for
   * the values of 1,11,12,13 respectively. The suits are replaced with the character that
   * represents each suit on a playing card.
   *
   * @return String that represents a playing card being displayed
   */
  @Override
  public String toString() {

    final String displayValue;
    final String displaySuit;

    // this switch statement determines what the displayValue will look like
    switch (this.value) {
      case 1:
        //ace
        displayValue = "A";
        break;
      case 11:
        //jack
        displayValue = "J";
        break;
      case 12:
        //queen
        displayValue = "Q";
        break;
      case 13:
        //king
        displayValue = "K";
        break;
      default:
        // just display the integer value since that's how it would appear on a card
        displayValue = Integer.toString(value);
    }

    // this switch statement determines what the displayValue will look like
    switch (this.suit) {
      case "hearts":
        displaySuit = "♥";
        break;
      case "diamonds":
        displaySuit = "♦";
        break;
      case "spades":
        displaySuit = "♠";
        break;
      case "clubs":
        displaySuit = "♣";
        break;
      default:
        // throw exception if suit isn't any of the above
        throw new IllegalArgumentException("Suit of card was not one of the only 4 "
            + "possible suit options: Spades, Diamonds, Hearts, Clubs");
    }

    // return the value of the card combined with the suit afterwards
    // Examples: A♠, J♦, 5♥
    return displayValue.concat(displaySuit);

  }

  // determines whether or not this card is equal to that card (same card)

  /**
   * This method determines if this Card is equal to an input object.
   *
   * @param that Another object to compare this card with.
   * @return Boolean that tells whether or not object and card are identical.
   */
  @Override
  public boolean equals(Object that) {

    // pointer equality check
    if (this == that) {
      return true;
    }

    // if that is not an object of the same class:
    if (!(that instanceof Card)) {
      return false;
    }
    // now we are allowed to cast the type Card to check equality
    return ((Card) that).value.equals(this.value) && ((Card) that).suit.equals(this.suit);
  }

  // returns hashcode of this card

  /**
   * Determines the hashcode of this card.
   *
   * @return Returns hashcode of this card depending on its value, suit.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.value, this.suit);
  }

  // returns value of this card
  public int getValue() {
    return value;
  }

  // returns value of this card
  public String getSuit() {
    return suit;
  }

}
