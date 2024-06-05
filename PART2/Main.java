public class Main {
    public static void main(String[] a) {
        int state = 0;

        while (state == 0) {
            CardGame g = new CardGame();
            state = g.startGame();
        }
    }
}