import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizWindow extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private JButton nextButton;
    private ButtonGroup group;
    private List<Question> questions;
    private int index = 0, score = 0;

    public QuizWindow() {
        setTitle("🧠 Take the Quiz");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(25, 25, 25));
        setLayout(new BorderLayout(20, 20));

        questionLabel = new JLabel("Loading...", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        questionLabel.setForeground(Color.WHITE);
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionPanel.setBackground(new Color(40, 40, 40));
        options = new JRadioButton[4];
        group = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBackground(new Color(40, 40, 40));
            options[i].setForeground(Color.WHITE);
            options[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            group.add(options[i]);
            optionPanel.add(options[i]);
        }

        add(optionPanel, BorderLayout.CENTER);

        nextButton = new JButton("Next ➡️");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextButton.setBackground(new Color(0, 120, 215));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> nextQuestion());
        add(nextButton, BorderLayout.SOUTH);

        loadQuestions();
        displayQuestion(0);
    }

    private void loadQuestions() {
        questions = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM questions")) {

            while (rs.next()) {
                String[] opts = {rs.getString("option1"), rs.getString("option2"),
                        rs.getString("option3"), rs.getString("option4")};
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("question"),
                        opts,
                        rs.getInt("correct_option")
                ));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }
    }

    private void displayQuestion(int idx) {
        if (idx >= questions.size()) {
            JOptionPane.showMessageDialog(this, "🎉 Quiz Completed! Your score: " + score + "/" + questions.size());
            new QuizApp().setVisible(true);
            dispose();
            return;
        }

        Question q = questions.get(idx);
        questionLabel.setText("<html><body style='text-align:center;'>" + (idx + 1) + ". " + q.getQuestion() + "</body></html>");
        String[] opts = q.getOptions();
        group.clearSelection();
        for (int i = 0; i < 4; i++) {
            options[i].setText(opts[i]);
        }
    }

    private void nextQuestion() {
        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) selected = i + 1;
        }

        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Please select an answer!");
            return;
        }

        if (selected == questions.get(index).getCorrectOption()) score++;
        index++;
        displayQuestion(index);
    }
}
