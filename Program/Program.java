import java.util.*;
import java.io.*;

public class Program {
	public static void main(String[] args) throws IOException {
		// Scanner in = new Scanner(System.in);

		// int[][] board = new int[9][9];
		// int[] counter = { 0, 0 };

		// for (int i = 0; i < 9; ++i) {
		// 	String[] line = in.nextLine().split(" ");
		// 	for (int j = 0; j < 9; j++) {
		// 		board[i][j] = Integer.parseInt(line[j]);
		// 	}
		// }

		// // start timer
		// long startTime = System.nanoTime();

		// // solve
		// if (solveSudoku(board, counter)) {
		// 	printLooksNxa(board);
		// } else {
		// 	System.out.println("No solution");
		// }

		// // end timer
		// long endTime = System.nanoTime();
		// long duration = (endTime - startTime) / 1000;
		// String time = Long.toString(duration) + " microseconds";
		// System.out.println(time);

		// // comparisons
		// String comparisons = Integer.toString(counter[0]) + " comparisons";
		// String changes = Integer.toString(counter[1]) + " changes";
		// System.out.println(comparisons);
		// System.out.println(changes);

		// FileWriter output1 = new FileWriter("output1.csv");
		// output1.append("difficulty");
		// output1.append(",");
		// output1.append("time");
		// output1.append(",");
		// output1.append("comparisons");
		// output1.append(",");
		// output1.append("changes");
		// output1.append(",");
		// output1.append("\n");

		// /// ADD INPUT SHIT HERE;
		// output1.append(difficulty + "," + time + "," + comparisons + "," + changes);
		// output1.append("\n");

		// output1.flush();
		// output1.close();

		// in.close();


		inputForSubmission();
	}

	public static boolean isSafe(int[][] board, int row, int col, int num, int[] counter) {
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

	public static boolean solveSudoku(int[][] board, int[] counter) {
		int row = -1;
		int col = -1;
		boolean isSolved = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == 0) { // Some values are still not filled in
					row = i;
					col = j;

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
			if (isSafe(board, row, col, num, counter)) {
				counter[1]++;
				board[row][col] = num;
				if (solveSudoku(board, counter)) {
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

	public static void printLooksNxa(int[][] board) {
		System.out.println("\n");
		System.out.println("----- Solution: -----");

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

	public static void inputForSubmission()  throws IOException {
		// open file
		try {
			FileWriter output1 = new FileWriter("output1.csv");
			output1.append("difficulty");
			output1.append(",");
			output1.append("time");
			output1.append(",");
			output1.append("comparisons");
			output1.append(",");
			output1.append("changes");
			output1.append(",");
			output1.append("\n");
	

			File myObj = new File("PUZZIES.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				// Take in puzzles
				String difc = myReader.nextLine();
				int[] counter = { 0, 0 };
				int[][] board = new int[9][9];

				for (int i = 0; i < 9; ++i) {
					String[] line = myReader.nextLine().split(" ");
					for (int j = 0; j < 9; j++) {
						board[i][j] = Integer.parseInt(line[j]);
					}
				}

				// start timer
				long startTime = System.nanoTime();

				// solve
				if (solveSudoku(board, counter)) {
					printLooksNxa(board);
				} else {
					System.out.println("No solution");
				}

				System.out.println(difc);

				// end timer
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / 1000;
				String time = Long.toString(duration) + " microseconds";
				System.out.println(time);

				// comparisons
				String comparisons = Integer.toString(counter[0]) + " comparisons";
				String changes = Integer.toString(counter[1]) + " changes";
				System.out.println(comparisons);
				System.out.println(changes);

				/// ADD INPUT SHIT HERE;
				output1.append(difc + "," + time + "," + comparisons + "," + changes);
				output1.append("\n");
			}
			output1.flush();
			output1.close();
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}