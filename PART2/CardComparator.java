import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
    @Override
    public int compare(Card a, Card b) {
        int thisCardValue = a.getCardValue();
        int otherCardValue = b.getCardValue();

        if (thisCardValue == otherCardValue) {
            // If the card values are the same, compare the suits
            String aSuit = a.getSuit();
            String bSuit = b.getSuit();

            return aSuit.compareTo(bSuit);
        } else {
            // Compare the card values
            return Integer.compare(thisCardValue, otherCardValue);
        }
    }
}
