import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorApp().createGUI());
    }

    private void createGUI() {
        JFrame frame = new JFrame("Calculator");
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Segoe UI", Font.BOLD, 24));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            button.addActionListener(e -> onButtonClick(e, textField));
            panel.add(button);
        }

        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245));
        frame.add(textField, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private String operand1 = "";
    private String operator = "";
    private boolean startNewNumber = true;

    private void onButtonClick(ActionEvent e, JTextField textField) {
        String command = ((JButton) e.getSource()).getText();

        switch (command) {
            case "C":
                textField.setText("");
                operand1 = "";
                operator = "";
                break;

            case "+": case "-": case "*": case "/":
                operand1 = textField.getText();
                operator = command;
                startNewNumber = true;
                break;

            case "=":
                try {
                    double num1 = Double.parseDouble(operand1);
                    double num2 = Double.parseDouble(textField.getText());
                    double result = switch (operator) {
                        case "+" -> num1 + num2;
                        case "-" -> num1 - num2;
                        case "*" -> num1 * num2;
                        case "/" -> num2 != 0 ? num1 / num2 : 0;
                        default -> 0;
                    };
                    textField.setText(String.valueOf(result));
                    startNewNumber = true;
                } catch (NumberFormatException | ArithmeticException ex) {
                    textField.setText("Error");
                }
                break;

            default:
                if (startNewNumber) {
                    textField.setText(command);
                    startNewNumber = false;
                } else {
                    textField.setText(textField.getText() + command);
                }
        }
    }
}
