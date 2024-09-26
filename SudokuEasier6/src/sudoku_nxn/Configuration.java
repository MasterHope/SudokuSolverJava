package sudoku_nxn;

import java.util.Stack;

public class Configuration {
	Stack<Sudoku> stackPreviousSudoku;
	Stack<Cell> stackCellPreviousSetting;

	public Configuration() {
		stackPreviousSudoku = new Stack<>();
		stackCellPreviousSetting = new Stack<>();
	}
	
	public void pushSudokuConfig(Sudoku sudoku) {
		stackPreviousSudoku.push(sudoku);
	}
	
	public void pushCellPreviousSetting(Cell cell) {
		stackCellPreviousSetting.push(cell);
	}
	
	public Sudoku popPreviousSudoku() {
		return stackPreviousSudoku.pop();
	}
	
	public Cell popPreviousCell() {
		return stackCellPreviousSetting.pop();
	}
	
	public boolean isPreviousSudokuEmpty() {
		return stackPreviousSudoku.isEmpty();
	}

}
