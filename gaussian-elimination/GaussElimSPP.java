import java.util.Scanner;

import java.io.File;

public class GaussElimSPP {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		int numOfEq = promptNumberOfEquations();
		double[][] matrix = new double[numOfEq][numOfEq + 1];
		promptInputMethod(matrix);
		/*
		 * Unused Code
		 * double[][] nAMatrix = getNonAugmentedMatrix(matrix);
		 * double[] bVector = getBVector(matrix);
		 */
		gaussianElimination(matrix);
	}

	// Prints 2D array.
	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%20f", matrix[i][j]);
			}
			System.out.println();
		}
	}

	// Prints 1D array.
	public static void printMatrix(double[] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("x_%d: %f\n", i + 1, matrix[i]);
		}
	}

	// Prompt the user for the number of equations to solve.
	public static int promptNumberOfEquations() {
		Scanner kb = new Scanner(System.in);
		int numOfEq = 0;
		while (numOfEq < 1 || numOfEq > 10) {
			try {
				System.out.println("Enter the number of linear equations to solve (minimum 1, maximum 10).");
				numOfEq = kb.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input. Try again.");
				return promptNumberOfEquations();
			}
		}
		return numOfEq;
	}

	// Prompt the user which method they would like to use to input coefficients.
	public static void promptInputMethod(double[][] matrix) {
		Scanner kb = new Scanner(System.in);
		System.out.print(
				"\nType an option in the console an press enter:\n1: Input coefficients manually through the console.\n2: Insert a file containing the Augmented Coefficient Matrix.\n");
		String input = kb.next();
		if (input.equals("1") || input.toLowerCase().equals("one") || input.toLowerCase().equals("uno")) {
			System.out.println("\nOption 1 selected.");
			fillMatrix(matrix);
		} else if (input.equals("2") || input.toLowerCase().equals("two") || input.toLowerCase().equals("dos")) {
			System.out.println("\nOption 2 selected.");
			readCoefficients(matrix);
		} else {
			System.out.println("\nPlease select an option.");
			promptInputMethod(matrix);
		}
	}

	// Fills the matrix with values depending on the input chosen by the user in
	// method promptInputMethod(double[][] matrix).
	public static void fillMatrix(double[][] matrix) {
		System.out.println("Begin entering coefficients row by row.");
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = inputCoefficient();
			}
		}
		System.out.println("Coefficients successfully entered.\n");
	}

	// Ensures the user inputs a valid coefficient value, recalls itself if
	// otherwise.
	public static double inputCoefficient() {
		Scanner kb = new Scanner(System.in);
		double coefficient;
		try {
			coefficient = kb.nextDouble();
			System.out.println(coefficient + " entered.");
		} catch (Exception e) {
			System.out.println("Enter a valid coefficient.");
			return inputCoefficient();
		}
		return coefficient;
	}

	// First checks if the file entered exists, and if it does exist, start reading
	// the coefficients. Ends if an invalid value is encountered. Returns null if
	// the array is not filled.
	public static void readCoefficients(double[][] matrix) {
		System.out.println("Enter a file.");
		Scanner kb = new Scanner(System.in);
		Scanner readMatrixFile = new Scanner("");
		try {
			readMatrixFile = new Scanner(new File(kb.next()));
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					double coefficient = readMatrixFile.nextDouble();
					matrix[i][j] = coefficient;
					System.out.println(coefficient + " entered.");
				}
			}
		} catch (Exception e) {
			System.out.println(
					"Invalid file or invalid value detected. Check the file, coefficients and/or the number of equations to solve and try again.");
			System.exit(0);
		}
		if (readMatrixFile.hasNextDouble()) {
			System.out
					.println("There are extra values in the entered file. Check the file or the number of equations to solve.");
			System.exit(0);
		} else {
			System.out.println("Coefficients successfully entered.\n");
		}
	}

	/*
	 * Unused Code
	 * public static double[][] getNonAugmentedMatrix(double [][] matrix) {
	 * double[][] nAMatrix = new double[matrix.length][matrix.length];
	 * for(int i = 0; i < matrix.length; i++) {
	 * for(int j = 0; j < matrix[i].length - 1; j++) {
	 * nAMatrix[i][j] = matrix[i][j];
	 * }
	 * }
	 * return nAMatrix;
	 * }
	 * 
	 * // Isolates the b vector.
	 * public static double[] getBVector(double[][] matrix) {
	 * double[] bVector = new double[matrix.length];
	 * for(int i = 0; i < matrix.length; i++) {
	 * bVector[i] = matrix[i][matrix.length];
	 * }
	 * return bVector;
	 * }
	 */

	// Gaussian Elimination
	public static void gaussianElimination(double[][] matrix) {
		int n = matrix.length;
		double[] scale = new double[n];
		for (int i = 0; i < n; i++) {
			double max = 0;
			for (int j = 0; j < n; j++) {
				if (Math.abs(matrix[i][j]) > max) {
					max = Math.abs(matrix[i][j]);
					scale[i] = max;
				}
			}
		}
		for (int i = 0; i < n; i++) {
			printMatrix(matrix);
			pivot(matrix, scale, n, i);
			for (int row = i + 1; row < n; row++) {
				double quotient = matrix[row][i] / matrix[i][i];
				for (int col = 0; col < n + 1; col++) {
					matrix[row][col] -= (matrix[i][col] * quotient);
				}
			}
		}
		double[] result = new double[n];
		for (int i = n - 1; i >= 0; i--) {
			double add = 0;
			for (int j = i; j < n; j++) {
				add += (matrix[i][j] * result[j]);
				result[i] = (matrix[i][n] - add) / matrix[i][i];
			}
		}
		System.out.println("Solution:");
		printMatrix(result);
	}

	// Finds pivot rows and swaps the rows accordingly.
	public static void pivot(double[][] matrix, double[] scale, int n, int index) {
		double[] ratios = new double[scale.length];
		double max = -1e100;
		int maxRow = 0;
		for (int i = index; i < n; i++) {
			ratios[i] = Math.abs(matrix[i][index] / scale[i]);
			if (max < Math.abs(matrix[i][index] / scale[i])) {
				maxRow = i;
				max = Math.abs(matrix[i][index] / scale[i]);
			}
		}
		System.out.println("Scaled ratios:");
		printMatrix(ratios);
		System.out.println("Pivot row: " + (maxRow + 1) + "\n");

		double temp = scale[index];
		scale[index] = scale[maxRow];
		scale[maxRow] = temp;

		double[] temp1 = matrix[index];
		matrix[index] = matrix[maxRow];
		matrix[maxRow] = temp1;
	}
}
