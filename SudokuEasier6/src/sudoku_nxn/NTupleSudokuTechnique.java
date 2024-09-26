package sudoku_nxn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class NTupleSudokuTechnique extends SudokuTechnique {

	@Override
	public void apply(Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		for (int i = 0; i < dimension; i++) {
			ArrayList<Cell> cellsRow = sudoku.getCells().get(i);
			findNTuple(sudoku, i, cellsRow, "row");
			ArrayList<Cell> cellsColumn = SudokuUtilities.getCellsColumn(sudoku, i);
			findNTuple(sudoku, i, cellsColumn, "column");
			ArrayList<Cell> cellsBox = getCellsBox(sudoku, i);
			findNTuple(sudoku, i, cellsBox, "box");
		}
	}

	

	private ArrayList<Cell> getCellsBox(Sudoku sudoku, int box) {
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		BoxManagement boxManager = sudoku.getBoxManagement();
		int[][] boxes = boxManager.getBoxes();
		int[][] indexes = boxManager.getBoxIndexes(boxes[box][0],boxes[box][1]);
		int[] irows = indexes[0];
		int[] jcolumns = indexes[1];
		ArrayList<Cell> cellsBox = new ArrayList<>();
		for (int i = 0; i < sqrtDimension; i++) {
			for (int j = 0; j < sqrtDimension; j++) {
				cellsBox.add(sudoku.getCell(irows[i], jcolumns[j]));
			}
		}
		return cellsBox;
	}

	private void findNTuple(Sudoku sudoku, int i, ArrayList<Cell> cellsApp, String removeWhere) {
		Map<Cell, Integer> cellAndCount = generateCellMap(cellsApp, sudoku.getDimension());
		Set<Entry<Cell, Integer>> entrySet = cellAndCount.entrySet();
		for (Entry<Cell, Integer> entry : entrySet) {
			if (entry.getValue() > 1) {
				if (entry.getKey().getCandidates().size() == entry.getValue()) {
					removeAllCandidatesFrom(sudoku, i, removeWhere, entry.getKey().getCandidates());
				}
			}
		}
	}

	private Map<Cell, Integer> generateCellMap(ArrayList<Cell> cellsApp, int dimension) {
		Map<Cell, Integer> cellAndCount = new HashMap<>();
		for (int j = 0; j < dimension; j++) {
			if (cellsApp.get(j).getFinalNumber() == 0) {
				Integer count = cellAndCount.get(cellsApp.get(j));
				if (count == null) {
					cellAndCount.put(cellsApp.get(j), 1);
				} else {
					cellAndCount.put(cellsApp.get(j), ++count);
				}
			}
		}
		return cellAndCount;
	}

	private void removeAllCandidatesFrom(Sudoku sudoku, int position, String removeWhere,
			ArrayList<Integer> candidates) {
		BoxManagement boxManagement = sudoku.getBoxManagement();
		int dimension = sudoku.getDimension();
		switch (removeWhere) {
		case "row":
			sudoku.getCells().get(position).stream().forEach(c -> c.removeCandidatesIf(candidates));
			break;
		case "column":
			ArrayList<Cell> cellsColumn = new ArrayList<>();
			for (int j = 0; j < dimension; j++) {
				cellsColumn.add(sudoku.getCell(j,position));
			}
			cellsColumn.stream().forEach(c -> c.removeCandidatesIf(candidates));
			break;

		case "box":
			int sqrtDimension = (int) Math.sqrt(dimension);
			int[][] boxes = boxManagement.getBoxes();
			int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[position][0],boxes[position][1]);
			int[] irows = boxIndexes[0];
			int[] jcolumns = boxIndexes[1];
			for (int i = 0; i < sqrtDimension; i++) {
				for (int j = 0; j < sqrtDimension; j++) {
					sudoku.getCell(irows[i], jcolumns[j]).removeCandidatesIf(candidates);
				}
			}
			break;
		}

	}
}
