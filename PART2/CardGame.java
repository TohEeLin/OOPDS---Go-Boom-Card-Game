import java.util.*;
import java.io.*;

public class CardGame {

    private static final int num_players = 4;
    private static final String[] suits = { "c", "d", "h", "s" };
    private static final List<String> ranks = Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q",
            "K");

    private List<Card> deck;
    private Set<Card>[] hands;
    private int currentPlayer;
    private List<Card> centerCard;
    private Player[] players;
    private static Scanner input = new Scanner(System.in);
    private int trickNumber = 1;
    private ArrayList<Map<Card, Integer>> playedCCards; // stores played cards and its respective 'point' during the turn

    int gst = 0; // game state, 1 = player 1 win, 2 = player 2 win, 3 = player 3 win, 4 = player 4 win
    int turns = 0; // state how many turns has happened

    private int getNextPlayer() {
        // Increment the current player index
        currentPlayer++;

        // If the current player index exceeds the number of players, wrap around to player 1
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
        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Shuffled Deck: " + deck);
        System.out.println("c = club");
        System.out.println("d = diamond");
        System.out.println("h = heart");
        System.out.println("s = spade");
        System.out.println("\n---< Game Commands >---");
        System.out.println("s ==> Start a new game / Reset the game");
        System.out.println("x ==> Exit the game");
        System.out.println("d ==> Draw cards from deck");
        System.out.println("save ==> Save the game");
        System.out.println("load ==> Load the game");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------\n");

        hands = new TreeSet[num_players + 1];
        for (int i = 1; i <= num_players; i++) {
            hands[i] = new TreeSet<>(new CardComparator());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < num_players + 1; j++) {
                hands[j].add(deck.remove(0));
            }
        }

        players = new Player[5];
        for (int i = 1; i <= num_players; i++) {
            players[i] = new Player(i, 0);
        }

        // ArrayList of Maps to store played cards
        playedCCards = new ArrayList<Map<Card, Integer>>();
        for (int i = 0; i <= num_players; i++) {
            playedCCards.add(new LinkedHashMap<Card, Integer>());
        }
    }

    public void calculateScore() {
        int[] score = new int[num_players + 1];

        // Calculate the scores for each player based on the cards remaining in their hand
        for (int i = 1; i <= num_players; i++) {
            for (Card card : hands[i]) {
                String rank = card.getRank();
                int value = 0;

                if (rank.equals("A")) {
                    value = 1;
                } else if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) {
                    value = 10;
                } else {
                    value = Integer.parseInt(rank);
                }

                score[i] = score[i] + value;
            }

            players[i].setScore(score[i]);
        }
    }

    public void redistribute() {
        deck = new ArrayList<>();
        centerCard = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(new Card(suit, rank));
            }
        }
        Collections.shuffle(deck);
        System.out.println("Shuffled Deck: " + deck);
        System.out.println("c = club");
        System.out.println("d = diamond");
        System.out.println("h = heart");
        System.out.println("s = spade");

        hands = new TreeSet[num_players + 1];
        for (int i = 1; i <= num_players; i++) {
            hands[i] = new TreeSet<>(new CardComparator());
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 1; j < num_players + 1; j++) {
                hands[j].add(deck.remove(0));
            }
        }

        // ArrayList of Maps to store played cards
        playedCCards = new ArrayList<Map<Card, Integer>>();
        for (int i = 0; i <= num_players; i++) {
            playedCCards.add(new LinkedHashMap<Card, Integer>());
        }

        currentPlayer = gst;
        gst = 0;
        turns = 0;
    }

    // main game
    public int startGame() {
        System.out.println("Starting new game...");
        System.out.println("Dealing cards...");
        center();
        int win = 0;

        while (win == 0) {
            turns = 0;
            if (trickNumber > 1) {
                centerCard.clear();
            }

            do {
                display();
                command();
            } while (gst != 1 && gst != 2 && gst != 3 && gst != 4 && gst != 5 && turns < 4);

            if (gst == 5) {
                return 0;
            }

            if (gst != 1 && gst != 2 && gst != 3 && gst != 4) {
                nextLead();
                System.out.println("*** Player" + currentPlayer + " wins Trick  #" + trickNumber);
                trickNumber++;
            } else {
                calculateScore();
                System.out.println("*** Player" + gst
                        + " has used up all his cards and will start first after card redistribution!!");
                System.out.println("Calculating Scores...");
                System.out.println("Redistributing Cards...");
                System.out.println();
                redistribute();
            }
        }

        return win;
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
                System.out.println("Restarting game...");
                System.out.println();
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
                } else if (cmd.equals("save")) {
                    saveGame();
                } else if (cmd.equals("load")) {
                    loadGame();
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

        if (centerCard.size() != 0) { // if first round of first trick
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
                } else {
                    playedCard = null;
                }
            }

            if (playedCard != null) {
                // Remove the card from the player's hand
                hands[currentPlayer].remove(playedCard);
                centerCard.add(playedCard);
                int points = calculatePoints(playedCard, leadSuit);
                playedCCards.get(currentPlayer).put(playedCard, points);

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

    // player enters draw, if return false means no card drawn or skip, if return true means card drawn successfully
    public boolean draw() {
        // Get the rank and suit of the lead card
        String leadRank = centerCard.get(0).getRank();
        String leadSuit = centerCard.get(0).getSuit();

        for (Card currentPlayerCards : hands[currentPlayer]) {
            if (currentPlayerCards.getRank().equals(leadRank) || currentPlayerCards.getSuit().equals(leadSuit)) {
                System.out.println("Player can play cards at hand");
                return false;
            }
        }

        if (deck.isEmpty()) {
            System.out.println("Deck is empty!! Player does not have playable cards. Skip to next player.");
            currentPlayer = getNextPlayer();

            // store "N0" card to playedCCards if player is skipped
            Card c = new Card("N", "0");
            playedCCards.get(currentPlayer).put(c, 0);
            turns++;

            return false;
        }

        // draw card
        hands[currentPlayer].add(deck.get(0));
        Card c = deck.remove(0);
        System.out.println("Player " + currentPlayer + " gets card " + c.toString() + ".");

        return true;
    }

    // calculate "points" of current player after card is played
    public Integer calculatePoints(Card playedCard, String leadSuit) {
        int point = 1;
        String playedRank = playedCard.getRank();
        String playedSuit = playedCard.getSuit();
        int r = Character.getNumericValue(playedRank.charAt(0));

        if (turns == 0 && trickNumber != 1) {
            leadSuit = playedSuit;
        } else {
            leadSuit = centerCard.get(0).getSuit();
        }

        if (playedSuit.equals(leadSuit)) {
            if (r >= 2 && r <= 9) {
                point = r;
            } else if (playedRank.equals("10")) {
                point = 10;
            } else if (playedRank.equals("J")) {
                point = 11;
            } else if (playedRank.equals("Q")) {
                point = 12;
            } else if (playedRank.equals("K")) {
                point = 13;
            } else if (playedRank.equals("A")) {
                point = 14;
            }
        }

        return point;
    }

    // determine next lead player by comparing points in trickPoint[]
    public void nextLead() {
        int current = 0;
        int greatest = 0;
        int lead = 0;

        for (int i = 1; i <= 4; i++) {
            for (Map.Entry<Card, Integer> entry : playedCCards.get(i).entrySet()) {
                current = entry.getValue();
            }

            if (current > greatest) {
                greatest = current;
                lead = i;
            }
        }

        currentPlayer = lead;
    }

    // save game
    public void saveGame() {
        System.out.println("Enter file name to save: ");
        String fileName = input.nextLine();

        try (FileWriter fileWriter = new FileWriter(fileName);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            // save current player
            bufferedWriter.write("Current Player = " + currentPlayer);
            bufferedWriter.newLine();

            // save trick number
            bufferedWriter.write("Trick # = " + trickNumber);
            bufferedWriter.newLine();

            // save turns
            bufferedWriter.write("Turns = " + turns);
            bufferedWriter.newLine();

            // save deck
            bufferedWriter.write("Deck = ");
            for (Card card : deck) {
                bufferedWriter.write(card.toString() + ", ");
            }
            bufferedWriter.newLine();

            // save hands
            for (int i = 1; i <= num_players; i++) {
                bufferedWriter.write("Hand " + i + " = ");
                for (Card card : hands[i]) {
                    bufferedWriter.write(card.toString() + ", ");
                }
                bufferedWriter.newLine();
            }

            // save center
            bufferedWriter.write("Center Card = ");
            for (Card card : centerCard) {
                bufferedWriter.write(card.toString() + ", ");
            }
            bufferedWriter.newLine();

            // save playedCCards
            bufferedWriter.write("PlayedCCards = ");
            for (Map<Card, Integer> playerCards : playedCCards) {
                for (Map.Entry<Card, Integer> entry : playerCards.entrySet()) {
                    String cardString = entry.getKey() + "=" + entry.getValue();
                    bufferedWriter.write(cardString + ", ");
                }
                bufferedWriter.write("; ");
            }
            bufferedWriter.newLine();

            // save scores
            for (int i = 1; i <= num_players; i++) {
                bufferedWriter.write("Score " + i + " = " + players[i].getScore());
                bufferedWriter.newLine();
            }

            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    // load game
    public void loadGame() {
        System.out.println("Enter file name to load: ");
        String fileName = input.nextLine();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("Current Player")) {
                    currentPlayer = Integer.parseInt(line.split(" = ")[1]);
                } 
                else if (line.startsWith("Trick #")) {
                    trickNumber = Integer.parseInt(line.split(" = ")[1]);
                } 
                else if(line.startsWith("Turns")) {
                    turns = Integer.parseInt(line.split(" = ")[1]);
                }
                else if (line.startsWith("Deck")) {
                    String[] cardStrings = line.split(" = ")[1].split(", ");
                    deck.clear();
                    for (String cardString : cardStrings) {
                        String suit = cardString.substring(0, 1);
                        String rank = cardString.substring(1);
                        deck.add(new Card(suit, rank));
                    }
                } 
                else if (line.startsWith("Center Card")) {
                    String[] cardStrings = line.substring(line.indexOf('=') + 2).split(", ");
                    centerCard.clear();
                    if (cardStrings.length != 0 && !cardStrings[0].isEmpty()) {
                        for (String cardString : cardStrings) {
                            String suit = cardString.substring(0, 1);
                            String rank = cardString.substring(1);
                            centerCard.add(new Card(suit, rank));
                        }
                    }
                }
                else if (line.startsWith("PlayedCCards")) {
                    playedCCards = new ArrayList<Map<Card, Integer>>();
                    for (int i = 0; i <= num_players; i++) {
                        playedCCards.add(new LinkedHashMap<Card, Integer>());
                    }

                    String[] playedCCardsStrings = line.substring(line.indexOf('=') + 2).split("; ");
                    if (playedCCardsStrings.length != 0 && !playedCCardsStrings[0].isEmpty()) {
                        for (int i = 1; i <= num_players; i++) {
                            Map<Card, Integer> playerCards = new LinkedHashMap<Card, Integer>();
                            String[] cardStrings = playedCCardsStrings[i].split(", ");
                            for (String cardString : cardStrings) {
                                String[] cardData = cardString.split("=");

                                String cardName = cardData[0];
                                String suit = cardName.substring(0, 1);
                                String rank = cardName.substring(1);
                                Card card = new Card(suit, rank);
                                int cardValue = Integer.parseInt(cardData[1]);

                                playerCards.put(card, cardValue);
                            }
                            playedCCards.set(i, playerCards);
                        }
                    }
                }

                else if (line.startsWith("Hand")) {
                    int playerIndex = Integer.parseInt(line.split(" ")[1]);
                    String[] cardStrings = line.split(" = ")[1].split(", ");
                    hands[playerIndex].clear();
                    for (String cardString : cardStrings) {
                        String suit = cardString.substring(0, 1);
                        String rank = cardString.substring(1);
                        hands[playerIndex].add(new Card(suit, rank));
                    }
                } 
                else if (line.startsWith("Score")) {
                    int playerIndex = Integer.parseInt(line.split(" ")[1]);
                    int score = Integer.parseInt(line.split(" = ")[1]);
                    players[playerIndex].setScore(score);
                }
            }

            bufferedReader.close();
            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading the game.");
            e.printStackTrace(); // pinpoint exact line where exception occurs
        }
    }
}