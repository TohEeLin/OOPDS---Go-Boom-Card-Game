import java.util.*;

public class CardGame {

    private static final int num_players = 4;
    private static final String[] suits = { "c", "d", "h", "s" };
    private static final List<String> ranks = Arrays.asList(
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");

    private List<Card> deck;
    private List<Card>[] hands;
    private int currentPlayer;
    private List<Card> centerCard;
    private static Scanner input = new Scanner(System.in);
    private int trickNumber = 1;
    private int[] trickPoint = { 0, 0, 0, 0, 0 };

    int gst = 0; // game state, 1 = player 1 win, 2 = player 2 win, 3 = player 3 win, 4 = player
                 // 4 win
    int turns = 0; // state how many turns has happened

    // instantiate players
    Player[] players = new Player[5];
    {
        players[1] = new Player(1, 0);
        players[2] = new Player(2, 0);
        players[3] = new Player(3, 0);
        players[4] = new Player(4, 0);
    }

    private int getNextPlayer() {
        // Increment the current player index
        currentPlayer++;

        // If the current player index exceeds the number of players, wrap around to
        // player 1
        if (currentPlayer > num_players) {
            currentPlayer = 1;
        }

        return currentPlayer;
    }

    public CardGame() {
        deck = new ArrayList<>();
        centerCard = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
        System.out.println("\nShuffled Deck: " + deck);

        hands = new ArrayList[num_players + 1];
        for (int i = 1; i <= num_players; i++) {
            hands[i] = new ArrayList<>();
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < num_players + 1; j++) {
                hands[j].add(deck.remove(0));
            }
        }
    }

    // main game
    public int startGame() {
        System.out.println("\nStarting new game...");
        System.out.println("Dealing cards...");
        center();

        while (gst != 1 && gst != 2 && gst != 3 && gst != 4 && gst != 5) {
            turns = 0;
            if (trickNumber > 1) {
                centerCard = new ArrayList<>();
            }
            // refresh trick point after each trick
            for (int i = 1; i <= 4; i++) {
                trickPoint[i] = 1;
            }

            do {
                display();
                command();
            } while (gst != 1 && gst != 2 && gst != 3 && gst != 4 && gst != 5 && turns < 4);

            if (gst != 1 && gst != 2 && gst != 3 && gst != 4 && gst != 5) {
                nextLead();
                System.out.println("*** Player" + currentPlayer + " wins Trick  #" + trickNumber);
                trickNumber++;
            }
        }

        return gst;
    }

    // display console
    public void display() {
        System.out.println();
        System.out.println("Trick #" + trickNumber);
        for (int i = 1; i <= num_players; i++) {
            int playerNumber = players[i].getPlayerNumber();
            System.out.println("Player " + playerNumber + ": " + hands[i]);
        }

        System.out.println("Center  : " + centerCard);
        System.out.println("Deck    : " + deck);

        System.out.println("Score: Player1 = " + players[1].getScore() + " | Player2 = " + players[2].getScore()
                + " | Player3 = " + players[3].getScore() + " | Player4 = " + players[4].getScore());
        System.out.println("Turn : Player" + currentPlayer);
    }

    // deal and check center card determine lead player
    public void center() {
        centerCard.add(deck.remove(0));
        String rank = centerCard.get(0).getRank();

        if (rank.equals("A") || rank.equals("5") || rank.equals("9") || rank.equals("K")) {
            currentPlayer = 1;
        } else if (rank.equals("2") || rank.equals("6") || rank.equals("10")) {
            currentPlayer = 2;
        } else if (rank.equals("3") || rank.equals("7") || rank.equals("J")) {
            currentPlayer = 3;
        } else if (rank.equals("4") || rank.equals("8") || rank.equals("Q")) {
            currentPlayer = 4;
        }
    }

    // enter command
    public void command() {
        System.out.print("> ");
        String cmd = input.nextLine();

        switch (cmd) {
            case "s":
                gst = 5;
                break;

            case "x":
                System.out.println("Exiting the game...");
                System.exit(0);
                break;

            case "d":
                draw();
                break;

            default:
                if (cmd.length() == 2 || cmd.length() == 3) {
                    play(cmd);
                } else {
                    System.out.println("This is an invalid command!!");
                }
                break;
        }
    }

