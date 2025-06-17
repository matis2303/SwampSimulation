package org.swampsimulation.UI;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class MenuFrame extends JFrame {
    private static final int MIN_TPS = 1;

    public MenuFrame() {
        setTitle("Swamp Simulation – Menu");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 0, 30));

        JLabel titleLabel = new JLabel("Swamp Simulation");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JTextField widthField = new JTextField("1200");
        ((AbstractDocument) widthField.getDocument()).setDocumentFilter(new PositiveIntegerDocumentFilter());

        JTextField heightField = new JTextField("900");
        ((AbstractDocument) heightField.getDocument()).setDocumentFilter(new PositiveIntegerDocumentFilter());

        JTextField endTimeField = new JTextField("200");
        ((AbstractDocument) endTimeField.getDocument()).setDocumentFilter(new PositiveIntegerDocumentFilter());

        JTextField frogsField = new JTextField("13");
        ((AbstractDocument) widthField.getDocument()).setDocumentFilter(new PositiveIntegerDocumentFilter());


        JPanel widthPanel = createLabeledField("Width:", widthField);
        JPanel heightPanel = createLabeledField("Hight:", heightField);
        JPanel tpsPanel = createLabeledField("End time:", endTimeField);
        JPanel frogsPanel = createLabeledField("Frogs count:", frogsField);



        JButton startButton = new JButton("Start");
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setPreferredSize(new Dimension(100, 40));
        startButton.setMaximumSize(new Dimension(100, 40));

        startButton.addActionListener(e -> {
            int widthValue, heightValue, endTimeValue, frogsValue;

            try {
                String widthText = widthField.getText();
                String heightText = heightField.getText();
                String tpsText = endTimeField.getText();
                String frogsText = frogsField.getText();

                if (widthText.isEmpty() || heightText.isEmpty() || tpsText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled", "Data error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                widthValue = Integer.parseInt(widthText);
                heightValue = Integer.parseInt(heightText);
                endTimeValue = Integer.parseInt(tpsText);
                frogsValue = Integer.parseInt(frogsText);

                if (endTimeValue <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Ticks number must be positive.",
                            "Error: TPS count", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (widthValue <= 0 || heightValue <= 0 ) {
                    JOptionPane.showMessageDialog(this, "Field values must be positive.", "Data error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (widthValue < 400 || heightValue < 300 ) {
                    JOptionPane.showMessageDialog(this, "Please give higher value", "Data error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please give positive integer numbers.", "Data error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();

            final SimulationConfig config = new SimulationConfig(widthValue, heightValue, endTimeValue, frogsValue);

            SwingUtilities.invokeLater(() -> {
                SwampSimFrame simFrame = new SwampSimFrame(config);
                simFrame.setVisible(true);
            });
        });

        mainPanel.add(titleLabel);
        mainPanel.add(widthPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(heightPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(tpsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(frogsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(startButton);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 25));
        label.setMinimumSize(new Dimension(100, 25));
        label.setMaximumSize(new Dimension(100, 25));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(textField);
        panel.setMaximumSize(new Dimension(300, 30));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

    static class PositiveIntegerDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            if (containsOnlyDigits(string)) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (containsOnlyDigits(text)) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        private boolean containsOnlyDigits(String text) {
            for (char c : text.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
        }
    }
}