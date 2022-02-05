import java.io.File;
import java.util.Scanner;

public class JacobiSeidel {
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		int numOfEq = promptNumberOfEquations();
		double[][] matrix = new double[numOfEq][numOfEq + 1];
		promptInputMethod(matrix);
		promptInitialConditions(matrix, numOfEq);
	}

	// Prints 1D array.
	public static void printMatrix(double[] matrix) {
		System.out.print("[ ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.print(matrix[i] + " ");
		}
		System.out.print("]T\n");
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
			System.out.println("Enter a valid value.");
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

	public static void promptInitialConditions(double[][] matrix, int numOfEq) {
		System.out.println("Enter the stopping error.");
		double stopError = inputCoefficient();
		System.out.println();
		System.out.println("Enter initial guesses.");
		double[] initialGuess = new double[numOfEq];
		for (int i = 0; i < initialGuess.length; i++) {
			initialGuess[i] = inputCoefficient();
		}
		jacobi(matrix, initialGuess, stopError);
		gSeidel(matrix, initialGuess, stopError);

	}

	// Jacobi method
	public static void jacobi(double[][] matrix, double[] guess, double stopError) {
		System.out.println("\nJACOBI:");
		int iterations = 0;
		int n = matrix.length;
		double[] approx = new double[n];

		while (true) {
			for (int i = 0; i < n; i++) {
				double sum = matrix[i][n];
				for (int j = 0; j < n; j++) {
					if (j != i) {
						sum -= matrix[i][j] * guess[j];
					}
				}
				approx[i] = 1 / matrix[i][i] * sum;
			}

			System.out.print("Iteration " + (iterations + 1) + ": ");
			printMatrix(approx);
			iterations++;

			if (iterations > 50) {
				System.out.println("Reached 50 iterations before error threshold was reached.");
				break;
			}
			double numerator = 0;
			double denominator = 0;
			for (int i = 0; i < n; i++) {
				numerator += Math.pow(approx[i] - guess[i], 2);
				denominator += Math.pow(approx[i], 2);
			}
			if (Math.sqrt(numerator) / Math.sqrt(denominator) < stopError) {
				System.out.println("Stop error threshold reached.");
				break;
			}
			guess = approx.clone();
		}
		System.out.println("END JACOBI\n");
	}

	// Gauss Seidel
	public static void gSeidel(double[][] matrix, double[] guess, double stopError) {
		System.out.println("GAUSS SEIDEL:");
		int iterations = 0;
		int n = matrix.length;
		double[] approx = new double[n];

		while (true) {
			for (int i = 0; i < n; i++) {
				double sum = matrix[i][n];
				for (int j = 0; j < n; j++) {
					if (j != i) {
						if (j < i) {
							sum -= matrix[i][j] * approx[j];
						} else {
							sum -= matrix[i][j] * guess[j];
						}
					}
				}
				approx[i] = 1 / matrix[i][i] * sum;
			}

			System.out.print("Iteration " + (iterations + 1) + ": ");
			printMatrix(approx);
			iterations++;

			if (iterations > 50) {
				System.out.println("Reached 50 iterations before error threshold was reached.");
				break;
			}
			double numerator = 0;
			double denominator = 0;
			for (int i = 0; i < n; i++) {
				numerator += Math.pow(approx[i] - guess[i], 2);
				denominator += Math.pow(approx[i], 2);
			}
			if (Math.sqrt(numerator) / Math.sqrt(denominator) < stopError) {
				System.out.println("Stop error threshold reached.");
				break;
			}
			guess = approx.clone();
		}
		System.out.println("END GAUSS SEIDEL");
	}
}
