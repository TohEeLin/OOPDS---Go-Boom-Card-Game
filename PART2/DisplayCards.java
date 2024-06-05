import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DisplayCards {
    ImageIcon icon = new ImageIcon("Images/cardicon.png");

    // Diamonds
    ImageIcon dA = new ImageIcon("Images/ace_of_diamonds.png");
    ImageIcon d2 = new ImageIcon("Images/2_of_diamonds.png");
    ImageIcon d3 = new ImageIcon("Images/3_of_diamonds.png");
    ImageIcon d4 = new ImageIcon("Images/4_of_diamonds.png");
    ImageIcon d5 = new ImageIcon("Images/5_of_diamonds.png");
    ImageIcon d6 = new ImageIcon("Images/6_of_diamonds.png");
    ImageIcon d7 = new ImageIcon("Images/7_of_diamonds.png");
    ImageIcon d8 = new ImageIcon("Images/8_of_diamonds.png");
    ImageIcon d9 = new ImageIcon("Images/9_of_diamonds.png");
    ImageIcon d10 = new ImageIcon("Images/10_of_diamonds.png");
    ImageIcon dJ = new ImageIcon("Images/jack_of_diamonds2.png");
    ImageIcon dQ = new ImageIcon("Images/queen_of_diamonds2.png");
    ImageIcon dK = new ImageIcon("Images/king_of_diamonds2.png");

    // Clubs
    ImageIcon cA = new ImageIcon("Images/ace_of_clubs.png");
    ImageIcon c2 = new ImageIcon("Images/2_of_clubs.png");
    ImageIcon c3 = new ImageIcon("Images/3_of_clubs.png");
    ImageIcon c4 = new ImageIcon("Images/4_of_clubs.png");
    ImageIcon c5 = new ImageIcon("Images/5_of_clubs.png");
    ImageIcon c6 = new ImageIcon("Images/6_of_clubs.png");
    ImageIcon c7 = new ImageIcon("Images/7_of_clubs.png");
    ImageIcon c8 = new ImageIcon("Images/8_of_clubs.png");
    ImageIcon c9 = new ImageIcon("Images/9_of_clubs.png");
    ImageIcon c10 = new ImageIcon("Images/10_of_clubs.png");
    ImageIcon cJ = new ImageIcon("Images/jack_of_clubs2.png");
    ImageIcon cQ = new ImageIcon("Images/queen_of_clubs2.png");
    ImageIcon cK = new ImageIcon("Images/king_of_clubs2.png");

    // Hearts
    ImageIcon hA = new ImageIcon("Images/ace_of_hearts.png");
    ImageIcon h2 = new ImageIcon("Images/2_of_hearts.png");
    ImageIcon h3 = new ImageIcon("Images/3_of_hearts.png");
    ImageIcon h4 = new ImageIcon("Images/4_of_hearts.png");
    ImageIcon h5 = new ImageIcon("Images/5_of_hearts.png");
    ImageIcon h6 = new ImageIcon("Images/6_of_hearts.png");
    ImageIcon h7 = new ImageIcon("Images/7_of_hearts.png");
    ImageIcon h8 = new ImageIcon("Images/8_of_hearts.png");
    ImageIcon h9 = new ImageIcon("Images/9_of_hearts.png");
    ImageIcon h10 = new ImageIcon("Images/10_of_hearts.png");
    ImageIcon hJ = new ImageIcon("Images/jack_of_hearts2.png");
    ImageIcon hQ = new ImageIcon("Images/queen_of_hearts2.png");
    ImageIcon hK = new ImageIcon("Images/king_of_hearts2.png");

    // Spades
    ImageIcon sA = new ImageIcon("Images/ace_of_spades.png");
    ImageIcon s2 = new ImageIcon("Images/2_of_spades.png");
    ImageIcon s3 = new ImageIcon("Images/3_of_spades.png");
    ImageIcon s4 = new ImageIcon("Images/4_of_spades.png");
    ImageIcon s5 = new ImageIcon("Images/5_of_spades.png");
    ImageIcon s6 = new ImageIcon("Images/6_of_spades.png");
    ImageIcon s7 = new ImageIcon("Images/7_of_spades.png");
    ImageIcon s8 = new ImageIcon("Images/8_of_spades.png");
    ImageIcon s9 = new ImageIcon("Images/9_of_spades.png");
    ImageIcon s10 = new ImageIcon("Images/10_of_spades.png");
    ImageIcon sJ = new ImageIcon("Images/jack_of_spades2.png");
    ImageIcon sQ = new ImageIcon("Images/queen_of_spades2.png");
    ImageIcon sK = new ImageIcon("Images/king_of_spades2.png");

    JFrame frame;
    JPanel diamondsRow;
    JPanel clubsRow;
    JPanel heartsRow;
    JPanel spadesRow;

    public DisplayCards() {
        initializeComponents();
        setupFrame();
    }

    private void initializeComponents() {
        frame = new JFrame();
        diamondsRow = new JPanel();
        clubsRow = new JPanel();
        heartsRow = new JPanel();
        spadesRow = new JPanel();

        resizeCards(dA, d2, d3, d4, d5, d6, d7, d8, d9, d10, dJ, dQ, dK);
        resizeCards(cA, c2, c3, c4, c5, c6, c7, c8, c9, c10, cJ, cQ, cK);
        resizeCards(hA, h2, h3, h4, h5, h6, h7, h8, h9, h10, hJ, hQ, hK);
        resizeCards(sA, s2, s3, s4, s5, s6, s7, s8, s9, s10, sJ, sQ, sK);

        diamondsRow.add(new JLabel(dA));
        diamondsRow.add(new JLabel(d2));
        diamondsRow.add(new JLabel(d3));
        diamondsRow.add(new JLabel(d4));
        diamondsRow.add(new JLabel(d5));
        diamondsRow.add(new JLabel(d6));
        diamondsRow.add(new JLabel(d7));
        diamondsRow.add(new JLabel(d8));
        diamondsRow.add(new JLabel(d9));
        diamondsRow.add(new JLabel(d10));
        diamondsRow.add(new JLabel(dJ));
        diamondsRow.add(new JLabel(dQ));
        diamondsRow.add(new JLabel(dK));

        clubsRow.add(new JLabel(cA));
        clubsRow.add(new JLabel(c2));
        clubsRow.add(new JLabel(c3));
        clubsRow.add(new JLabel(c4));
        clubsRow.add(new JLabel(c5));
        clubsRow.add(new JLabel(c6));
        clubsRow.add(new JLabel(c7));
        clubsRow.add(new JLabel(c8));
        clubsRow.add(new JLabel(c9));
        clubsRow.add(new JLabel(c10));
        clubsRow.add(new JLabel(cJ));
        clubsRow.add(new JLabel(cQ));
        clubsRow.add(new JLabel(cK));

        heartsRow.add(new JLabel(hA));
        heartsRow.add(new JLabel(h2));
        heartsRow.add(new JLabel(h3));
        heartsRow.add(new JLabel(h4));
        heartsRow.add(new JLabel(h5));
        heartsRow.add(new JLabel(h6));
        heartsRow.add(new JLabel(h7));
        heartsRow.add(new JLabel(h8));
        heartsRow.add(new JLabel(h9));
        heartsRow.add(new JLabel(h10));
        heartsRow.add(new JLabel(hJ));
        heartsRow.add(new JLabel(hQ));
        heartsRow.add(new JLabel(hK));

        spadesRow.add(new JLabel(sA));
        spadesRow.add(new JLabel(s2));
        spadesRow.add(new JLabel(s3));
        spadesRow.add(new JLabel(s4));
        spadesRow.add(new JLabel(s5));
        spadesRow.add(new JLabel(s6));
        spadesRow.add(new JLabel(s7));
        spadesRow.add(new JLabel(s8));
        spadesRow.add(new JLabel(s9));
        spadesRow.add(new JLabel(s10));
        spadesRow.add(new JLabel(sJ));
        spadesRow.add(new JLabel(sQ));
        spadesRow.add(new JLabel(sK));

        frame.setLayout(new GridLayout(4, 1));
        frame.getContentPane().setBackground(new Color(21, 105, 29));
    }

    private void setupFrame() {
        frame.setTitle("Go Boom");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon.getImage());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(true);
        frame.getContentPane().setBackground(new Color(21, 105, 29));
        frame.setVisible(true);

        // Add the card rows to the main frame
        frame.add(diamondsRow);
        frame.add(clubsRow);
        frame.add(heartsRow);
        frame.add(spadesRow);

        diamondsRow.setBackground(new Color(21, 105, 29));
        clubsRow.setBackground(new Color(21, 105, 29));
        heartsRow.setBackground(new Color(21, 105, 29));
        spadesRow.setBackground(new Color(21, 105, 29));

    }

    private void resizeCards(ImageIcon... icons) {
        for (ImageIcon icon : icons) {
            Image image = icon.getImage();
            Image resizedImage = image.getScaledInstance(80, 120, Image.SCALE_SMOOTH);
            icon.setImage(resizedImage);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisplayCards());
    }
}