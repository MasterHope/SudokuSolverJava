package sudoku_nxn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EasyCellSudokuTecnique extends SudokuTechnique {

	private List<Integer> findUniqueNumbers(ArrayList<Cell> array) {
		return array.stream().filter((c) -> c.isNotAlreadyOccupied()).flatMap((c) -> c.getCandidates().stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.filter(c -> c.getValue() == 1).map(c -> c.getKey()).collect(Collectors.toList());
	}

	private void findAndInsertAllBoxUniqueNumbers(int box, Sudoku sudoku) {
		BoxManagement boxManager = sudoku.getBoxManagement();
		int[][] boxes = boxManager.getBoxes();
		int[][] boxindexes = boxManager.getBoxIndexes(boxes[box][0],boxes[box][1]);
		int[] irows = boxindexes[0];
		int[] jcolumns = boxindexes[1];
		ArrayList<Cell> cellsBox = boxManager.getCellsBox(sudoku, irows, jcolumns);
		List<Integer> numbersUnique = findUniqueNumbers(cellsBox);
		insertUniqueNumbersInBox(sudoku, irows, jcolumns, numbersUnique);
	}

	private void insertUniqueNumbersInBox(Sudoku sudoku, int[] irows, int[] jcolumns, List<Integer> numbersUnique) {
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		for (int z = 0; z < numbersUnique.size(); z++) {
			int finalNumber = numbersUnique.get(z);
			for (int i = 0; i < sqrtDimension; i++) {
				for (int j = 0; j < sqrtDimension; j++) {
					Cell cell = sudoku.getCell(irows[i],jcolumns[j]);
					if (cell.getCandidates().contains(finalNumber)) {
						sudoku.addNumberToSudokuSolution(finalNumber, cell);
						removeAllUselessCandidates(sudoku);
						return;
					}
				}
			}
		}
	}

	private void putAllUniqueNumbersInRow(List<Integer> numbersUnique, int j, Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		for (int z = 0; z < numbersUnique.size(); z++) {
			int finalNumber = numbersUnique.get(z);
			for (int i = 0; i < dimension; i++) {
				Cell cell = sudoku.getCell(j, i);
				if (cell.getCandidates().contains(finalNumber)) {
					sudoku.addNumberToSudokuSolution(finalNumber, cell);
					removeAllUselessCandidates(sudoku);
					return;
				}

			}
		}
	}

	private void putAllUniqueNumbersInColumn(List<Integer> numbersUnique, int j, Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		for (int z = 0; z < numbersUnique.size(); z++) {
			int finalNumber = numbersUnique.get(z);
			for (int i = 0; i < dimension; i++) {
				Cell cell = sudoku.getCell(i, j);
				if (cell.getCandidates().contains(finalNumber)) {
					sudoku.addNumberToSudokuSolution(finalNumber, cell);
					removeAllUselessCandidates(sudoku);
					return;
				}
			}

		}
	}

	@Override
	public void apply(Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		for (int j = 0; j < dimension; j++) {
			ArrayList<Cell> cellsRow = sudoku.getCells().get(j);
			List<Integer> numbersUnique = findUniqueNumbers(cellsRow);
			putAllUniqueNumbersInRow(numbersUnique, j, sudoku);
			ArrayList<Cell> cellsColumn = SudokuUtilities.getCellsColumn(sudoku,j);
			numbersUnique = findUniqueNumbers(cellsColumn);
			putAllUniqueNumbersInColumn(numbersUnique, j, sudoku);
			findAndInsertAllBoxUniqueNumbers(j, sudoku);
		}
	}

	

}
