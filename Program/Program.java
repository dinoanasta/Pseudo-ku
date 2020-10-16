import java.util.*;
import java.io.*;

public class Program {
	static int[] counter = { 0, 0 };

	public static void main(String[] args) throws IOException {

		//Run for submission purposes
		// runForSubmission();

		//Run for testing purposes
		runForTesting();
	}

	public static boolean isSafe(int[][] board, int row, int col, int num) {
		// Check row
		for (int c = 0; c < 9; c++) {
			counter[0]++;
			if (board[row][c] == num) { // Number already exists in row
				return false;
			}
		}

		// Check column
		for (int r = 0; r < 9; r++) {
			counter[0]++;
			if (board[r][col] == num) { // Number already exists in column
				return false;
			}
		}

		// Check sub grid
		int sqrt = (int) Math.sqrt(board.length);
		int boxRowStart = row - row % sqrt;
		int boxColStart = col - col % sqrt;

		for (int r = boxRowStart; r < boxRowStart + sqrt; r++) {
			for (int c = boxColStart; c < boxColStart + sqrt; c++) {
				counter[0]++;
				if (board[r][c] == num) { // Number already exists in sub grid
					return false;
				}
			}
		}

		// No clash - this number is safe in position board[row][col]
		return true;
	}

	public static boolean solveSudoku(int[][] board) {
		int row = -1;
		int col = -1;
		boolean isSolved = true;
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] == 0) { // Some values are still not filled in
					row = r;
					col = c;

					isSolved = false;
					break;
				}
			}
			if (!isSolved) {
				break;
			}
		}

		if (isSolved) { // Sudoku is solved
			return true;
		}

		// If Sudoku is not yet solved, backtrack for each row
		for (int num = 1; num <= 9; num++) {
			if (isSafe(board, row, col, num)) {
				counter[1]++;
				board[row][col] = num;
				if (solveSudoku(board)) {
					return true;
				} else {
					board[row][col] = 0;
				}
			}
		}
		return false;
	}

	public static void printForSubmission(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				System.out.print(board[row][column]);
				System.out.print(" ");

			}
			System.out.print("\n");
		}
	}

	public static void printForTesting(int[][] board) {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				System.out.print(board[row][column]);
				System.out.print(" ");

				if ((column + 1) % 3 == 0) {
					System.out.print(" ");
				}

			}
			System.out.print("\n");

			if ((row + 1) % 3 == 0) {
				System.out.print("\n");
			}
		}
	}

	public static void runForSubmission()  throws IOException {
		Scanner in = new Scanner(System.in);

		int[][] board = new int[9][9];

		for (int i = 0; i < 9; ++i) {
			String[] line = in.nextLine().split(" ");
			for (int j = 0; j < 9; j++) {
				board[i][j] = Integer.parseInt(line[j]);
			}
		}

		//Solve
		if (solveSudoku(board)) {
			printForSubmission(board);
		} else {
			System.out.println("No solution");
		}
		in.close();
	}

	public static void runForTesting()  throws IOException {
		try {
			//Create new .csv file
			FileWriter timeOutput = new FileWriter("bull1.csv");
			timeOutput.append("NumClues");
			timeOutput.append(",");
			timeOutput.append("Time");
			timeOutput.append("\n");

			FileWriter comparisonsOutput = new FileWriter("bull2.csv");
			comparisonsOutput.append("NumClues");
			comparisonsOutput.append(",");
			comparisonsOutput.append("Comparisons");
			comparisonsOutput.append("\n");

			FileWriter changesOutput = new FileWriter("bull3.csv");
			changesOutput.append("NumClues");
			changesOutput.append(",");
			changesOutput.append("Changes");
			changesOutput.append("\n");
	
			//Open file
			File myObj = new File("DifficultyLevels/PuzzlesNumCluesLabel.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				// Take in puzzles
				int difc = Integer.parseInt(myReader.nextLine());
				int[][] board = new int[9][9];

				for (int i = 0; i < 9; ++i) {
					String[] line = myReader.nextLine().split(" ");
					for (int j = 0; j < 9; j++) {
						board[i][j] = Integer.parseInt(line[j]);
					}
				}
				
				//Print out original puzzle
				System.out.println("----- PUZZLE: -----");
				printForTesting(board);

				//Start timer
				long startTime = System.nanoTime();

				//Solve and print solution, or print "No solution"
				if (solveSudoku(board)) {
					System.out.println("----- SOLUTION: -----");
					printForTesting(board);
				} else {
					System.out.println("No solution");
				}

				//End timer
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000;

				int comparisons = counter[0];
				int changes = counter[1];

				System.out.println("Difficulty: " + difc);
				System.out.println(Long.toString(duration) + " microseconds");
				System.out.println(Integer.toString(comparisons) + " comparisons");
				System.out.println(Integer.toString(changes) + " changes");
				System.out.println("\n");
				System.out.println("=======================================================");
				System.out.println("\n");

				timeOutput.append(difc + "," + duration);
				timeOutput.append("\n");


				comparisonsOutput.append(difc + "," + comparisons);
				comparisonsOutput.append("\n");


				changesOutput.append(difc + "," + changes);
				changesOutput.append("\n");


			}
			timeOutput.flush();
			timeOutput.close();

			comparisonsOutput.flush();
			comparisonsOutput.close();

			changesOutput.flush();
			changesOutput.close();

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}