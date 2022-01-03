package com.blackjack;

// Import all java utilities
 import java.util.*;

public class Blackjack {

    public static void main(String[] args) {
        // new Scanner input
        Scanner input = new Scanner(System.in);
        // Defining the overall program loop variable
        char playAgain = 'n';
        // Overall program do-while loop
        // Given the tag "overall" so it can be referenced inside another loop
        overall: do {
            // Create new objects of type Deck, Computer, and Player
            Deck deck = new Deck();
            Dealer computer = new Dealer();
            Player player = new Player();
            // Print and shuffle deck
            System.out.println("Blackjack\n**********\nWelcome to BlackJack!\nShuffling deck...");
            deck.shuffle();
            // Deal 2 cards to player
            System.out.println("Dealing cards...");
            player.deal(2, deck);
            // Print player cards
            System.out.println("You were dealt:");
            player.printCards();
            // For loop to check for aces
            for (int i = 0; i < player.playerCards.size(); i++) {
                if (player.playerCards.get(i).getNumber() == "Ace") {
                    // If ace is found, prompt user for decision and if 11 is the input, then set the ace points to 11
                    System.out.println("You have an Ace, and your current point total is " + player.getPlayerPoints() + ". Would you like your Ace's point value to be 1 or 11?");
                    if (Integer.parseInt(input.nextLine()) == 11) {
                        player.playerCards.get(i).setAceEleven(player);
                    }
                }
            }
            // Reused code to check if player won
            if (player.getPlayerPoints() == 21) {
                System.out.println("You have 21 points. You win!\nCongtratulations!");
                System.out.println("Would you like to play again?");
                // Update playAgain
                playAgain = input.nextLine().charAt(0);
                // Go to the end of the do-while loop and decide action based on playAgain
                continue overall;
            }
            // Reused code to check if player lost
            if (player.getPlayerPoints() > 21) {
                System.out.println("You have " + player.getPlayerPoints() + " points.\nGame over.");
                System.out.println("Would you like to play again?");
                playAgain = input.nextLine().charAt(0);
                // If player lost final time, then print "Better luck next time!"
                if (playAgain != 'y' && playAgain != 'Y') {
                    System.out.println(":( Better luck next time!");
                }
                continue overall;
            }
            // Prompt user whether or not to hit
            System.out.println("You have " + player.getPlayerPoints() + " points. Would you like to hit? (Y = yes, N = no)");
            char hitFirst = input.nextLine().charAt(0);
            char continueLoop = 'y';
            // Two separate action variables so that it activates hit loop only if player chooses hit the first time
            if (hitFirst == 'Y' || hitFirst == 'y') {
                while (continueLoop == 'y' || continueLoop == 'Y') {
                    // Deal player one card (aka 'hit')
                    player.deal(1, deck);
                    // Print your current deck
                    System.out.println("You now have:");
                    player.printCards();
                    // Same code as before to check for aces
                    if (player.playerCards.get(0).getNumber() == "Ace") {
                        System.out.println("You have an Ace, and your current point total is " + player.getPlayerPoints() + ". Would you like your Ace's point value to be 1 or 11?");
                        if (Integer.parseInt(input.nextLine()) == 11) {
                            player.playerCards.get(0).setAceEleven(player);
                        }
                    }
                    // Same code as before to check if player won
                    if (player.getPlayerPoints() == 21) {
                        System.out.println("You have 21 points. You win!\nCongtratulations!");
                        System.out.println("Would you like to play again?");
                        playAgain = input.nextLine().charAt(0);
                        continue overall;
                    }
                    // Same code as before to check if player lost
                    if (player.getPlayerPoints() > 21) {
                        System.out.println("You have " + player.getPlayerPoints() + " points.\nGame over.");
                        System.out.println("Would you like to play again?");
                        playAgain = input.nextLine().charAt(0);
                        if (playAgain != 'y' && playAgain != 'Y') {
                            System.out.println("Better luck next time!");
                        }
                        continue overall;
                    }
                    // End hit loop
                    System.out.println("You have " + player.getPlayerPoints() + " points. Would you like to hit again? (Y = yes, N = no)");
                    continueLoop = input.nextLine().charAt(0);
                }

            }
            // Checks to see if user has ended their turn
            if (hitFirst != 'y' || hitFirst != 'Y' || continueLoop != 'y' || continueLoop != 'Y') {
                // Deal computer cards
                computer.deal(2, deck);
                // Keep hitting until point value is greater than or equal to 17
                while (computer.getDealerPoints() < 17) {
                    computer.deal(1, deck);
                    // Check if computer won
                    if (computer.getDealerPoints() == 21) {
                        // Print computer hand and lose screen
                        System.out.println("The computer's current hand is: ");
                        computer.printCards();
                        System.out.println("The computer has 21 points.\n Game over.");
                        System.out.println("Would you like to play again?");
                        playAgain = input.nextLine().charAt(0);
                        if (playAgain != 'y' && playAgain != 'Y') {
                            System.out.println("Better luck next time!");
                        }
                        continue overall;
                    }
                    // Check of computer lost
                    if (computer.getDealerPoints() > 21) {
                        // Print computer hand and total points, and win screen
                        System.out.println("The computer's current hand is: ");
                        computer.printCards();
                        System.out.println("The computer has " + computer.getDealerPoints() + " points.\nCongratulations! You win!");
                        System.out.println("Would you like to play again?");
                        playAgain = input.nextLine().charAt(0);
                        continue overall;
                    }

                }
                // At this point the game actions are over, now we evaluate who won
                // Print computer hand
                System.out.println("The computer's current hand is: ");
                computer.printCards();
                // Print both point values
                System.out.println("You have " + player.getPlayerPoints() + " points.");
                System.out.println("The computer has " + computer.getDealerPoints() + " points.");
                //If computer won, print lose screen and how much player lost by
                if (player.getPlayerPoints() < computer.getDealerPoints()) {
                    int loseDiff = computer.getDealerPoints() - player.getPlayerPoints();
                    System.out.println("The computer wins with " + loseDiff + " more points than you.\nGame over.");
                    System.out.println("Would you like to play again?");
                    playAgain = input.nextLine().charAt(0);
                    if (playAgain != 'y' && playAgain != 'Y') {
                        System.out.println("Better luck next time!");
                    }
                    continue overall;
                }
                // If player won, print win screen and how much player won by
                else {
                    int winDiff;
                    winDiff = player.getPlayerPoints() - computer.getDealerPoints();
                    System.out.println("You win with " + winDiff + " more points than the computer.\nCongratulations!");
                    System.out.println("Would you like to play again?");
                    playAgain = input.nextLine().charAt(0);
                    continue overall;
                }
            }
            // Update overall continueLoop variable for do-while loop
            System.out.println("Would you like to play again?");
            playAgain = input.nextLine().charAt(0);
        } while (playAgain == 'y' || playAgain == 'Y');
    }
}


