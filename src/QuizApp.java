import javax.swing.*;
import java.awt.*;

public class QuizApp extends JFrame {
    public QuizApp() {
        setTitle("🎯 Quiz Application");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout(20, 20));

        JLabel title = new JLabel("Welcome to the Quiz Portal", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JButton quizButton = createButton("🧠 Take Quiz");
        JButton adminButton = createButton("⚙️ Admin Panel");

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        btnPanel.setBackground(new Color(30, 30, 30));
        btnPanel.add(quizButton);
        btnPanel.add(adminButton);

        add(title, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        quizButton.addActionListener(e -> {
            new QuizWindow().setVisible(true);
            dispose();
        });

        adminButton.addActionListener(e -> {
            new AdminPanel().setVisible(true);
            dispose();
        });
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBackground(new Color(0, 120, 215));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(new Color(0, 140, 240)); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(new Color(0, 120, 215)); }
        });
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
    }
}
