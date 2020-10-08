import java.util.*;



public class Program{

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int [][] board = new int[9][9];
		int[] counter = {0,0} ;

        for(int i=0; i<9; ++i){
            String [] line = in.nextLine().split(" ");
            for(int j=0; j<9; j++){
                board[i][j] = Integer.parseInt(line[j]);
            }
        }

        int N=9;


        //start timer
		long startTime = System.nanoTime();

		//solve
		if (solveSudoku(board, N, counter)) { 
			printLooksNxa(board, N); 
		} 
		else { 
			System.out.println("No solution"); 
		}

		//end timer
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000;
		String time = Long.toString(duration) + " microseconds";
		System.out.println(time);

		//comparisons
		String comparisons = Integer.toString(counter[0]) + " comparisons";
		String changes = Integer.toString(counter[1]) + " changes";
		System.out.println(comparisons);
		System.out.println(changes);
         

        in.close();
    }

    public static boolean isSafe(int[][] board,	int row, int col, int num, int[] counter)	{ 
		// row has the unique (row-clash) 
		for (int d = 0; d < board.length; d++) { 
			// if the number we are trying to 
			// place is already present in 
			// that row, return false; 
			counter[0]++;
			if (board[row][d] == num) { 
				return false; 
			} 
		} 

		// column has the unique numbers (column-clash) 
		for (int r = 0; r < board.length; r++) { 
			// if the number we are trying to 
			// place is already present in 
			// that column, return false; 
			counter[0]++;
			if (board[r][col] == num) { 
				return false; 
			} 
		} 

		// corresponding square has 
		// unique number (box-clash) 
		int sqrt = (int)Math.sqrt(board.length); 
		int boxRowStart = row - row % sqrt; 
		int boxColStart = col - col % sqrt; 

		for (int r = boxRowStart; 
			r < boxRowStart + sqrt; r++) { 
			for (int d = boxColStart; 
				d < boxColStart + sqrt; d++) { 
				counter[0]++;
				if (board[r][d] == num) { 
					return false; 
				} 
			} 
		} 

		// if there is no clash, it's safe 
		return true; 
	} 

	public static boolean solveSudoku( int[][] board, int n, int[] counter) { 
		int row = -1; 
		int col = -1; 
		boolean isSolved = true; 
		for (int i = 0; i < n; i++) { 
			for (int j = 0; j < n; j++) { 
				if (board[i][j] == 0) { 
					row = i; 
					col = j; 

					// we still have some remaining 
					// missing values in Sudoku 
					isSolved = false; 
					break; 
				} 
			} 
			if (!isSolved) { 
				break; 
			} 
		} 

		// no empty space left 
		if (isSolved) { 
			return true; 
		} 

		// else for each-row backtrack 
		for (int num = 1; num <= n; num++) { 
			if (isSafe(board, row, col, num, counter)) { 
				counter[1]++;
				board[row][col] = num; 
				if (solveSudoku(board, n, counter)) { 
					// print(board, n); 
					return true; 
				} 
				else { 
					// replace it 
					board[row][col] = 0; 
				} 
			} 
		} 
		return false; 
	} 

	public static void printForSubmission(int[][] board, int N) { 
		for (int row = 0; row < N; row++) { 
			for (int column = 0; column < N; column++) { 
				System.out.print(board[row][column]); 
                System.out.print(" "); 

			} 
			System.out.print("\n"); 
		} 
	} 

	public static void printLooksNxa(int[][] board, int N) { 
        System.out.println("\n"); 
        System.out.println("----- Solution: -----"); 

		for (int row = 0; row < N; row++) { 
			for (int column = 0; column < N; column++) { 
				System.out.print(board[row][column]); 
                System.out.print(" "); 

                if ((column + 1) % (int)Math.sqrt(N) == 0) { 
                    System.out.print(" ");
                } 

			} 
			System.out.print("\n"); 

			if ((row + 1) % (int)Math.sqrt(N) == 0) { 
				System.out.print("\n"); 
			} 
		} 
	} 
}