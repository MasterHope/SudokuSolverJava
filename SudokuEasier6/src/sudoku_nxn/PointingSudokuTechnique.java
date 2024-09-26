package sudoku_nxn;

import java.util.ArrayList;
import java.util.HashSet;

public class PointingSudokuTechnique extends SudokuTechnique {

	@Override
	public void apply(Sudoku sudoku) {
		int dimension = sudoku.getDimension();
		for (int i = 0; i < dimension; i++) {
			for (int number = 1; number <= dimension; number++) {
				ArrayList<String> pos = positionsOfCandidateWhereInBox(sudoku,i, number);
				if (!pos.isEmpty() && everyElementStartEndWithSameDigit(pos, true)) {
					removePairsRow(sudoku,i, number, getRowColumnCandidate(sudoku,i, number)[0]);
				} else if (!pos.isEmpty() && everyElementStartEndWithSameDigit(pos, false)) {
					removePairsColumn(sudoku,i, number, getRowColumnCandidate(sudoku,i, number)[1]);
				}
			}
		}

	}

	private void removePairsColumn(Sudoku sudoku,int box, int number, int column) {
		int dimension = sudoku.getDimension();
		BoxManagement boxManagement = sudoku.getBoxManagement();
		int[][] boxes = boxManagement.getBoxes();
		int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[box][0], boxes[box][1]);
		int[] irows = boxIndexes[0];
		HashSet<Integer> hashSet = createSkippingBoxSet(irows);
		for (int i = 0; i < dimension; i++) {
			if (!hashSet.contains(i)) {
				Cell cell = sudoku.getCell(i, column);
				if (cell.getCandidates().contains(number)) {
					cell.removeCandidates(number);
				}
			}
		}

	}

	private HashSet<Integer> createSkippingBoxSet(int[] array) {
		HashSet<Integer> hashSet = new HashSet<>();
		for(int i=0; i < array.length; i++) {
			hashSet.add(array[i]);
		}
		return hashSet;
	}

	private void removePairsRow(Sudoku sudoku,int box, int number, int row) {
		int dimension = sudoku.getDimension();
		BoxManagement boxManagement = sudoku.getBoxManagement();
		int[][] boxes = boxManagement.getBoxes();
		int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[box][0],boxes[box][1]);
		int[] jcolumns = boxIndexes[1];
		HashSet<Integer> hashSet = createSkippingBoxSet(jcolumns);
		for (int i = 0; i < dimension; i++) {
			if (!hashSet.contains(i)) {
				Cell cell = sudoku.getCell(row, i);
				if (cell.getCandidates().contains(number)) {
					cell.removeCandidates(number);
				}
			}
		}
	}

	private boolean everyElementStartEndWithSameDigit(ArrayList<String> pos, boolean columnTrue_rowFalse) {
		char first = pos.get(0).toCharArray()[0];
		char end = pos.get(0).toCharArray()[pos.get(0).length() - 1];
		return (columnTrue_rowFalse) ? pos.stream().allMatch((string) -> string.startsWith("" + first))
				: pos.stream().allMatch((string) -> string.endsWith("" + end));

	}

	private ArrayList<String> positionsOfCandidateWhereInBox(Sudoku sudoku,int box, int candidate) {
		BoxManagement boxManagement = sudoku.getBoxManagement();
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		int[][] boxes = boxManagement.getBoxes();
		int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[box][0],boxes[box][1]);
		int[] irows = boxIndexes[0];
		int[] jcolumns = boxIndexes[1];
		ArrayList<String> positions = new ArrayList<>();
		for (int i = 0; i < sqrtDimension; i++) {
			for (int j = 0; j < sqrtDimension; j++) {
				Cell cell = sudoku.getCell(irows[i], jcolumns[j]);
				if (cell.getCandidates().contains(candidate)) {
					positions.add("" + irows[i] + jcolumns[j]);
				}
			}
		}
		return positions;
	}

	private int[] getRowColumnCandidate(Sudoku sudoku,int box, int number) {
		BoxManagement boxManagement = sudoku.getBoxManagement();
		int sqrtDimension = (int) Math.sqrt(sudoku.getDimension());
		int[][] boxes = boxManagement.getBoxes();
		int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[box][0],boxes[box][1]);
		int[] irows = boxIndexes[0];
		int[] jcolumns = boxIndexes[1];
		for (int i = 0; i < sqrtDimension; i++) {
			for (int j = 0; j < sqrtDimension; j++) {
				if (sudoku.getCell(irows[i], jcolumns[j]).getCandidates().contains(number)) {
					int[] rowCols = { irows[i], jcolumns[j] };
					return rowCols;
				}
			}
		}
		return null;
	}
}
