import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame implements ActionListener {
    ImageIcon icon = new ImageIcon("Images/cardicon.png");
    Image originalTitleImage = new ImageIcon("Images/Title.png").getImage();
    Image resizedTitleImage = originalTitleImage.getScaledInstance(600, 400, Image.SCALE_SMOOTH);
    ImageIcon titleIcon = new ImageIcon(resizedTitleImage);
    JLabel titleImage = new JLabel(titleIcon);
    JPanel panel1 = new JPanel();
    JButton startButton = new JButton("START");
    JButton quitButton = new JButton("Quit");
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel centerPanel = new JPanel(new GridBagLayout());

    public Menu() {
        startButton.addActionListener(this);
        quitButton.addActionListener(this);

        panel1.setLayout(new BorderLayout());
        panel1.setBackground(new Color(21, 105, 29));
        panel1.add(titleImage, BorderLayout.CENTER);
        panel1.add(buttonPanel, BorderLayout.PAGE_END);

        buttonPanel.setBackground(new Color(21, 105, 29));
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);

        centerPanel.setBackground(new Color(21, 105, 29));
        centerPanel.add(panel1);

        this.setTitle("Go Boom");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(new Color(21, 105, 29));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setResizable(true);
        this.add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Menu();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            DisplayCards game = new DisplayCards();
            this.dispose();
        }
        if (e.getSource() == quitButton) {
            System.exit(0);
        }
    }
}