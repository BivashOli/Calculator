package com.calculator.main;

public class Calculator {

	private double num1, num2, num3;
	private String operator;

	public double solve(String text) {
		String[] tokens = text.split(" ");
		if (tokens.length > 2) {
			num1 = Double.valueOf(tokens[0]);
			num2 = Double.valueOf(tokens[2]);
			operator = tokens[1];
		} else {
			if (tokens[1].equals("√") || tokens[1].equals("x²")) {
				num1 = Double.valueOf(tokens[0]);
				operator = tokens[1];
				System.out.println(num1 + " " + operator);
			} else {
				num2 = Double.valueOf(tokens[1]);
				operator = tokens[0];
			}
		}
		if (operator.equals("+"))
			num3 = num1 + num2;
		else if (operator.equals("-"))
			num3 = num1 - num2;
		else if (operator.equals("*"))
			num3 = num1 * num2;
		else if (operator.equals("/"))
			num3 = num1 / num2;
		else if (operator.equals("^"))
			num3 = Math.pow(num1, num2);
		else if (operator.equals("√"))
			num3 = Math.sqrt(num1);
		else if(operator.equals("x²"))
			num3 = Math.pow(num1, 2);
		num1 = num3;
		return num1;
	}

	public void reset() {
		num1 = num2 = num3 = 0;
	}
}
