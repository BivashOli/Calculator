package com.calculator.main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Display {

	private int width = 400, height = 500;
	private int row;
	private int column;
	private JFrame frame;
	private JPanel container, buttonPanel;
	private JTextField textField;
	private Font font;
	private StringBuilder builder;
	private Calculator calculator;
	private String[][] buttonTexts = { 
			{ "√", "x²", "^", "CE" }, 
			{ "7", "8", "9", "+" },
			{ "4", "5", "6", "-" },
			{ "1", "2", "3", "*" }, 
			{ "0", ".", "/", "=" } };
	private String[] operations = { "+", "-", "/", "*", "^" };
	private boolean isSymbol = false;
	private boolean carrySelected = false;
	private boolean numberSelected = false;

	public Display() {
		init();
		setProperties();
		window();
		update();
	}

	private void init() {
		row = buttonTexts.length;
		column = buttonTexts[0].length;
		frame = new JFrame("Calculator");
		container = new JPanel(new BorderLayout());
		buttonPanel = new JPanel(new GridLayout(row, column));
		textField = new JTextField(15);
		font = new Font("Sans-Serif", Font.PLAIN, 25);
		builder = new StringBuilder();
		calculator = new Calculator();
	}

	private void setProperties() {
		textField.setFont(font);
		textField.setEditable(false); 
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				JButton button = new JButton(buttonTexts[i][j]);
				button.setFont(font);
				buttonPanel.add(button);
			}
		}
		container.add(textField, BorderLayout.PAGE_START);
		container.add(buttonPanel, BorderLayout.CENTER);
	}

	private void window() {
		frame.getContentPane().add(container);
		frame.pack();
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void update() {
		for (int i = 0; i < buttonPanel.getComponentCount(); i++) {
			JButton button = (JButton) buttonPanel.getComponent(i);
			String text = button.getText();
			button.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					action(text);
					textField.setText(builder.toString());
					isSymbol = false;
				}
			});
		}
	}

	private void action(String text) {
		try {
			for (int i = 0; i < operations.length; i++) {
				if (text.equals(operations[i])) {
					isSymbol = true;
					break;
				}
			}
			if (text.equals("CE")) {
				clearEntry();
				calculator.reset();
			} else if (text.equals("=") || (isSymbol && carrySelected && numberSelected)) {
				double answer = calculator.solve(builder.toString().trim());
				clearEntry();
				if (!isSymbol)
					builder.append(answer);
				else
					builder.append(answer + " " + text + " ");
			} else if ((text.equals("√") || text.equals("x²")) && !numberSelected) {
				double answer = calculator.solve(builder.toString().trim() + " " + text);
				clearEntry();
				builder.append(answer);
			} else if (!isSymbol && !carrySelected) {
				builder.append(text);
			} else if (!isSymbol && carrySelected) {
				builder.append(text);
				numberSelected = true;
			} else if (isSymbol && !carrySelected) {
				if (!textField.getText().equals(null))
					builder.append(" " + text + " ");
				else
					builder.append(text + " ");
				carrySelected = true;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame, "Error");
		}
	}

	private void clearEntry() {
		carrySelected = false;
		numberSelected = false;
		builder = new StringBuilder();
	}
}
