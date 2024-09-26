package satsudoku;

import satsudoku.exception.SudokuException;

public class SudokuSat {

	private int[][] numbers;

	public SudokuSat(int[][] numbers) {
		if (numbers.length != numbers[0].length && numbers.length != 9) {
			throw new SudokuException("Sudoku is not 9x9 grid");
		}
		this.numbers = numbers;
	}

	public int[][] getNumbers() {
		return numbers;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int r = 0; r < 9; r++) {
			if (r % 3 == 0)
				string.append("#-------#-------#-------#" + "\n");
			for (int c = 0; c < 9; c++) {
				if (c % 3 == 0) {
					string.append("| ");
				}
				string.append(numbers[r][c] + " ");
				if (c == 8) {
					string.append("|" + "\n");
				}
			}

		}
		string.append("#-------#-------#-------#");
		return string.toString();
	}

	public void setRCV(int r, int c, int v) {
		this.numbers[r][c] = v;
	}

}