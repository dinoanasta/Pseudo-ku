import java.util.*;
import java.io.*;

public class Program {
	static int[] counter = { 0, 0, 0 }; //comparisons, changes, recursions
	//static int[][] board = new int[9][9];

	public static void main(String[] args) throws IOException {

		//Run for submission purposes
		// runForSubmission();

		//Run for testing purposes
		runForTestingNew();
		compileResults();
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
		counter[2]++;
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

	public static void removeCluesOrdered(int[][] board, int numClues) {
			for (int row = 0; row < 9; row++) {
				for (int column = 0; column < 9; column++) {
					if(board[row][column]!=0){
						board[row][column]=0;
						numClues--;
					}
					if(numClues<=0){
						break;
					}
				}
				if(numClues<=0){
						break;
					}
			}

			//return board;
	}
	

	public static void removeCluesRandom(int[][] board, int numClues) {
		while(numClues>0){
			Random r = new Random();
			int row = r.nextInt(9);
			int column = r.nextInt(9);
			if(board[row][column]!=0){
				board[row][column]=0;
				numClues--;
			}
		}

		//return board;
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

	public static void runForTestingNew()  throws IOException {
		try {
			//Create new .csv file
			FileWriter timeOutput = new FileWriter("time.csv");
			timeOutput.append("EmptyCells");
			timeOutput.append(",");
			timeOutput.append("Time");
			timeOutput.append("\n");

			FileWriter comparisonsOutput = new FileWriter("comparisons.csv");
			comparisonsOutput.append("EmptyCells");
			comparisonsOutput.append(",");
			comparisonsOutput.append("Comparisons");
			comparisonsOutput.append("\n");

			FileWriter changesOutput = new FileWriter("changes.csv");
			changesOutput.append("EmptyCells");
			changesOutput.append(",");
			changesOutput.append("Changes");
			changesOutput.append("\n");

			FileWriter recursionsOutput = new FileWriter("recursions.csv");
			recursionsOutput.append("EmptyCells");
			recursionsOutput.append(",");
			recursionsOutput.append("Recursions");
			recursionsOutput.append("\n");
	
			//Open file
			File myObj = new File("COMPLETED_PUZZLES.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				// Take in puzzlesthat the value it has just compared has
				int difc = Integer.parseInt(myReader.nextLine());
				int[][] board = new int[9][9];

				for (int i = 0; i < 9; ++i) {
					String[] line = myReader.nextLine().split(" ");
					for (int j = 0; j < 9; j++) {
						board[i][j] = Integer.parseInt(line[j]);
					}
				}

				
				for(int g=0; g<=81; g++){

					//reset counter
					counter[0]=0;
					counter[1]=0;
					counter[2]=0;

					//remove clue
					removeCluesRandom(board,g);
					

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
					int recursions = counter[2];

					System.out.println("Board Number: " + difc);
					System.out.println(Long.toString(duration) + " microseconds");
					System.out.println(Integer.toString(comparisons) + " comparisons");
					System.out.println(Integer.toString(changes) + " changes");
					System.out.println("\n");
					System.out.println("=======================================================");
					System.out.println("\n");

					timeOutput.append(g + "," + duration);
					timeOutput.append("\n");


					comparisonsOutput.append(g + "," + comparisons);
					comparisonsOutput.append("\n");


					changesOutput.append(g + "," + changes);
					changesOutput.append("\n");

					recursionsOutput.append(g + "," + recursions);
					recursionsOutput.append("\n");
				}


			}
			timeOutput.flush();
			timeOutput.close();

			comparisonsOutput.flush();
			comparisonsOutput.close();

			changesOutput.flush();
			changesOutput.close();

			recursionsOutput.flush();
			recursionsOutput.close();

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static void compileResults() throws IOException {
		try{

			int[] cntr = new int[82];
			int numBoards = 0;

			//time 
			FileWriter timeAverage = new FileWriter("timeAve.csv");
			timeAverage.append("EmptyCells");
			timeAverage.append(",");
			timeAverage.append("Time");
			timeAverage.append("\n");
			for (int j=0; j<=81; j++){
				cntr[j]=0;
			}
			numBoards=0;
			File myObj = new File("time.csv");
			Scanner myReader = new Scanner(myObj);
			String[] line = myReader.nextLine().split(",");
			while (myReader.hasNextLine()) {
				for (int i=0; i<=81; i++){
					line = myReader.nextLine().split(",");
					cntr[i] += Integer.parseInt(line[1]);
				}
				numBoards++;
			}
			for (int i=0; i<=81; i++){
				timeAverage.append(i + "," + cntr[i]/numBoards);
				timeAverage.append("\n");
			}
			timeAverage.flush();
			timeAverage.close();
			myReader.close();

			//comparisons
			FileWriter compAverage = new FileWriter("comparisonsAve.csv");
			compAverage.append("EmptyCells");
			compAverage.append(",");
			compAverage.append("Comparisons");
			compAverage.append("\n");
			for (int j=0; j<=81; j++){
				cntr[j]=0;
			}
			numBoards=0;
			File myObj1 = new File("comparisons.csv");
			Scanner myReader1 = new Scanner(myObj1);
			line = myReader1.nextLine().split(",");
			while (myReader1.hasNextLine()) {
				for (int i=0; i<=81; i++){
					line = myReader1.nextLine().split(",");
					cntr[i] += Integer.parseInt(line[1]);
				}
				numBoards++;
			}
			for (int i=0; i<=81; i++){
				compAverage.append(i + "," + cntr[i]/numBoards);
				compAverage.append("\n");
			}
			compAverage.flush();
			compAverage.close();
			myReader1.close();

			//changes
			FileWriter changesAverage = new FileWriter("changesAve.csv");
			changesAverage.append("EmptyCells");
			changesAverage.append(",");
			changesAverage.append("Changes");
			changesAverage.append("\n");
			for (int j=0; j<=81; j++){
				cntr[j]=0;
			}
			numBoards=0;
			File myObj2 = new File("changes.csv");
			Scanner myReader2 = new Scanner(myObj2);
			line = myReader2.nextLine().split(",");
			while (myReader2.hasNextLine()) {
				for (int i=0; i<=81; i++){
					line = myReader2.nextLine().split(",");
					cntr[i] += Integer.parseInt(line[1]);
				}
				numBoards++;
			}
			for (int i=0; i<=81; i++){
				changesAverage.append(i + "," + cntr[i]/numBoards);
				changesAverage.append("\n");
			}
			changesAverage.flush();
			changesAverage.close();
			myReader2.close();

			//recursions
			FileWriter recAverage = new FileWriter("recursionsAve.csv");
			recAverage.append("EmptyCells");
			recAverage.append(",");
			recAverage.append("Recursions");
			recAverage.append("\n");
			for (int j=0; j<=81; j++){
				cntr[j]=0;
			}
			numBoards=0;
			File myObj3 = new File("recursions.csv");
			Scanner myReader3 = new Scanner(myObj3);
			line = myReader3.nextLine().split(",");
			while (myReader3.hasNextLine()) {
				for (int i=0; i<=81; i++){
					line = myReader3.nextLine().split(",");
					cntr[i] += Integer.parseInt(line[1]);
				}
				numBoards++;
			}
			for (int i=0; i<=81; i++){
				recAverage.append(i + "," + cntr[i]/numBoards);
				recAverage.append("\n");
			}
			recAverage.flush();
			recAverage.close();
			myReader3.close();

		}catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}


}
