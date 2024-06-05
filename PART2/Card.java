public class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String toString() {
        return suit + rank;
    }

    public int getCardValue() {
        String s = this.getSuit();
        int cardValue = 0;
        // diamond
        if (s.equals("d")) {
            cardValue = 0;
        }
        // clubs
        else if (s.equals("c")) {
            cardValue = 100;
        }
        // hearts
        else if (s.equals("h")) {
            cardValue = 200;
        }
        // spades
        else if (s.equals("s")) {
            cardValue = 300;
        }

        s = this.getRank();
        int value = Character.getNumericValue(s.charAt(0));

        if (value >= 2 && value <= 9) {
            cardValue += value;
        } else if (value == 1) {
            cardValue += 10;
        } else if (s.equals("J")) {
            cardValue += 11;
        } else if (s.equals("Q")) {
            cardValue += 12;
        } else if (s.equals("K")) {
            cardValue += 13;
        } else if (s.equals("A")) {
            cardValue += 14;
        }

        return cardValue;
    }
}