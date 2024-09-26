package satsudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import satsudoku.exception.NotClonableExpressionException;

public class ReduceSudokuCNFFormula {

	private SudokuSat sudoku;

	private CNFExpression exp;
	private ArrayList<SudokuVariable> vars;
	private HashSet<String> impossibleCell;

	public ReduceSudokuCNFFormula(SudokuSat sudoku) {
		this.sudoku = sudoku;
		this.vars = deepCopyVars(SudokuExpressionBuilder.getVariables());
		try {
			this.exp = SudokuExpressionBuilder.getGeneralSudokuCNFExpression().clone();
		} catch (CloneNotSupportedException e) {
			throw new NotClonableExpressionException(exp.getClass().getSimpleName() + " must implements Cloneable");
		}
		impossibleCell = new HashSet<>();
		simplifyExp();
	}

	private void simplifyExp() {
		int[][] numbers = sudoku.getNumbers();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (numbers[i][j] != 0) {
					int number = numbers[i][j];
					String var = "(" + i + "," + j + "," + number + ")";
					vars.removeIf(variable -> variable.getVarRapresentation().equals(var));
					removeAffirmedVariableFromClauses(SudokuExpressionBuilder.getVariable(var));
					removeAllPossibleVariableWatching(new SudokuVariable(var));
				}

			}
		}
	}

	private void removeNegatedVariablesFromClauses(SudokuVariable var) {
		Iterator<Clause> itClauses = exp.getClauses().iterator();
		while (itClauses.hasNext()) {
			Clause clause = itClauses.next();
			Iterator<Litteral> itLitterals = clause.getLitterals().iterator();
			while (itLitterals.hasNext()) {
				Litteral litt = itLitterals.next();
				if (litt.getVar().getVarRapresentation().equals(var.getVarRapresentation())) {
					itLitterals.remove();
					if (litt.isNegated()) {
						itClauses.remove();
						break;
					}
				}
			}
		}
	}

	private void removeAffirmedVariableFromClauses(SudokuVariable var) {
		Iterator<Clause> it = exp.getClauses().iterator();
		while (it.hasNext()) {
			Clause clause = it.next();
			Iterator<Litteral> itLitterals = clause.getLitterals().iterator();
			while (itLitterals.hasNext()) {
				Litteral litt = itLitterals.next();
				if (litt.getVar().getVarRapresentation().equals(var.getVarRapresentation())) {
					itLitterals.remove();
					if (!litt.isNegated()) {
						it.remove();
						break;
					}
				}
			}
		}
	}

	private ArrayList<SudokuVariable> deepCopyVars(List<SudokuVariable> variables) {
		ArrayList<SudokuVariable> vars = new ArrayList<>();
		for (SudokuVariable var : variables) {
			try {
				vars.add(var.clone());
			} catch (CloneNotSupportedException e) {
				throw new NotClonableExpressionException(var.getClass().getSimpleName() + " must implement clone method.");
			}
		}
		return vars;
	}

	public SudokuSat getSudoku() {
		return sudoku;
	}

	public ArrayList<SudokuVariable> getVars() {
		return vars;
	}

	public CNFExpression getExp() {
		return exp;
	}

	public void removeAllPossibleVariableWatching(SudokuVariable var) {
		int r = var.getR();
		int c = var.getC();
		int v = var.getV();
		removeAllVariablesInCell(r, c, v);
		removeAllVariablesInRow(r, c, v);
		removeAllVariablesInColumn(r, c, v);
		removeAllVariablesInBox(r, c, v);

	}

	private void removeAllVariablesInCell(int r, int c, int v) {
		Iterator<SudokuVariable> it = vars.iterator();
		while (it.hasNext()) {
			SudokuVariable var = it.next();
			if (var.getR() == r && var.getC() == c && var.getV() != v) {
				if (impossibleCell.add(var.getVarRapresentation())) {
					removeNegatedVariablesFromClauses(var);
					it.remove();
				}
			}
		}

	}

	private void removeAllVariablesInBox(int r, int c, int v) {
		// GET Indexes
		int[] indexes = getBoxIndexes(r, c);

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				int row1 = 3 * indexes[0] + row;
				int col1 = 3 * indexes[1] + col;
				Iterator<SudokuVariable> it = vars.iterator();
				while (it.hasNext()) {
					SudokuVariable var = it.next();
					if (var.getR() == row1 && var.getC() == col1 && var.getV() == v) {
						if (impossibleCell.add(var.getVarRapresentation())) {
							removeNegatedVariablesFromClauses(var);
							it.remove();
						}
					}
				}
			}
		}

	}

	private int[] getBoxIndexes(int r, int c) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int row = 0; row < 3; row++) {
					for (int col = 0; col < 3; col++) {
						int row1 = 3 * i + row;
						int col1 = 3 * j + col;
						if (row1 == r && col1 == c) {
							int[] arr = new int[2];
							arr[0] = i;
							arr[1] = j;
							return arr;
						}
					}
				}
			}
		}
		return null;
	}

	private void removeAllVariablesInColumn(int r, int c, int v) {
		Iterator<SudokuVariable> it = vars.iterator();
		while (it.hasNext()) {
			SudokuVariable var = it.next();
			if (var.getR() != r && var.getC() == c && var.getV() == v) {
				if (impossibleCell.add(var.getVarRapresentation())) {
					removeNegatedVariablesFromClauses(var);
					it.remove();
				}
			}
		}

	}

	private void removeAllVariablesInRow(int r, int c, int v) {
		Iterator<SudokuVariable> it = vars.iterator();
		while (it.hasNext()) {
			SudokuVariable var = it.next();
			if (var.getC() != c && var.getR() == r && var.getV() == v) {
				if (impossibleCell.add(var.getVarRapresentation())) {
					removeNegatedVariablesFromClauses(var);
					it.remove();
				}
			}
		}
	}

}
