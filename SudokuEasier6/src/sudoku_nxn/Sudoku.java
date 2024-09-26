package sudoku_nxn;

import java.util.ArrayList;
import java.util.Iterator;

import sudoku.exception.SudokuException;

public class Sudoku implements Iterable<Cell>, Cloneable {

	private ArrayList<ArrayList<Cell>> cells;
	private int numberFilled;
	private BoxManagement boxManagement;

	public ArrayList<ArrayList<Cell>> getCells() {
		return cells;
	}

	public Cell getCell(int i, int j) {
		return cells.get(i).get(j);
	}

	public Sudoku(int[][] numbers) {
		if (numbers[0].length != numbers.length) {
			throw new SudokuException("Not valid sudoku. It must be nxn sudoku.");
		}
		int n = numbers.length;
		boxManagement = new BoxManagement(n);
		cells = new ArrayList<ArrayList<Cell>>();
		for (int i = 0; i < n; i++) {
			cells.add(new ArrayList<Cell>());
		}
		numberFilled = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Cell cell;
				if (numbers[i][j] != 0) {
					cell = new Cell(numbers[i][j], n);
					cells.get(i).add(cell);
					numberFilled = getNumberFilled() + 1;
				} else {
					cell = new Cell(n);
					cells.get(i).add(cell);
				}
				cells.get(i).get(j).setR(i);
				cells.get(i).get(j).setC(j);
			}
		}
	}

	private Sudoku(ArrayList<ArrayList<Cell>> cells, int numberFilled) {
		this.cells = cells;
		this.numberFilled = numberFilled;
		boxManagement = new BoxManagement(this.getDimension());
	}

	public int getNumberFilled() {
		return numberFilled;
	}

	@Override
	public Iterator<Cell> iterator() {
		return new Iterator<Cell>() {

			Cell current = null;
			int numberOfCell = 0;
			int row = 0;
			int column = 0;

			@Override
			public boolean hasNext() {
				return (current == null) ? false : (numberOfCell < current.getN() * current.getN()) ? true : false;
			}

			@Override
			public Cell next() {
				current = cells.get(row).get(column);
				if (column == 8) {
					row++;
					column = 0;
				} else {
					column++;
				}
				numberOfCell++;
				return current;
			}

		};
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		int dimension = cells.size();
		int sqrtDimension = (int) Math.sqrt(dimension);
		for (int r = 0; r < dimension; r++) {
			if(r%sqrtDimension==0) {
				createHorizontalLine(string, dimension, sqrtDimension);
			}
			for (int c = 0; c < dimension; c++) {
				if (c % sqrtDimension == 0) {
					string.append("|");
				}
				if (this.getCell(r, c).getFinalNumber() != 0) {
					string.append(this.getCells().get(r).get(c).getFinalNumber() + "");
				} else {
					string.append("0");
				}
				if (c == dimension - 1) {
					string.append("|" + "\n");
				}
			}
		}
		createHorizontalLine(string, dimension, sqrtDimension);
		return string.toString();
	}

	private void createHorizontalLine(StringBuilder string, int dimension, int sqrtDimension) {
		for(int i=0; i<dimension+sqrtDimension+1;i++) {
			if(i==0 || i==dimension+sqrtDimension) {
				string.append("#");
			}else {
				string.append("-");
			}
		}
		string.append("\n");
	}



	@Override
	public Sudoku clone() throws CloneNotSupportedException {
		ArrayList<ArrayList<Cell>> sud = new ArrayList<>();
		for (ArrayList<Cell> arrCell : cells) {
			ArrayList<Cell> arrAppo = new ArrayList<>();
			for (Cell cell : arrCell) {
				arrAppo.add(cell.clone());
			}
			sud.add(arrAppo);
		}
		return new Sudoku(sud, numberFilled);
	}

	public void addNumberToSudokuSolution(int finalNumber, Cell cell) {
		cell.setFinalNumber(finalNumber);
		this.numberFilled++;
	}

	public int getDimension() {
		return cells.size();
	}

	public BoxManagement getBoxManagement() {
		return boxManagement;
	}

}
