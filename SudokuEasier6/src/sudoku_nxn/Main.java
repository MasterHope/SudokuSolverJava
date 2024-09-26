package sudoku_nxn;

public class Main {

	public static void main(String[] args) throws CloneNotSupportedException {
		int[][] sudoku1 = { { 2, 0, 0, 0, 7, 0, 0, 3, 8 }, { 0, 0, 0, 0, 0, 6, 0, 7, 0 }, { 3, 0, 0, 0, 4, 0, 6, 0, 0 },
				{ 0, 0, 8, 0, 2, 0, 7, 0, 0 }, { 1, 0, 0, 0, 0, 0, 0, 0, 6 }, { 0, 0, 7, 0, 3, 0, 4, 0, 0 },
				{ 0, 0, 4, 0, 8, 0, 0, 0, 9 }, { 0, 6, 0, 4, 0, 0, 0, 0, 0 }, { 9, 1, 0, 0, 6, 0, 0, 0, 2 } };
		int[][] sudoku2 = { { 2, 0, 7, 0, 0, 9, 1, 3, 4 }, { 0, 4, 0, 2, 1, 0, 0, 6, 7 }, { 3, 0, 0, 7, 6, 4, 8, 0, 9 },
				{ 0, 8, 2, 4, 3, 0, 7, 0, 6 }, { 0, 7, 9, 8, 2, 1, 4, 5, 0 }, { 4, 0, 3, 0, 7, 6, 2, 1, 0 },
				{ 5, 0, 6, 3, 4, 7, 0, 0, 1 }, { 7, 9, 0, 0, 5, 8, 0, 4, 0 }, { 8, 3, 4, 1, 0, 0, 6, 0, 5 } };
		int[][] sudoku3 = { { 0, 1, 7, 9, 0, 3, 6, 0, 0 }, { 0, 0, 0, 0, 8, 0, 0, 0, 0 }, { 9, 0, 0, 0, 0, 0, 5, 0, 7 },
				{ 0, 7, 2, 0, 1, 0, 4, 3, 0 }, { 0, 0, 0, 4, 0, 2, 0, 7, 0 }, { 0, 6, 4, 3, 7, 0, 2, 5, 0 },
				{ 7, 0, 1, 0, 0, 0, 0, 6, 5 }, { 0, 0, 0, 0, 3, 0, 0, 0, 0 }, { 0, 0, 5, 6, 0, 1, 7, 2, 0 } };
		int[][] sudoku4 = { { 4, 0, 0, 0, 0, 0, 9, 3, 8 }, { 0, 3, 2, 0, 9, 4, 1, 0, 0 }, { 0, 9, 5, 3, 0, 0, 2, 4, 0 },
				{ 3, 7, 0, 6, 0, 9, 0, 0, 4 }, { 5, 2, 9, 0, 0, 1, 6, 7, 3 }, { 6, 0, 4, 7, 0, 3, 0, 9, 0 },
				{ 9, 5, 7, 0, 0, 8, 3, 0, 0 }, { 0, 0, 3, 9, 0, 0, 4, 0, 0 }, { 2, 4, 0, 0, 3, 0, 7, 0, 9 } };

		int[][] sudoku5 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 3, 0, 8, 5 }, { 0, 0, 1, 0, 2, 0, 0, 0, 0 },
				{ 0, 0, 0, 5, 0, 7, 0, 0, 0 }, { 0, 0, 4, 0, 0, 0, 1, 0, 0 }, { 0, 9, 0, 0, 0, 0, 0, 0, 0 },
				{ 5, 0, 0, 0, 0, 0, 0, 7, 3 }, { 0, 0, 2, 0, 1, 0, 0, 0, 0 }, { 0, 0, 0, 0, 4, 0, 0, 0, 9 } };

		int[][] sudoku6 = { { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 4, 0, 0, 6, 0, 0, 2, 0 }, { 7, 6, 0, 0, 4, 0, 0, 9, 5 },
				{ 0, 0, 0, 5, 0, 3, 0, 0, 0 }, { 2, 1, 0, 0, 0, 0, 0, 4, 8 }, { 0, 0, 0, 4, 0, 8, 0, 0, 0 },
				{ 4, 2, 0, 0, 7, 0, 0, 1, 9 }, { 0, 9, 0, 0, 3, 0, 0, 7, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };

		int[][] sudoku7 = { { 8, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 3, 6, 0, 0, 0, 0, 0 }, { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
				{ 0, 5, 0, 0, 0, 7, 0, 0, 0 }, { 0, 0, 0, 0, 4, 5, 7, 0, 0 }, { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
				{ 0, 0, 1, 0, 0, 0, 0, 6, 8 }, { 0, 0, 8, 5, 0, 0, 0, 1, 0 }, { 0, 9, 0, 0, 0, 0, 4, 0, 0 } };
		int[][] sudoku8 = { { 0, 0, 6, 0, 0, 0, 4, 0, 0 }, { 0, 0, 0, 0, 5, 0, 0, 7, 0 }, { 0, 7, 0, 1, 0, 0, 0, 3, 0 },
				{ 8, 0, 0, 0, 7, 9, 0, 0, 6 }, { 0, 6, 0, 3, 0, 1, 0, 5, 0 }, { 7, 0, 0, 6, 2, 0, 0, 0, 4 },
				{ 0, 9, 0, 0, 0, 7, 0, 2, 0 }, { 0, 3, 0, 0, 6, 0, 0, 0, 0 }, { 0, 0, 8, 0, 0, 0, 9, 0, 0 } };
		int[][] sudoku10 = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
		int[][] sudoku11 = {{1,0,0,0},{2,3,0,4},{0,0,3,2},{0,0,0,0}};
		System.out.println((solveSudoku(sudoku1)));
		System.out.println();
		System.out.println((solveSudoku(sudoku2)));
		System.out.println();
		System.out.println((solveSudoku(sudoku3)));
		System.out.println();
		System.out.println((solveSudoku(sudoku4)));
		System.out.println();
		System.out.println((solveSudoku(sudoku5)));
		System.out.println();
		System.out.println((solveSudoku(sudoku6)));
		System.out.println();
		System.out.println((solveSudoku(sudoku7)));
		System.out.println();
		System.out.println((solveSudoku(sudoku8)));
		System.out.println();
		System.out.println((solveSudoku(sudoku10)));
		System.out.println();
		System.out.println((solveSudoku(sudoku11)));
		System.out.println();
	}

	private static String solveSudoku(int[][] sudoku1) throws CloneNotSupportedException {
		Sudoku sudoku = new Sudoku(sudoku1);
		SudokuTechniques techs = createSudokuTechniques(sudoku);
		SudokuSolver sudokuSol = new SudokuSolver(3, techs);
		sudoku = sudokuSol.sudokuSolution(sudoku);
		return sudoku.toString();
	}

	private static SudokuTechniques createSudokuTechniques(Sudoku sudoku) {
		EasyCellSudokuTecnique tech1 = new EasyCellSudokuTecnique();
		HiddenSingleSudokuTechnique tech2 = new HiddenSingleSudokuTechnique();
		NTupleSudokuTechnique tech3 = new NTupleSudokuTechnique();
		PointingSudokuTechnique tech4 = new PointingSudokuTechnique();
		SudokuTechniques techs = SudokuTechniques.Builder.newInstance(tech1)
					.addTechnique(tech2)
					.addTechnique(tech3)
					.addTechnique(tech4)
					.build();
		return techs;
	}

}
