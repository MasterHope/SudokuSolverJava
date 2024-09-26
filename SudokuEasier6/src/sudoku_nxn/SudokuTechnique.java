package sudoku_nxn;

import java.util.ArrayList;

public abstract class SudokuTechnique {

	public abstract void apply(Sudoku sudoku);

	public void removeAllUselessCandidates(Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		BoxManagement boxManagement = sudoku.getBoxManagement();
		for (int i = 0; i < dimension; i++) {
			ArrayList<Cell> cellsColumn = new ArrayList<Cell>();
			for (int j = 0; j < dimension; j++) {
				int finalNumber = sudoku.getCell(i, j).getFinalNumber();
				if (finalNumber != 0) {
					int[][] boxIndexes = boxManagement.getBoxIndexes(i, j);
					boxManagement.removeBox(boxIndexes[0], boxIndexes[1], finalNumber, sudoku);
					sudoku.getCells().get(i).forEach(c -> c.removeCandidates(finalNumber)); // remove from row
				}
				cellsColumn.add(sudoku.getCell(j, i));
			}
			for (int z = 0; z < dimension; z++) {
				int finalNumber = cellsColumn.get(z).getFinalNumber();
				if (finalNumber != 0) {
					cellsColumn.forEach((c) -> c.removeCandidates(finalNumber));
				}
			}
		}

	}

}
