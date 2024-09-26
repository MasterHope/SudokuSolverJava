package sudoku_nxn;

import java.util.ArrayList;

public class BoxManagement {

	private int dimension;

	public BoxManagement(int dimension) {
		this.dimension = dimension;
	}

	public int[][] getBoxes() {
		int[][] boxes = new int[dimension][2];
		int sqrtDimension = (int) Math.sqrt(dimension);
		int rowElement = 0;
		int colElement = 0;
		for (int i = 0; i < dimension; i++) {
			boxes[i][0] = rowElement;
			boxes[i][1] = colElement;
			if (colElement + sqrtDimension >= dimension) {
				rowElement += 3;
				colElement = 0;
			} else {
				colElement += 3;
			}
		}
		return boxes;
	}

	public int[][] getBoxIndexes(int row, int col) {
		int sqrtDimension = (int) Math.sqrt(dimension);
		int[][] positions = createPositionsArray(sqrtDimension);
		int[][] boxindexes = new int[2][sqrtDimension];
		boxindexes[0] = positions[row / sqrtDimension];
		boxindexes[1] = positions[col / sqrtDimension];
		return boxindexes;
	}

	private int[][] createPositionsArray(int sqrtDimension) {
		int[][] positions = new int[sqrtDimension][sqrtDimension];
		int array = 0;
		int posInArray = 0;
		int number = 0;
		while (number < dimension) {
			positions[array][posInArray] = number++;
			if (posInArray >= sqrtDimension - 1) {
				array++;
				posInArray = 0;
			} else {
				posInArray++;
			}
		}
		return positions;
	}

	public void removeBox(int[] irows, int[] jcolumns, int finalNumber, Sudoku sudoku) {
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		for (int i = 0; i < sqrtDimension; i++) {
			for (int j = 0; j < sqrtDimension; j++) {
				sudoku.getCell(irows[i], jcolumns[j]).removeCandidates(finalNumber);
			}
		}

	}

	public ArrayList<Cell> getCellsBox(Sudoku sudoku, int[] irows, int[] jcolumns) {
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		ArrayList<Cell> cellsBox = new ArrayList<>();
		for (int i = 0; i < sqrtDimension; i++) {
			for (int j = 0; j < sqrtDimension; j++) {
				cellsBox.add(sudoku.getCell(irows[i], jcolumns[j]));
			}
		}
		return cellsBox;
	}

}
