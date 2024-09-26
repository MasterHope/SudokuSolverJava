package sudoku_nxn;

public class HiddenSingleSudokuTechnique extends SudokuTechnique {

	@Override
	public void apply(Sudoku sudoku) {
		for (Cell c : sudoku) {
			if (c.onlyOneCandidateIsPresent()) {
				int finalNumber = c.getCandidates().get(0);
				sudoku.addNumberToSudokuSolution(finalNumber, c);
				removeAllUselessCandidates(sudoku);
			}
		}

	}

}
