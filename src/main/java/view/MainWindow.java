package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("ALL")
public class MainWindow extends JFrame {
    private final JLabel timeLabel;

    public MainWindow(String username) {
        setTitle("Mobilisation De Créance");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel backgroundPanel = new JPanel() {
            BufferedImage backgroundImage;
            {
                try {
                    URL imageUrl = getClass().getClassLoader().getResource("resources/BTE000_026d9ecc-d817-4258-b39a-2cf60408797e_b.jpg");
                    if (imageUrl != null) {
                        backgroundImage = ImageIO.read(imageUrl);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 255));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        menuBar.setOpaque(false);
        menuBar.setForeground(Color.BLACK);

        JMenu referentielMenu = new JMenu("Référentiel");
        referentielMenu.setForeground(Color.BLACK);
        JMenuItem typeCreanceItem = new JMenuItem("Type de Créance");
        typeCreanceItem.addActionListener(e -> new TypeCreanceWindow().setVisible(true));
        referentielMenu.add(typeCreanceItem);

        menuBar.add(referentielMenu);
        setJMenuBar(menuBar);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Mobilisation De Créance", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 40, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(titleLabel);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 100));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        bottomPanel.setLayout(new BorderLayout());

        JLabel userLabel = new JLabel("Utilisateur: " + username);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(Color.WHITE);

        timeLabel = new JLabel();
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timeLabel.setForeground(Color.WHITE);
        updateTime();

        JButton exitButton = new JButton("Quitter");
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitButton.setBackground(new Color(255, 255, 255));
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(e -> System.exit(0));

        bottomPanel.add(userLabel, BorderLayout.WEST);
        bottomPanel.add(timeLabel, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(backgroundPanel);

        Timer timer = new Timer(1000, e -> updateTime());
        timer.start();
    }

    private void updateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        timeLabel.setText("Heure: " + LocalDateTime.now().format(formatter));
    }
}