//Class for dealer
class Dealer {
    // New int dealerPoints
    private int dealerPoints;
    // Getter method for player points
    public int getDealerPoints() {
        return this.dealerPoints;
    }
    // New ArrayList dealerCards
    public ArrayList<Cards> dealerCards = new ArrayList<Cards>();

    // Dealer constructor
    public Dealer() {

    }

    // Method to deal cards to dealer
    public void deal(int numberToDeal, Deck c) {
        for (int i = 0; i < numberToDeal; i++) {
            // Add deck cards to dealer hand
            dealerCards.add(i, c.deck.get(c.topIndex));
            // Update current top card in deck
            c.topIndex++;
            // Set dealt cards to faceUp
            dealerCards.get(i).faceUp();
            // Update dealer points
            dealerPoints += dealerCards.get(i).getPoints();
        }
    }
    // Print dealer cards
    public void printCards() {
        for (int i = 0; i < dealerCards.size(); i++) {
            System.out.println(dealerCards.get(i).getNumber() + " of " + dealerCards.get(i).getSuit());
        }
    }
}

//Class for player
class Player {
    // New int playerPoints
    public int playerPoints;
    // Getter method for player points
    public int getPlayerPoints() {
        return this.playerPoints;
    }
    // New ArrayList playerCards
    public ArrayList<Cards> playerCards = new ArrayList<Cards>();

    // Player constructor
    public Player() {

    }

    // Method to deal cards to player
    public void deal(int numberToDeal, Deck c) {
        for (int i = 0; i < numberToDeal; i++) {
            // Add deck's top cards to player hand
            playerCards.add(i, c.deck.get(c.topIndex));
            // Update deck top index
            c.topIndex++;
            // Make dealt cards faceUp
            playerCards.get(i).faceUp();
            // Update playerPoints
            playerPoints += playerCards.get(i).getPoints();
        }
    }
    // Print player cards
    public void printCards() {
        for (int i = 0; i < playerCards.size(); i++) {
            System.out.println(playerCards.get(i).getNumber() + " of " + playerCards.get(i).getSuit());
        }
    }
}

