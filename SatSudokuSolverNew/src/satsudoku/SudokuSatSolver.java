package satsudoku;

import satsudoku.exception.SudokuException;

public class SudokuSatSolver {

	private ReduceSudokuCNFFormula reductor;
	private SatSolver sat;

	public SudokuSatSolver(SudokuSat sudoku) {
		reductor = new ReduceSudokuCNFFormula(sudoku);
		sat = new SatSolver(reductor.getVars());
	}

	public ReduceSudokuCNFFormula getReductor() {
		return reductor;
	}

	public SudokuSat sudokuSolution() {
		sat.findAssignment(reductor.getExp());
		String result = sat.getResult();
		if(result==null) {
			throw new SudokuException("Sudoku not solvable...");
		}
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == '1') {
				String str = sat.getVariablesMap().get(i);
				SudokuVariable var = new SudokuVariable(str);
				int r = var.getR();
				int c = var.getC();
				int v = var.getV();
				reductor.getSudoku().setRCV(r, c, v);
			}
		}
		return reductor.getSudoku();
	}

}
