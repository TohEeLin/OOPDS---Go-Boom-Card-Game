public class Main {
    public static void main(String[] a) {
        int mst = 0;

        do {
            CardGame g = new CardGame();
            mst = g.startGame();
        } while (mst != 1 || mst != 2 || mst != 3 || mst != 4);

        switch (mst) {
            case 1:
                System.out.println("Player1 won the game.");
                break;
            case 2:
                System.out.println("Player2 won the game.");
                break;
            case 3:
                System.out.println("Player3 won the game.");
                break;
            case 4:
                System.out.println("Player4 won the game.");
                break;
        }
    }
}