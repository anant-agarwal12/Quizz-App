import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminPanel extends JFrame {
    private JTextArea questionField;
    private JTextField opt1, opt2, opt3, opt4, correct;
    private JButton addBtn, backBtn;

    public AdminPanel() {
        setTitle("⚙️ Admin Panel - Add Questions");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Add New Question", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(6, 2, 10, 10));
        form.setBackground(new Color(40, 40, 40));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        questionField = new JTextArea(3, 30);
        opt1 = new JTextField(); opt2 = new JTextField(); opt3 = new JTextField(); opt4 = new JTextField();
        correct = new JTextField();

        form.add(new JLabel("Question:")); form.add(new JScrollPane(questionField));
        form.add(new JLabel("Option 1:")); form.add(opt1);
        form.add(new JLabel("Option 2:")); form.add(opt2);
        form.add(new JLabel("Option 3:")); form.add(opt3);
        form.add(new JLabel("Option 4:")); form.add(opt4);
        form.add(new JLabel("Correct Option (1-4):")); form.add(correct);

        for (Component c : form.getComponents()) {
            c.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            c.setForeground(Color.WHITE);
            if (c instanceof JLabel) ((JLabel) c).setForeground(Color.WHITE);
            if (c instanceof JTextField) ((JTextField) c).setBackground(new Color(60, 63, 65));
            if (c instanceof JTextArea) ((JTextArea) c).setBackground(new Color(60, 63, 65));
        }

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 30));
        addBtn = new JButton("➕ Add Question");
        backBtn = new JButton("⬅️ Back");

        styleButton(addBtn);
        styleButton(backBtn);

        btnPanel.add(addBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addQuestion());
        backBtn.addActionListener(e -> {
            new QuizApp().setVisible(true);
            dispose();
        });
    }

    private void styleButton(JButton b) {
        b.setFont(new Font("Segoe UI", Font.BOLD, 16));
        b.setBackground(new Color(0, 120, 215));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(new Color(0, 140, 240)); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(new Color(0, 120, 215)); }
        });
    }

    private void addQuestion() {
        String q = questionField.getText();
        String o1 = opt1.getText(), o2 = opt2.getText(), o3 = opt3.getText(), o4 = opt4.getText();
        int correctOpt = Integer.parseInt(correct.getText());

        if (q.isEmpty() || o1.isEmpty() || o2.isEmpty() || o3.isEmpty() || o4.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO questions(question, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, q); ps.setString(2, o1); ps.setString(3, o2);
            ps.setString(4, o3); ps.setString(5, o4); ps.setInt(6, correctOpt);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Question Added Successfully!");
            questionField.setText(""); opt1.setText(""); opt2.setText(""); opt3.setText(""); opt4.setText(""); correct.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