//Class for deck
class Deck {
    // New card arraylist deck
    ArrayList<Cards> deck = new ArrayList<Cards>();
    // ArrayList index counter, only used within this class
    private int currentIndex = 0;
    // Static integer for index of the top card
    public int topIndex = 0;

    // Constructor for new deck
    public Deck() {
        // Initializing variables with null to prevent error
        String currentNumber = null;
        String currentSuit = null;
        // Nested for loop that runs 52 times to initialize every Card object in a new deck
        for (int n = 0; n < 13; n++) {
            for (int s = 0; s < 4; s++) {
                // Switch to assign card numbers
                switch (n) {
                    case 0:
                        currentNumber = "Ace";
                        break;
                    case 1:
                        currentNumber = "Two";
                        break;
                    case 2:
                        currentNumber = "Three";
                        break;
                    case 3:
                        currentNumber = "Four";
                        break;
                    case 4:
                        currentNumber = "Five";
                        break;
                    case 5:
                        currentNumber = "Six";
                        break;
                    case 6:
                        currentNumber = "Seven";
                        break;
                    case 7:
                        currentNumber = "Eight";
                        break;
                    case 8:
                        currentNumber = "Nine";
                        break;
                    case 9:
                        currentNumber = "Ten";
                        break;
                    case 10:
                        currentNumber = "Jack";
                        break;
                    case 11:
                        currentNumber = "Queen";
                        break;
                    case 12:
                        currentNumber = "King";
                        break;
                }
                // Switch to assign card suites
                switch (s) {
                    case 0:
                        currentSuit = "Clubs";
                        break;
                    case 1:
                        currentSuit = "Diamonds";
                        break;
                    case 2:
                        currentSuit = "Hearts";
                        break;
                    case 3:
                        currentSuit = "Spades";
                        break;
                }
                // Add object Cards 52 total times
                deck.add(currentIndex, new Cards(currentNumber, currentSuit));
                // Update local ArrayList counter
                currentIndex++;
            }
        }
    }

    // Method to shuffle a deck. Information on how to use Random class obtained
    // from JournalDev.com
    public void shuffle() {
        // New 'random' object
        Random rand = new Random();
        // Switch each element with a random index
        for (int i = 0; i < deck.size(); i++) {
            // Creates Random of only necessary size
            int randomIndexToSwap = rand.nextInt(deck.size());
            // Temporary Cards bject to store Cards during the switch
            Cards temp = deck.get(randomIndexToSwap);
            // Assign the current Card to the random index
            deck.set(randomIndexToSwap, deck.get(i));
            // Set the temporary placeholder to current index
            deck.set(i, temp);
        }
    }
}

// Class for individual cards
class Cards {
    // Card attributes
    private String number;
    private String suit;
    private int points;
    private String orientation;

    // Getter methods for suit and number
    public String getNumber() {
        return this.number;
    }
    public String getSuit() {
        return this.suit;
    }

    // If user specifies, ace value becomes 11 and playerPoints is updated
    public void setAceEleven(Player p) {
        this.points = 11;
        p.playerPoints += 10;
    }

    // Getter method for points
    public int getPoints() {
        return this.points;
    }

    // Method to set orientation to face up
    public void faceUp() {
        this.orientation = "face up";
    }

    // Create a 'card' object with given number and suit
    public Cards(String n, String s) {
        // Assign passed values to each Card
        number = n;
        suit = s;
        orientation = "face down";
        // Switch to assign card point values
        switch (n) {
            case "Ace":
                points = 1;
                break;
            case "Two":
                points = 2;
                break;
            case "Three":
                points = 3;
                break;
            case "Four":
                points = 4;
                break;
            case "Five":
                points = 5;
                break;
            case "Six":
                points = 6;
                break;
            case "Seven":
                points = 7;
                break;
            case "Eight":
                points = 8;
                break;
            case "Nine":
                points = 9;
                break;
            case "Ten":
                points = 10;
                break;
            case "Jack":
                points = 10;
                break;
            case "Queen":
                points = 10;
                break;
            case "King":
                points = 10;
                break;
        }
    }

    // Method to print Card attributes if Card is face up
    public static void printCard(Cards c) {
        if (c.orientation == "face up") {
            System.out.println(c.number + " of " + c.suit);
        }
        else
            System.out.println("This card is face down");
    }
}