    // player enters card
    public void play(String card) {
        // Get the rank and suit of the card played by the current player
        String playedRank = card.substring(1);
        String playedSuit = card.substring(0, 1);

        // Get the rank and suit of the lead card
        String leadRank = "";
        String leadSuit = "";
        if (centerCard.size() != 0) {
            leadRank = centerCard.get(0).getRank();
            leadSuit = centerCard.get(0).getSuit();
        } else {
            leadRank = playedRank;
            leadSuit = playedSuit;
        }

        // Check if the played card follows the suit or rank of the lead card
        if (playedSuit.equals(leadSuit) || playedRank.equals(leadRank)) {
            // Find the card in the player's hand
            Card playedCard = null;
            for (Card playerCard : hands[currentPlayer]) {
                if (playerCard.getRank().equals(playedRank) && playerCard.getSuit().equals(playedSuit)) {
                    playedCard = playerCard;
                    break;
                }

                else {
                    playedCard = null;
                }
            }

            if (playedCard != null) {
                // Remove the card from the player's hand
                hands[currentPlayer].remove(playedCard);
                centerCard.add(playedCard);
                calculatePoints(playedCard);
                turns++;
                // check number of cards, if 0 set win state
                if (hands[currentPlayer].size() == 0) {
                    gst = currentPlayer;
                }
                // Switch to the next player
                currentPlayer = getNextPlayer();
            }

            else {
                System.out.println("Invalid card! Please play a card from your hand.");
            }
        }

        else {
            System.out.println("Invalid card! Please play a card following the lead card.");
        }
    }

    // player enters draw
    public void draw() {
        int flag = 1;
        // Get the rank and suit of the lead card
        String leadRank = centerCard.get(0).getRank();
        String leadSuit = centerCard.get(0).getSuit();

        for (Card currentPlayerCards : hands[currentPlayer]) {
            if (currentPlayerCards.getRank().equals(leadRank) || currentPlayerCards.getSuit().equals(leadSuit)) {
                System.out.println("Player can play cards at hand");
                flag = 0;
                break;
            }
        }

        if (deck.isEmpty()) {
            System.out.println("Deck is empty!! Skip to next player.");
            flag = 0;
            currentPlayer = getNextPlayer();
        }

        if (flag == 1) {
            hands[currentPlayer].add(deck.get(0));
            deck.remove(0);
        }
    }

    // calculate "points" of current player after card is played
    public void calculatePoints(Card playedCard) {
        int point = 1;
        String leadSuit = "";
        String card = playedCard.toString();
        String playedRank = card.substring(1);
        String playedSuit = card.substring(0, 1);

        if (turns == 0 && trickNumber != 1) {
            leadSuit = playedSuit;
        } else {
            leadSuit = centerCard.get(0).getSuit();
        }

        if (playedSuit.equals(leadSuit)) {
            if (playedRank.equals("2")) {
                point += 2;
            }
            if (playedRank.equals("3")) {
                point += 3;
            }
            if (playedRank.equals("4")) {
                point += 4;
            }
            if (playedRank.equals("5")) {
                point += 5;
            }
            if (playedRank.equals("6")) {
                point += 6;
            }
            if (playedRank.equals("7")) {
                point += 7;
            }
            if (playedRank.equals("8")) {
                point += 8;
            }
            if (playedRank.equals("9")) {
                point += 9;
            }
            if (playedRank.equals("10")) {
                point += 10;
            }
            if (playedRank.equals("J")) {
                point += 11;
            }
            if (playedRank.equals("Q")) {
                point += 12;
            }
            if (playedRank.equals("K")) {
                point += 13;
            }
            if (playedRank.equals("A")) {
                point += 14;
            }
        }

        trickPoint[currentPlayer] = point;
    }

    // determine next lead player by comparing points in trickPoint[]
    public void nextLead() {
        int greatest = 0;
        int lead = 0;

        for (int i = 1; i <= 4; i++) {
            if (trickPoint[i] > greatest) {
                greatest = trickPoint[i];
                lead = i;
            }
        }
        currentPlayer = lead;
    }
}