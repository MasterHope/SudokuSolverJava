package sudoku_nxn;

import java.util.Arrays;
import java.util.Optional;

import sudoku.exception.SudokuException;

public class SudokuSolver {

	private int numberIteration;
	private SudokuTechniques sudokuTechniques;

	public SudokuSolver(int numberIteration, SudokuTechniques sudokuTechniques) {
		this.numberIteration = numberIteration;
		this.sudokuTechniques = sudokuTechniques;
	}

	public Sudoku sudokuSolution(Sudoku sudoku) throws CloneNotSupportedException {
		removeAllUselessCandidates(sudoku);
		if (!solvable(sudoku))
			throw new SudokuException("Sudoku not solvable");
		int count = 0;
		while (count <= numberIteration && isNotSolved(sudoku)) {
			simplify(sudoku);
			count++;
			if (!solvable(sudoku)) {
				throw new SudokuException("Sudoku not solvable");
			}
		}
		return sudoku = bruteForceSolving(sudoku);

	}

	private void removeAllUselessCandidates(Sudoku sudoku) {
		sudokuTechniques.getTechniques().get(0).removeAllUselessCandidates(sudoku);
	}

	private void simplify(Sudoku sudoku) {
		for (SudokuTechnique sudokuTechnique : sudokuTechniques.getTechniques()) {
			sudokuTechnique.apply(sudoku);
		}
	}

	private Sudoku bruteForceSolving(Sudoku sudoku) throws CloneNotSupportedException {
		Optional<Cell> cellSetting = Optional.empty();
		Cell[] cells = null;
		Configuration config = new Configuration();
		CellComparator compare = new CellComparator();
		boolean exit = false;
		while (!exit) {
			config.pushSudokuConfig(sudoku);
			Sudoku sudoku2 = config.popPreviousSudoku();
			sudoku = sudoku.clone();
			cells = findCellsWithNoFinalNumber(sudoku);
			Arrays.sort(cells, compare);
			if (isNotSolved(sudoku)) {
				int number = getLowerDigitToInsert(cells, cellSetting);
				if (number == -1) {
					if (config.isPreviousSudokuEmpty()) {
						throw new SudokuException("Sudoku not solvable");
					}
					sudoku = config.popPreviousSudoku();
					cellSetting = Optional.of(config.popPreviousCell());
					// re-upload
				} else {
					cellSetting = getCellSetting(sudoku, cellSetting, cells, number);
					config.pushCellPreviousSetting(cellSetting.get().clone());
					int row = cellSetting.get().getR();
					int column = cellSetting.get().getC();
					sudoku.addNumberToSudokuSolution(number, sudoku.getCell(row, column));
					config.pushSudokuConfig(sudoku2);
					int count = 0;
					removeAllUselessCandidates(sudoku);
					if (!solvable(sudoku)) {
						sudoku = config.popPreviousSudoku();
						cellSetting = Optional.of(config.popPreviousCell());
					} else {
						boolean isSolvable = true;
						while (count < numberIteration && isNotSolved(sudoku)) {
							simplify(sudoku);
							isSolvable = solvable(sudoku);
							if (!isSolvable) {
								sudoku = config.popPreviousSudoku();
								cellSetting = Optional.of(config.popPreviousCell());
								break;
							}
							count++;
						}
						if (isSolvable) {
							cellSetting = Optional.empty();
						}
					}
				}
			} else {
				exit = true;
			}
		}
		return sudoku;
	}

	private Optional<Cell> getCellSetting(Sudoku sudoku, Optional<Cell> cellSetting, Cell[] cells, int number)
			throws CloneNotSupportedException {
		Cell cell;
		if (cellSetting.isEmpty()) {
			cell = cells[0];
			cellSetting = Optional.of(cell.clone());
			cellSetting.get().setFinalNumberWithoutRemovingCandidates(number);
		} else {
			cellSetting = getPreviousCellSetting(sudoku, cellSetting);
			cellSetting.get().setFinalNumberWithoutRemovingCandidates(number);
		}
		return cellSetting;
	}

	private Cell[] findCellsWithNoFinalNumber(Sudoku sudoku) {
		return sudoku.getCells().stream().flatMap(el -> el.stream()).filter(c -> c.getFinalNumber() == 0)
				.toArray(Cell[]::new);
	}

	private Optional<Cell> getPreviousCellSetting(Sudoku sudoku, Optional<Cell> cellSetting)
			throws CloneNotSupportedException {
		Cell cell;
		int r = cellSetting.get().getR();
		int c = cellSetting.get().getC();
		cell = sudoku.getCell(r, c);
		Cell cellAppo = cell.clone();
		Integer[] candidates = SudokuUtilities.getAllCandidatesArray(sudoku.getDimension());
		cellAppo.removeCandidates(candidates);
		cellAppo.addCandidates(cellSetting.get().getCandidates().toArray(Integer[]::new));
		cellSetting = Optional.of(cellAppo);
		return cellSetting;
	}

	private boolean isNotSolved(Sudoku sudoku) {
		return sudoku.getNumberFilled() != sudoku.getDimension()* sudoku.getDimension();
	}

	private int getLowerDigitToInsert(Cell[] cells, Optional<Cell> cellSetting) {
		if (cellSetting.isEmpty()) {
			return cells[0].getCandidates().get(0);
		} else {
			Optional<Integer> number = cellSetting.get().getCandidates().stream()
					.filter(c -> c > cellSetting.get().getFinalNumber()).findFirst();
			return (number.isPresent()) ? number.get() : -1;
		}
	}

	private boolean solvable(Sudoku sudoku) {
		boolean somethingToInsert = !isAnyEmptyCellWithNoCandidates(sudoku);
		BoxManagement boxManagement = sudoku.getBoxManagement();
		for (int i = 0; i < sudoku.getDimension(); i++) {
			if (!checkRowColumnConstraint(sudoku, i) || !checkBoxConstraint(sudoku, boxManagement, i)) {
				return false;
			}
		}
		return somethingToInsert;

	}

	private boolean checkRowColumnConstraint(Sudoku sudoku, int rowColumn) {
		int dimension = sudoku.getDimension();
		for (int numb = 1; numb <= dimension; numb++) {
			int countRow = 0;
			int countCol = 0;
			for (int j = 0; j < dimension; j++) {

				if (sudoku.getCell(rowColumn, j).getFinalNumber() == numb) {
					countRow++;
				}
				if (sudoku.getCell(j, rowColumn).getFinalNumber() == numb) {
					countCol++;
				}
				if (countRow > 1 || countCol > 1) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkBoxConstraint(Sudoku sudoku, BoxManagement boxManagement, int box) {
		int[][] boxes = boxManagement.getBoxes();
		int[][] boxIndexes = boxManagement.getBoxIndexes(boxes[box][0], boxes[box][1]);
		int[] jrows = boxIndexes[0];
		int[] zcolumns = boxIndexes[1];
		int dimension = sudoku.getDimension();
		int sqrtDimension = (int) Math.sqrt(dimension);
		for (int number = 1; number <= dimension; number++) {
			int countBox = 0;
			for (int j = 0; j < sqrtDimension; j++) {
				for (int z = 0; z < sqrtDimension; z++) {
					if (sudoku.getCell(jrows[j], zcolumns[z]).getFinalNumber() == number) {
						countBox++;
					}
					if (countBox > 1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean isAnyEmptyCellWithNoCandidates(Sudoku sudoku) {
		return sudoku.getCells().stream().flatMap(c -> c.stream())
				.anyMatch(c -> c.getCandidates().isEmpty() && c.isNotAlreadyOccupied());
	}

}
