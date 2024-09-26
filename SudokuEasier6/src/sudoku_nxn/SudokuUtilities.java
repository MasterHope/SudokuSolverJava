package sudoku_nxn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SudokuUtilities {

	public static ArrayList<Cell> getCellsColumn(Sudoku sudoku, int column) {
		int dimension = sudoku.getDimension();
		ArrayList<Cell> cellsColumn = new ArrayList<>(dimension);
		for (int j = 0; j < dimension; j++) {
			cellsColumn.add(sudoku.getCell(j, column));
		}
		return cellsColumn;
	}

	public static List<Integer> getAllCandidates(int n) {
		return Stream.iterate(1, i -> i + 1).limit(n + 1).collect(Collectors.toList());
	}

	public static Integer[] getAllCandidatesArray(int n) {
		return getAllCandidates(n).toArray(Integer[]::new);
	}

}
