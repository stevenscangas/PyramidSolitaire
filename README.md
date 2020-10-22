# cs3500.pyramidsolitaire.PyramidSolitaire
project for object oriented design class

Please run this program through the main class in OODHW3/src/cs3500/PyramidSolitaire (this is the main class)

There are multiple possible run configurations (command line arguments) the user can use:
"basic" (default option if none selected)
"multipyramid": creates 3 pyramids combined to create a bigger, overlapping triple pyramid
"relaxed": allows the user to remove cards in pairs if one of them covers the other directly and is the only card that covers it

IF the user has input one of these arguments, they can also input the number of rows & number of draw cards.
These MUST be BOTH entered if you want to add one.
Default pyramid is: basic 7 3
Note: if you choose a size that uses more cards than there are in the deck, the game cannot start.


The rules for pyramid solitaire can be found online, please look them up before playing or the game will make no sense.
Basically there are 5 commands for the user to interact with the game.
All coordinates are 1 indexed:

-----------------
RM1: Remove a single card from the pyramid (since it is one card it has to be a King since King value is 13)
rm1 (row) (card)
example: rm1 7 2    would remove the 2nd card of the 7th row if it is equal to 13 (if its a king)
-----------------
RM2: Remove two cards from pyramid that values add up to 13
rn2 (row 1) (card 1) (row 2) (card 2)
example: rm2 7 5 7 3   would check if the cards at 7 5 and 7 3 are equal to 13 and then remove them if so
-----------------
RMWD: Pair up a card in the pyramid with one in the draw pile
rmwd (draw index) (row) (card)
example: rmwd 1 5 2 would remove the first draw card and the 2nd card in the 5th row if they are equal to 13
-----------------
DD: Discard a card from the draw pile and replace it with 
dd: (draw index)
example: dd 1 would replace the first card in the draw with the fist card left in the deck
-----------------
Q/q : Quit the game
If a Q/q is seen anywhere by itself (separated by spaces) the game will be quit.
-----------------

Game is won when all cards are removed from pyramid.
Game is over when there are no possible moves left.
Score is equal to sum of values of all cards in the pyramid.

Good luck and have fun :)
