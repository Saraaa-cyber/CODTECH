import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeTracker extends JFrame {
    private JTextField[] subjectFields;
    private JTextField[] markFields;
    private JLabel totalMarksLabel;
    private JLabel averageMarksLabel;
    private JLabel letterGradeLabel;
    private JLabel gpaLabel;
    private JButton calculateButton;

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setLayout(new GridLayout(7, 2));

        subjectFields = new JTextField[5];
        markFields = new JTextField[5];

        for (int i = 0; i < 5; i++) {
            subjectFields[i] = new JTextField();
            markFields[i] = new JTextField();
            add(new JLabel("Subject " + (i + 1) + ":"));
            add(subjectFields[i]);
            add(new JLabel("Marks:"));
            add(markFields[i]);
        }

        calculateButton = new JButton("Calculate Average");
        totalMarksLabel = new JLabel("Total Marks: ");
        averageMarksLabel = new JLabel("Average Marks: ");
        letterGradeLabel = new JLabel("Letter Grade: ");
        gpaLabel = new JLabel("GPA: ");

        calculateButton.addActionListener(new CalculateButtonListener());

        add(calculateButton);
        add(totalMarksLabel);
        add(new JLabel());
        add(averageMarksLabel);
        add(new JLabel());
        add(letterGradeLabel);
        add(new JLabel());
        add(gpaLabel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double sum = 0;
            int count = 0;

            for (int i = 0; i < markFields.length; i++) {
                String markText = markFields[i].getText();
                if (!markText.isEmpty()) {
                    try {
                        double marks = Double.parseDouble(markText);
                        sum += marks;
                        count++;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid numeric marks.");
                        return;
                    }
                }
            }

            if (count == 0) {
                JOptionPane.showMessageDialog(null, "Please enter at least one mark.");
                return;
            }

            double average = sum / count;
            double total = sum;

            totalMarksLabel.setText("Total Marks: " + total);
            averageMarksLabel.setText("Average Marks: " + average);

            String letterGrade = calculateLetterGrade(average);
            letterGradeLabel.setText("Letter Grade: " + letterGrade);

            double gpa = calculateGPA(average);
            gpaLabel.setText("GPA: " + gpa);
        }

        private String calculateLetterGrade(double average) {
            if (average >= 90) return "A";
            if (average >= 80) return "B";
            if (average >= 70) return "C";
            if (average >= 60) return "D";
            return "F";
        }

        private double calculateGPA(double average) {
            if (average >= 90) return 4.0;
            if (average >= 80) return 3.0;
            if (average >= 70) return 2.0;
            if (average >= 60) return 1.0;
            return 0.0;
        }
    }

    public static void main(String[] args) {
        new StudentGradeTracker();
    }
}

