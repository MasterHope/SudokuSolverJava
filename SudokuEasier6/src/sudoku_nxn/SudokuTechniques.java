package sudoku_nxn;

import java.util.ArrayList;

import sudoku.exception.NullTechniqueException;

public class SudokuTechniques {

	public final static class Builder {
		
		private ArrayList<SudokuTechnique> techniques;

		private Builder(SudokuTechnique tech) {
			techniques = new ArrayList<>();
			addTechnique(tech);
		}

		public static Builder newInstance(SudokuTechnique tech) {
			return new Builder(tech);
		}

		public Builder addTechnique(SudokuTechnique tech) {
			if (tech == null) {
				throw new NullTechniqueException("Technique must not be null");
			}
			techniques.add(tech);
			return this;
		}

		public SudokuTechniques build() {
			return new SudokuTechniques(techniques);
		}
	}

	private Sudoku sudoku;
	private ArrayList<SudokuTechnique> techniques;

	private SudokuTechniques(ArrayList<SudokuTechnique> sudokuTechniques) {
		this.techniques = sudokuTechniques;
	}

	public ArrayList<SudokuTechnique> getTechniques() {
		return techniques;
	}

	public boolean isTechniquesEmpty() {
		return techniques.isEmpty();
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

}
